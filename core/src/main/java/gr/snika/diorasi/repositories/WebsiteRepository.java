package gr.snika.diorasi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.snika.diorasi.entities.AppUser;
import gr.snika.diorasi.entities.Website;

@Repository
public interface WebsiteRepository extends CrudRepository<Website, UUID> {

	@Query("SELECT w FROM Website w WHERE w.owner=:owner")
	Iterable<Website> findAllByOwnerId(@Param("owner") AppUser owner);

	@Query("SELECT w FROM Website w WHERE w.domain=:domain")
	Website findByDomainName(@Param("domain") String domain);
}
