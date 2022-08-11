package gr.snika.diorasi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.snika.diorasi.entities.AppUser;

@Repository
public interface UserRepository extends CrudRepository<AppUser, UUID> {

	@Query("SELECT u FROM AppUser u WHERE u.username=:username")
	AppUser findByUsername(@Param("username") String username);

}
