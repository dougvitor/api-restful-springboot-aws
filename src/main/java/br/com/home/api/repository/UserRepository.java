package br.com.home.api.repository;

import java.util.Optional;

import br.com.home.api.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.home.api.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT user FROM User user WHERE email = ?1 AND password = ?2")
	public Optional<User> login(String username, String password);

	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE User SET role = ?2 WHERE id = ?1")
	public int updateRole(Long id, Role role);

	public Optional<User> findByEmail(String email);
}
