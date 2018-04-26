package courses;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CoursesPopulator implements CommandLineRunner {

	@Resource
	private CourseRepository courseRepo;

	@Resource
	private TextbookRepository textbookRepo;

	@Resource
	private TopicRepository topicRepo;

	@Override
	public void run(String... args) throws Exception {

		Topic java = new Topic("Java");
		java = topicRepo.save(java);
		Topic spring = new Topic("Spring");
		spring = topicRepo.save(spring);
		Topic tdd = new Topic("TDD");
		tdd = topicRepo.save(tdd);
		// we save on the *one* side of the relationship first
		Course java101 = new Course("Intro to Java", "Learn the fundamental concepts of Java Programming", java);
		java101 = courseRepo.save(java101);
		Course java102 = new Course("Advanced Software Design",
				"Learn how to test drive a fully functional application from start to finish!", java, tdd);
		java102 = courseRepo.save(java102);
		// then the things on the *many* side.
		textbookRepo.save(new Textbook("Head First Java", java101));
		textbookRepo.save(new Textbook("Head First Design Patterns", java102));
		textbookRepo.save(new Textbook("Clean Code", java102));
		textbookRepo.save(new Textbook("Intro to JPA", java102));

	}

}
