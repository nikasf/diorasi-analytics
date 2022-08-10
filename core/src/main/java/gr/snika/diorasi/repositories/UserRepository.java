package gr.snika.diorasi.repositories;

import java.util.UUID; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gr.snika.diorasi.entities.AppUser;

public interface UserRepository extends CrudRepository<AppUser, UUID> {

	@Query("SELECT u FROM AppUser u WHERE u.username=:username")
	AppUser findByUsername(@Param("username") String username);

}
