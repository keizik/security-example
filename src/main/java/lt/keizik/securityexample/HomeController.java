package lt.keizik.securityexample;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegistrationPage(Model model) {
		model.addAttribute("user", new User());
		return "registration";

	}

	@PostMapping("/register")
	public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		model.addAttribute("user", user);
		if (result.hasErrors()) {
			return "registration";
		} else {
			userService.saveUser(user);
			model.addAttribute("message", "User Account Created");
		}
		return "index";
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/secure")
	public String secure(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("user", userRepository.findByUserName(username));
		return "secure";
	}

	@RequestMapping("/logout")
	public String logout() {
		return "logout";
	}

}
