package lt.keizik.securityexample;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserName(String userName);

	User findByEmail(String email);

	Long countByEmail(String email);

	Long countByUserName(String userName);

}
