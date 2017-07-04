/**
 *
 */
package de.stanetz.society.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import de.stanetz.society.domain.Group;

/**
 * {@link Repository} to access {@link Group}.
 * @author niels
 *
 */
@RepositoryRestResource(collectionResourceRel = "group", path = "group")
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {

}
