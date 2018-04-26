
package courses;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CourseController {

	@Resource
	CourseRepository courseRepo;

	@Resource
	TopicRepository topicRepo;

	@RequestMapping("/show-courses")
	public String findAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAll());
		return "courses";
	}

	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value = "id") Long id, Model model) throws CourseNotFoundException
	{

		Optional<Course> course = courseRepo.findById(id);

		if (course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course";
		}

		throw new CourseNotFoundException();

	}

	@RequestMapping("/show-topics")
	public String findAllTopics(Model model) {
		model.addAttribute("topics", topicRepo.findAll());
		return "topics";
	}

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value = "id") Long id, Model model) {
		Optional<Topic> topic = topicRepo.findById(id);

		if (topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			return "topic";
		}

		throw new TopicNotFoundException();
	}
	
	@RequestMapping("/add-course")
	public String addCourse(String name, String description, String topicName) {

		Topic topic = topicRepo.findByName(topicName);
		if(topic == null) {
			topic = new Topic(topicName);
			topicRepo.save(topic);
		}

		Course newCourse = courseRepo.findByName(name);
		if (newCourse == null) {
			newCourse = new Course(name, description, topic);
			courseRepo.save(newCourse);
		}

		return "redirect:/show-courses";
	}

	@RequestMapping("/delete-course")
	public String deleteCourseByName(String courseName) {

		if (courseRepo.findByName(courseName) != null) {
			Course deletedCourse = courseRepo.findByName(courseName);
			courseRepo.delete(deletedCourse);

		}

		return "redirect:/show-courses";
	}

	@RequestMapping(value = "/del-course", method = RequestMethod.POST)
	public String deleteCourseById(@RequestParam Long id) {

		courseRepo.deleteById(id);

		return "redirect:/show-courses";
	}

	@RequestMapping("find-by-topic")
	public String findCoursesByTopic(String topicName, Model model) {
		Topic topic = topicRepo.findByName(topicName);
		model.addAttribute("courses", courseRepo.findByTopicsContains(topic));
		return "/topic";
	}

	@RequestMapping("/sort-courses")
	public String sortAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAllByOrderByNameAsc());
		return "courses";

	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class CourseNotFoundException extends RuntimeException {
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class TopicNotFoundException extends RuntimeException {
	}

}
