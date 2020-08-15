package nc.oliweb.service.impl;

import nc.oliweb.service.AttributArticleService;
import nc.oliweb.domain.AttributArticle;
import nc.oliweb.repository.AttributArticleRepository;
import nc.oliweb.repository.search.AttributArticleSearchRepository;
import nc.oliweb.service.dto.AttributArticleDTO;
import nc.oliweb.service.mapper.AttributArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AttributArticle}.
 */
@Service
@Transactional
public class AttributArticleServiceImpl implements AttributArticleService {

    private final Logger log = LoggerFactory.getLogger(AttributArticleServiceImpl.class);

    private final AttributArticleRepository attributArticleRepository;

    private final AttributArticleMapper attributArticleMapper;

    private final AttributArticleSearchRepository attributArticleSearchRepository;

    public AttributArticleServiceImpl(AttributArticleRepository attributArticleRepository, AttributArticleMapper attributArticleMapper, AttributArticleSearchRepository attributArticleSearchRepository) {
        this.attributArticleRepository = attributArticleRepository;
        this.attributArticleMapper = attributArticleMapper;
        this.attributArticleSearchRepository = attributArticleSearchRepository;
    }

    /**
     * Save a attributArticle.
     *
     * @param attributArticleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AttributArticleDTO save(AttributArticleDTO attributArticleDTO) {
        log.debug("Request to save AttributArticle : {}", attributArticleDTO);
        AttributArticle attributArticle = attributArticleMapper.toEntity(attributArticleDTO);
        attributArticle = attributArticleRepository.save(attributArticle);
        AttributArticleDTO result = attributArticleMapper.toDto(attributArticle);
        attributArticleSearchRepository.save(attributArticle);
        return result;
    }

    /**
     * Get all the attributArticles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttributArticleDTO> findAll() {
        log.debug("Request to get all AttributArticles");
        return attributArticleRepository.findAll().stream()
            .map(attributArticleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one attributArticle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttributArticleDTO> findOne(Long id) {
        log.debug("Request to get AttributArticle : {}", id);
        return attributArticleRepository.findById(id)
            .map(attributArticleMapper::toDto);
    }

    /**
     * Delete the attributArticle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributArticle : {}", id);
        attributArticleRepository.deleteById(id);
        attributArticleSearchRepository.deleteById(id);
    }

    /**
     * Search for the attributArticle corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttributArticleDTO> search(String query) {
        log.debug("Request to search AttributArticles for query {}", query);
        return StreamSupport
            .stream(attributArticleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(attributArticleMapper::toDto)
        .collect(Collectors.toList());
    }
}
