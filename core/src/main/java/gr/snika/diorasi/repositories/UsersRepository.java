package gr.snika.diorasi.repositories;

import java.util.UUID;
import gr.snika.diorasi.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, UUID> {

}
