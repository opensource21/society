/**
 *
 */
package de.stanetz.society.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import de.stanetz.society.domain.Person;

/**
 * {@link Repository} to access {@link Person}.
 * @author niels
 *
 */
@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

}
