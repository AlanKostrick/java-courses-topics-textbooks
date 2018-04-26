package courses;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Topic {

	@Id
	@GeneratedValue
	private long id;
	private String name;

	/**
	 * We are considering Course the owner of this relationship. This is arbitrary
	 * in the case of ManyToMany relationships.
	 */
	@JsonIgnore
	@ManyToMany(mappedBy = "topics")
	private Collection<Course> courses;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<Course> getCourses() {
		return courses;
	}

	@SuppressWarnings("unused")
	private Topic() {
	}

	public Topic(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return id == ((Topic) obj).id;
	}

}
