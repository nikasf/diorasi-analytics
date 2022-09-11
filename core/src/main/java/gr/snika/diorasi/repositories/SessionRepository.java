package gr.snika.diorasi.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gr.snika.diorasi.entities.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {

}
