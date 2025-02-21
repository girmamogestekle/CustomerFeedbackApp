package customer.feedback.com.repository;

import customer.feedback.com.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "UserRepository")
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndIsEnabled(String username, boolean isEnabled);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<List<UserEntity>> findAllByIsEnabled(boolean isEnabled);
    Optional<String> deleteByUsername(String username);

}
