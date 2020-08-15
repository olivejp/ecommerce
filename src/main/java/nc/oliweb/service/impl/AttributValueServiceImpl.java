package nc.oliweb.service.impl;

import nc.oliweb.service.AttributValueService;
import nc.oliweb.domain.AttributValue;
import nc.oliweb.repository.AttributValueRepository;
import nc.oliweb.repository.search.AttributValueSearchRepository;
import nc.oliweb.service.dto.AttributValueDTO;
import nc.oliweb.service.mapper.AttributValueMapper;
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
 * Service Implementation for managing {@link AttributValue}.
 */
@Service
@Transactional
public class AttributValueServiceImpl implements AttributValueService {

    private final Logger log = LoggerFactory.getLogger(AttributValueServiceImpl.class);

    private final AttributValueRepository attributValueRepository;

    private final AttributValueMapper attributValueMapper;

    private final AttributValueSearchRepository attributValueSearchRepository;

    public AttributValueServiceImpl(AttributValueRepository attributValueRepository, AttributValueMapper attributValueMapper, AttributValueSearchRepository attributValueSearchRepository) {
        this.attributValueRepository = attributValueRepository;
        this.attributValueMapper = attributValueMapper;
        this.attributValueSearchRepository = attributValueSearchRepository;
    }

    /**
     * Save a attributValue.
     *
     * @param attributValueDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AttributValueDTO save(AttributValueDTO attributValueDTO) {
        log.debug("Request to save AttributValue : {}", attributValueDTO);
        AttributValue attributValue = attributValueMapper.toEntity(attributValueDTO);
        attributValue = attributValueRepository.save(attributValue);
        AttributValueDTO result = attributValueMapper.toDto(attributValue);
        attributValueSearchRepository.save(attributValue);
        return result;
    }

    /**
     * Get all the attributValues.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttributValueDTO> findAll() {
        log.debug("Request to get all AttributValues");
        return attributValueRepository.findAll().stream()
            .map(attributValueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one attributValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttributValueDTO> findOne(Long id) {
        log.debug("Request to get AttributValue : {}", id);
        return attributValueRepository.findById(id)
            .map(attributValueMapper::toDto);
    }

    /**
     * Delete the attributValue by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributValue : {}", id);
        attributValueRepository.deleteById(id);
        attributValueSearchRepository.deleteById(id);
    }

    /**
     * Search for the attributValue corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttributValueDTO> search(String query) {
        log.debug("Request to search AttributValues for query {}", query);
        return StreamSupport
            .stream(attributValueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(attributValueMapper::toDto)
        .collect(Collectors.toList());
    }
}
