package br.com.home.api.repository;

import java.util.Collection;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.enums.RequestState;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
	
	public Collection<Request> findAllByOwnerId(Long ownerId);

	public Page<Request> findAllByOwnerId(Long id, Pageable pageable);

	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE Request SET state = ?2 WHERE id = ?1")
	public Integer updateStatus(Long pedidoId, RequestState state);

}
