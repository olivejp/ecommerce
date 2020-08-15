package nc.oliweb.service;

import nc.oliweb.service.dto.AttributArticleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.AttributArticle}.
 */
public interface AttributArticleService {

    /**
     * Save a attributArticle.
     *
     * @param attributArticleDTO the entity to save.
     * @return the persisted entity.
     */
    AttributArticleDTO save(AttributArticleDTO attributArticleDTO);

    /**
     * Get all the attributArticles.
     *
     * @return the list of entities.
     */
    List<AttributArticleDTO> findAll();


    /**
     * Get the "id" attributArticle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributArticleDTO> findOne(Long id);

    /**
     * Delete the "id" attributArticle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the attributArticle corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<AttributArticleDTO> search(String query);
}
