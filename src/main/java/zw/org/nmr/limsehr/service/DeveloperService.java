package zw.org.nmr.limsehr.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.org.nmr.limsehr.domain.Developer;

/**
 * Service Interface for managing {@link Developer}.
 */
public interface DeveloperService {
    /**
     * Save a developer.
     *
     * @param developer the entity to save.
     * @return the persisted entity.
     */
    Developer save(Developer developer);

    /**
     * Updates a developer.
     *
     * @param developer the entity to update.
     * @return the persisted entity.
     */
    Developer update(Developer developer);

    /**
     * Partially updates a developer.
     *
     * @param developer the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Developer> partialUpdate(Developer developer);

    /**
     * Get all the developers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Developer> findAll(Pageable pageable);

    /**
     * Get the "id" developer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Developer> findOne(Long id);

    /**
     * Delete the "id" developer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
