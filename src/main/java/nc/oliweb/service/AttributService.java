package nc.oliweb.service;

import nc.oliweb.service.dto.AttributDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.Attribut}.
 */
public interface AttributService {

    /**
     * Save a attribut.
     *
     * @param attributDTO the entity to save.
     * @return the persisted entity.
     */
    AttributDTO save(AttributDTO attributDTO);

    /**
     * Get all the attributs.
     *
     * @return the list of entities.
     */
    List<AttributDTO> findAll();

    /**
     * Get all the attributs for specified category.
     *
     * @return the list of entities.
     */
    List<AttributDTO> findAllByCategoryId(Long idCategory);

    /**
     * Get the "id" attribut.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributDTO> findOne(Long id);

    /**
     * Delete the "id" attribut.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the attribut corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @return the list of entities.
     */
    List<AttributDTO> search(String query);
}
