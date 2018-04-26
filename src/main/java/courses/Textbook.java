package courses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Textbook {

	@Id
	@GeneratedValue
	private long id;

	/**
	 * The owner is the Many side in a ManyToOne since in a database, it has the
	 * foreign key. It "owns" the relationship.
	 */
	@ManyToOne
	private Course course;

	private String title;

	public long getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	private Textbook() {}
	
	public Textbook(String title, Course course) {
		this.title = title;
		this.course = course;
		// if we were cascading
		// course.addTextbook(this);
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

		return id == ((Textbook) obj).id;
	}
}
