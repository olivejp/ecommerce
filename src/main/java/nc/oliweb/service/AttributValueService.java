package nc.oliweb.service;

import nc.oliweb.service.dto.AttributValueDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.AttributValue}.
 */
public interface AttributValueService {

    /**
     * Save a attributValue.
     *
     * @param attributValueDTO the entity to save.
     * @return the persisted entity.
     */
    AttributValueDTO save(AttributValueDTO attributValueDTO);

    /**
     * Get all the attributValues.
     *
     * @return the list of entities.
     */
    List<AttributValueDTO> findAll();


    /**
     * Get the "id" attributValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributValueDTO> findOne(Long id);

    /**
     * Delete the "id" attributValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the attributValue corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<AttributValueDTO> search(String query);
}
