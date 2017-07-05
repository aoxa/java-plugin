package hello;

import hello.model.Task;
import hello.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class HelloWorld {

	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", "Welcome file");
		return "welcome";
	}

	@GetMapping("/mockAdd")
	public @ResponseBody String addMock() {
		Task task = new Task();

		task.setDescription("mock desc");
		task.setStartDate(new Date());
		task.setDuration(3);

		task = taskRepository.save(task);

		return "Saved: " + task.getId();
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<Task> getAll() {
		return taskRepository.findAll();
	}


}