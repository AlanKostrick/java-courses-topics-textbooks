package courses;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Course {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;

	/**
	 * Textbook owns the relationship because it has the foreign key to Course.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "course")
	private Collection<Textbook> textbooks;

	/**
	 * We are thinking of Course as the owner, so it does not get a "mappedBy"
	 * attribute. This is arbitrary in the case of ManyToMany relationships.
	 */

	@ManyToMany
	private Collection<Topic> topics;

	public Collection<Textbook> getTextbooks() {
		return textbooks;
	}

	public Collection<Topic> getTopics() {
		return topics;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Collection<String> getTopicsUrls() {
		Collection<String> urls = new ArrayList<>();
		for (Topic t : topics) {
			urls.add(format("/courses/%d/topics/%s", this.getId(), t.getName()));
		}
		return urls;
	}

	@SuppressWarnings("unused")
	private Course() {
	}

	public Course(String name, String description, Topic... topics) {
		this.name = name;
		this.description = description;
		this.topics = new HashSet<>(asList(topics));
	}

	// needed when running jpa tests so that we can flush out the proper objects by
	// id
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
		return id == ((Course) obj).id;
	}

}
