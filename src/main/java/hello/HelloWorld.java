package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HelloWorld {

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", "Welcome file");
		return "welcome";
	}
}