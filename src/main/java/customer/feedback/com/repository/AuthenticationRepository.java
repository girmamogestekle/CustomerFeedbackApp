package customer.feedback.com.repository;

import customer.feedback.com.model.AuthenticationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends CrudRepository<AuthenticationEntity, String> {

    boolean existsByUsername(String username);

    Optional<AuthenticationEntity> findByUsername(String username);

}
