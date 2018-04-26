
package courses;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicRestController {
	
	@Resource
	private CourseRepository courseRepo;

	@Resource
	private TopicRepository topicRepo;

	@RequestMapping("")
	public Iterable<Topic> findAllTopics() {
		return topicRepo.findAll();
	}

	@RequestMapping("/{id}")
	public Optional<Topic> findOneTopic(@PathVariable long id) {
		return topicRepo.findById(id);
	}

	@RequestMapping("/{topicName}/courses")
	public Collection<Course> findAllCoursesByTopic(@PathVariable(value = "topicName") String topicName) {
		Topic topic = topicRepo.findByNameIgnoreCaseLike(topicName);
		// Collection<Course> courses = courseRepo.findByTopicsContains(topic);
		return courseRepo.findByTopicsContains(topic);
	}
}


