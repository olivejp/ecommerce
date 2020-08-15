package nc.oliweb.service.impl;

import nc.oliweb.service.ModelService;
import nc.oliweb.domain.Model;
import nc.oliweb.repository.ModelRepository;
import nc.oliweb.repository.search.ModelSearchRepository;
import nc.oliweb.service.dto.ModelDTO;
import nc.oliweb.service.mapper.ModelArticleMapper;
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
 * Service Implementation for managing {@link Model}.
 */
@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    private final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    private final ModelRepository modelRepository;

    private final ModelArticleMapper modelArticleMapper;

    private final ModelSearchRepository modelSearchRepository;

    public ModelServiceImpl(ModelRepository modelRepository, ModelArticleMapper modelArticleMapper, ModelSearchRepository modelSearchRepository) {
        this.modelRepository = modelRepository;
        this.modelArticleMapper = modelArticleMapper;
        this.modelSearchRepository = modelSearchRepository;
    }

    /**
     * Save a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ModelDTO save(ModelDTO modelDTO) {
        log.debug("Request to save Model : {}", modelDTO);
        Model model = modelArticleMapper.toEntity(modelDTO);
        model = modelRepository.save(model);
        ModelDTO result = modelArticleMapper.toDto(model);
        modelSearchRepository.save(model);
        return result;
    }

    /**
     * Get all the models.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModelDTO> findAll() {
        log.debug("Request to get all Models");
        return modelRepository.findAll().stream()
            .map(modelArticleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one model by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModelDTO> findOne(Long id) {
        log.debug("Request to get Model : {}", id);
        return modelRepository.findById(id)
            .map(modelArticleMapper::toDto);
    }

    /**
     * Delete the model by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Model : {}", id);
        modelRepository.deleteById(id);
        modelSearchRepository.deleteById(id);
    }

    /**
     * Search for the model corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModelDTO> search(String query) {
        log.debug("Request to search Models for query {}", query);
        return StreamSupport
            .stream(modelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(modelArticleMapper::toDto)
        .collect(Collectors.toList());
    }
}
