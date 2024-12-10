package lt.keizik.securityexample;

import java.util.Arrays;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

public class DataLoader implements InitializingBean {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private DelegatingPasswordEncoder passwordEncoder;

	@Override
	public void afterPropertiesSet() throws Exception {
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

}
