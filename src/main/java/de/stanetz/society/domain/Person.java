/**
 *
 */
package de.stanetz.society.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author niels
 *
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Person {

	@GraphId
	private Long id;

	/** Possible Labels are Person or Leader*/
	@Labels
	private Set<String> labels = new HashSet<>();

	private String name;

	private Sex sex;

	@Relationship(type = "IS_PARENT", direction = Relationship.OUTGOING)
	private List<Person> parents = new ArrayList<>();

	@Relationship(type = "LEADS", direction = Relationship.OUTGOING)
	private List<Group> leadedGroups = new ArrayList<>();

	@Relationship(type = "IS_MEMBER", direction = Relationship.OUTGOING)
	private List<Group> groups = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getLeader() {
		return labels.contains(PersonLabels.Leader.name());
	}

	private void setLeader(boolean isLeader) {
		if (isLeader) {
			labels.add(PersonLabels.Leader.name());
		} else {
			labels.remove(PersonLabels.Leader.name());
		}
	}

	public List<Person> getParents() {
		return parents;
	}

	public void setParents(List<Person> parents) {
		this.parents = parents;
	}

	public List<Group> getLeadedGroups() {
		return leadedGroups;
	}

	public void setLeadedGroups(List<Group> leadedGroups) {
		this.setLeader(!CollectionUtils.isEmpty(leadedGroups));
		this.leadedGroups = leadedGroups;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}



}
