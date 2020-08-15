package nc.oliweb.service;

import nc.oliweb.service.dto.ModelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.Model}.
 */
public interface ModelService {

    /**
     * Save a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    ModelDTO save(ModelDTO modelDTO);

    /**
     * Get all the models.
     *
     * @return the list of entities.
     */
    List<ModelDTO> findAll();


    /**
     * Get the "id" model.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModelDTO> findOne(Long id);

    /**
     * Delete the "id" model.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the model corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ModelDTO> search(String query);
}
