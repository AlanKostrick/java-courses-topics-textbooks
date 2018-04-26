package courses;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

	Course findByName(String name);

	Collection<Course> findByTopicsContains(Topic topic);

	Collection<Course> findByTopicsId(Long id);

	Collection<Course> findAllByOrderByNameAsc();
}
