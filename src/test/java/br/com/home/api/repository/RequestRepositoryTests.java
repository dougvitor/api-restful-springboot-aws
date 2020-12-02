package br.com.home.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.User;
import br.com.home.api.domain.enums.RequestState;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class RequestRepositoryTests {
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Test
	@Order(1)
	public void saveTest() {
		User owner = new User();
		owner.setId(1L);
		
		Request request = new Request(null, "Novo Laptop HP", "Pretendo obter um laptop HP", new Date(), RequestState.OPEN, owner, null);
		Request createdRequest = requestRepository.save(request);
		
		assertThat(createdRequest.getId()).isEqualTo(1L);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		User owner = new User();
		owner.setId(1L);
		
		String newDescription = "Pretendo obter um laptop HP, de 16GB de RAM";
		Request request = new Request(1L, "Novo Laptop HP", newDescription, null, RequestState.OPEN, owner, null);
		Request updatedRequest = requestRepository.save(request);
		
		assertThat(updatedRequest.getDescription()).isEqualTo(newDescription);
	}

	@Test
	@Order(3)
	public void findByIdTest() {
		Optional<Request> result = requestRepository.findById(1L);
		Request request = result.get();
		
		assertThat(request.getSubject()).isEqualTo("Novo Laptop HP");
	}
	
	@Test
	@Order(4)
	public void findAllTest() {
		List<Request> requests = requestRepository.findAll();
		
		assertThat(requests.size()).isEqualTo(1);
	}
	
	@Test
	@Order(5)
	public void findAllByOwnerIdTest() {
		Collection<Request> requestsByOwner = requestRepository.findAllByOwnerId(1L);
		
		assertThat(requestsByOwner.size()).isEqualTo(1);
	}
	
	@Test
	@Order(6)
	public void updateStatusTest() {
		Integer affectedRows = requestRepository.updateStatus(1L, RequestState.IN_PROGRESS);
		
		assertThat(affectedRows).isEqualTo(1);
	}

}
