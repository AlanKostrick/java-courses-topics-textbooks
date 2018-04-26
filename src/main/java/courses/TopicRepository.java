package courses;

import org.springframework.data.repository.CrudRepository;


public interface TopicRepository extends CrudRepository<Topic, Long>{

	Topic findByName(String name);

	Topic findByNameIgnoreCaseLike(String topicName);
	

}
