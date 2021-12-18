package br.com.home.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import br.com.home.api.domain.RequestStage;
import br.com.home.api.domain.enums.RequestState;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class RequestStageRepositoryTest {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void saveTest() {
        val request = requestRepository.findAll().stream().findFirst().get();
        val owner = userRepository.findAll().stream().findFirst().get();

        RequestStage stage = new RequestStage(null, "Foi comprado um novo laptop de marca HD e com 16GB de RAM", new Date(), RequestState.CLOSED, request, owner);
        val createdStaged = requestStageRepository.save(stage);

        assertThat(requestStageRepository.findById(createdStaged.getId())).isPresent();
    }

    @Test
    @Order(2)
    public void getByIdTest() {
        val result = requestStageRepository.findAll().stream().findFirst();
        val requestStage = requestStageRepository.findById(result.get().getId()).get();

        assertThat(requestStage.getDescription()).isEqualTo("Foi comprado um novo laptop de marca HD e com 16GB de RAM");

    }

    @Test
    @Order(3)
    public void getListByRequestIdTest() {
        val request = requestRepository.findAll().stream().findFirst().get();
        val requestStages = requestStageRepository.findAllByRequestId(request.getId());

        assertThat(requestStages.size()).isGreaterThan(0);
    }

}
