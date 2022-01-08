package br.com.home.api.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.home.api.domain.RequestStage;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long>{
	
	public Collection<RequestStage> findAllByRequestId(Long requestId);

	public Page<RequestStage> findAllByRequestId(Long requestId, Pageable pageable);

}
