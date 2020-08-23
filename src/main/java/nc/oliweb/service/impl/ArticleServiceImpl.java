package nc.oliweb.service.impl;

import nc.oliweb.service.ArticleService;
import nc.oliweb.domain.Article;
import nc.oliweb.repository.ArticleRepository;
import nc.oliweb.repository.search.ArticleSearchRepository;
import nc.oliweb.service.dto.ArticleDTO;
import nc.oliweb.service.mapper.ArticleMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Article}.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private final ArticleSearchRepository articleSearchRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper, ArticleSearchRepository articleSearchRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.articleSearchRepository = articleSearchRepository;
    }

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {
        log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        ArticleDTO result = articleMapper.toDto(article);
        articleSearchRepository.save(article);
        return result;
    }

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAll(pageable)
            .map(articleMapper::toDto);
    }


    /**
     * Get one article by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleDTO> findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findById(id)
            .map(articleMapper::toDto);
    }

    /**
     * Delete the article by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
        articleSearchRepository.deleteById(id);
    }

    /**
     * Search for the article corresponding to the query.
     *
     * @param query      the query of the search.
     * @param idCategory could be null. If specified we search on category.id field with a must term query.
     * @param pageable   the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDTO> search(String query, @Nullable Long idCategory, Pageable pageable) {
        log.debug("Request to search for a page of Articles for query {}", query);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNumber(query)) {
            boolQueryBuilder.should(termQuery("price", query));
        }

        if (idCategory != null) {
            boolQueryBuilder.must(termQuery("category.id", idCategory));
        }

        boolQueryBuilder
            .must(QueryBuilders.boolQuery()
                .should(wildcardQuery("name", "*" + query + "*"))
                .should(wildcardQuery("description", "*" + query + "*"))
            );

        return articleSearchRepository.search(boolQueryBuilder, pageable)
            .map(articleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void reindex() {
        log.debug("Request to reindex Articles");
        articleRepository.findAll().forEach(articleSearchRepository::save);
    }
}
