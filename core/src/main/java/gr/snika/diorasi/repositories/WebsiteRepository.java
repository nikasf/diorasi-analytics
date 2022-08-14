package gr.snika.diorasi.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gr.snika.diorasi.entities.Website;

@Repository
public interface WebsiteRepository extends CrudRepository<Website, UUID> {


}
