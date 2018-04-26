package courses;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;


public class CourseControllerTest {

	@InjectMocks
	private CourseController underTest;

	@Mock
	private CourseRepository courseRepo;
	
	@Mock
	private TopicRepository topicRepo;

	@Mock
	private Course course;
	private Long courseId;

	@Mock
	private Course anotherCourse;
	
	@Mock
	private Topic topic;
	
	@Mock
	private Topic anotherTopic;

	@Mock
	private Model model;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddSingleCourseToModel() {
		long arbitraryCourseId = 42;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));

		underTest.findOneCourse(arbitraryCourseId, model);

		verify(model).addAttribute("courses", course);
	}
	
	
	@Test
	public void shouldAddAllCoursesToModel() {
		Collection<Course> allCourses = asList(course, anotherCourse);
		when(courseRepo.findAll()).thenReturn(allCourses);
		
		underTest.findAllCourses(model);
		
		verify(model).addAttribute("courses", allCourses);
	}
	
	@Test
	public void shouldAddSingleTopicToModel() {
		long arbitraryTopicId = 42;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		
		underTest.findOneTopic(arbitraryTopicId, model);

		verify(model).addAttribute("topics", topic);
	}
	
	@Test
	public void shouldAddAllTopicsToModel() {
		Collection<Topic> allTopics = asList(topic, anotherTopic);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		underTest.findAllTopics(model);
		
		verify(model).addAttribute("topics", allTopics);
	}
	
	@Test
	public void shouldAddAdditionalCoursesToModel() {
		String topicName = "topic name";
		Topic newTopic = topicRepo.findByName(topicName);
		String courseName = "course name";
		String courseDescription = "course description";
		underTest.addCourse(courseName, courseDescription, topicName);
		Course newCourse = new Course(courseName, courseDescription, newTopic);
		when(courseRepo.save(newCourse)).thenReturn(newCourse);
	}

	@Test
	public void shouldRemoveCourseFromModelByName() {
		String courseName = course.getName();
		when(courseRepo.findByName(courseName)).thenReturn(course);
		underTest.deleteCourseByName(courseName);
		verify(courseRepo).delete(course);
	}

	@Test
	public void shouldRemoveCourseFromModelById() {
		underTest.deleteCourseById(courseId);
		verify(courseRepo).deleteById(courseId);
	}

}
