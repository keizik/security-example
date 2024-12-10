package lt.keizik.securityexample;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;

	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {

		roleRepository.save(new Role("USER"));
		roleRepository.save(new Role("ADMIN"));
		Role adminRole = roleRepository.findByRole("ADMIN");
		Role userRole = roleRepository.findByRole("USER");

		User user = new User("jim@jim.com", passwordEncoder.encode("password"), "Jim", "Jimerson", true, "jim"); // passwordEncoder
																													// -
																													// uzkoduoja
																													// slaptazodi
		userRepository.save(user);

		user = new User("admin@admin.com", "password", "Admin", "User", true, "admin");
		user.setRoles(Arrays.asList(adminRole));
		userRepository.save(user);

	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Long countByEmail(String email) {
		return userRepository.countByEmail(email);
	}

	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public void saveUser(User user) {
		user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public void saveAdmin(User user) {
		user.setRoles(Arrays.asList(roleRepository.findByRole("ADMIN")));
		user.setEnabled(true);
		userRepository.save(user);
	}

}
