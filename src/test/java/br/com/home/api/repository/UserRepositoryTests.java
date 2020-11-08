package br.com.home.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.home.api.domain.User;
import br.com.home.api.domain.enums.Role;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@Order(1)
	public void saveTest() {
		User user = User
						.builder()
						.id(null)
						.name("DouG")
						.email("douglas@gmail.com")
						.password("123")
						.role(Role.ADMINISTRATOR)
						.build();
		
		User createdUser = userRepository.save(user);
		
		assertThat(createdUser.getId()).isEqualTo(1L);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		User user = User
				.builder()
				.id(1L)
				.name("DouG ViTor")
				.email("douglas@gmail.com")
				.password("123")
				.role(Role.ADMINISTRATOR)
				.build();
		
		User updatedUser = userRepository.save(user);
		
		assertThat(updatedUser.getName()).isEqualTo("DouG ViTor");
	}

	@Test
	@Order(3)
	public void findByIdTest() {
		Optional<User> result = userRepository.findById(1L);
		User user = result.get();
		
		assertThat(user.getPassword()).isEqualTo("123");
	}
	
	@Test
	@Order(4)
	public void findAllTest() {
		List<User> users = userRepository.findAll();
		
		assertThat(users.size()).isEqualTo(1);
	}
	
	@Test
	@Order(5)
	public void loginTest() {
		Optional<User> result = userRepository.login("douglas@gmail.com", "123");
		User loggedUser = result.get();
		
		assertThat(loggedUser.getId()).isEqualTo(1L);
	}
	
	@Test
	@Order(6)
	public void deleteTest() {
		Optional<User> result = userRepository.findById(1L);
		User user = result.get();
		
		userRepository.delete(user);
		
		result = userRepository.findById(1L);
		
		assert(result.isEmpty());
	}
}
