package br.com.home.api.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.RequestStage;
import br.com.home.api.domain.enums.RequestState;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long>{
	
	public Collection<RequestStage> findAllByRequestId(Long requestId);
	
	@Query("UPDATE Request SET state = ?2 WHERE id = ?1")
	public Request updateStatus(Long pedidoId, RequestState state);

}
