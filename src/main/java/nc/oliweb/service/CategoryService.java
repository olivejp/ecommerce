package nc.oliweb.service;

import nc.oliweb.service.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.Category}.
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    List<CategoryDTO> findAll();


    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryDTO> findOne(Long id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the category corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    List<CategoryDTO> search(String query);

    /**
     * Reindex all categories into ES instance
     *
     * @return void
     */
    void reindex();

    /**
     * Search with queryStringQuery
     *
     * @param query
     * @return
     */
    List<CategoryDTO> searchQueryString(String query);

    List<CategoryDTO> getAvailableParents(long idCategory);
}
