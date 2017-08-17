package de.stanetz.society;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.stanetz.society.domain.Person;
import de.stanetz.society.domain.Sex;
import de.stanetz.society.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SocietyApplicationTests {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private Session springManagedSession;

	private static final Logger LOG = LoggerFactory.getLogger(SocietyApplicationTests.class);

	@Test
	public void contextLoads() {

		// Set up the Session
		SessionFactory sessionFactory = new SessionFactory("de.stanetz.society");
		Session session = sessionFactory.openSession();

		Person child = new Person("Child", Sex.OTHER);

		Person father = new Person("Father", Sex.MALE);
		Person mother = new Person("Mother", Sex.FEMALE);

		child.setParents(Arrays.asList(mother, father));

		LOG.info("Save");
		session.beginTransaction();
		// Persist the movie. This persists the actors as well.
		session.save(child);
		session.getTransaction().commit();
		LOG.info("Clear session");
		session.clear();

		// Load a movie
		Person loadedChild = session.load(Person.class, child.getId());
		Assert.assertEquals(2, loadedChild.getParents().size());
		Assert.assertNotSame(child, loadedChild);

		loadedChild.setParents(Arrays.asList(mother));

		LOG.info("Save and Remove Relation to " + father.getId());
		session.beginTransaction();
		session.save(loadedChild);
		session.getTransaction().commit();
		LOG.info("Clear session");
		session.clear();
		Person loadedChild2 = session.load(Person.class, child.getId());
		Assert.assertNotSame(loadedChild, loadedChild2);
		loadedChild = null;
		Assert.assertEquals(1, loadedChild2.getParents().size());

		LOG.info("Delete it");
		session.beginTransaction();
		session.delete(mother);
		session.delete(father);
		session.delete(child);
		session.getTransaction().commit();
	}

	@Test
	@Transactional
	public void repoTest() {

		Person child = new Person("Child", Sex.OTHER);

		Person father = new Person("Father", Sex.MALE);
		Person mother = new Person("Mother", Sex.FEMALE);

		child.setParents(Arrays.asList(mother, father));

		LOG.info("Save");
		// Persist the movie. This persists the actors as well.
		personRepository.save(child);

		LOG.info("Clear session");
		springManagedSession.clear();

		// Load a movie
		Person loadedChild = personRepository.findOne(child.getId());
		Assert.assertEquals(2, loadedChild.getParents().size());
		Assert.assertNotSame(child, loadedChild);

		loadedChild.setParents(Arrays.asList(mother));

		LOG.info("Save and Remove Relation to " + father.getId());
		personRepository.save(loadedChild);
		LOG.info("Clear session");
		springManagedSession.clear();
		Person loadedChild2 = personRepository.findOne(child.getId());
		Assert.assertNotSame(loadedChild, loadedChild2);
		loadedChild = null;
		Assert.assertEquals(1, loadedChild2.getParents().size());

		LOG.info("Delete it");
		personRepository.delete(mother);
		personRepository.delete(father);
		personRepository.delete(child);

	}

}
