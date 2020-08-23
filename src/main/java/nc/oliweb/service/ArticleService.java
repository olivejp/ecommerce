package nc.oliweb.service;

import nc.oliweb.service.dto.ArticleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.Article}.
 */
public interface ArticleService {

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleDTO save(ArticleDTO articleDTO);

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" article.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleDTO> findOne(Long id);

    /**
     * Delete the "id" article.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the article corresponding to the query.
     *
     * @param query      the query of the search.
     * @param idCategory long idCategory.
     * @param pageable   the pagination information.
     * @return the list of entities.
     */
    Page<ArticleDTO> search(String query, Long idCategory, Pageable pageable);

    /**
     * Reindex all articles into ES instance
     *
     * @return void
     */
    void reindex();
}
