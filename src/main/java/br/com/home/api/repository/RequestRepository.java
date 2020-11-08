package br.com.home.api.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.home.api.domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
	
	public Collection<Request> findAllByOwnerId(Long ownerId);

}
