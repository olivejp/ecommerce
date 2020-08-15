package nc.oliweb.service.impl;

import nc.oliweb.service.AttributService;
import nc.oliweb.domain.Attribut;
import nc.oliweb.repository.AttributRepository;
import nc.oliweb.repository.search.AttributSearchRepository;
import nc.oliweb.service.dto.AttributDTO;
import nc.oliweb.service.mapper.AttributMapper;
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
 * Service Implementation for managing {@link Attribut}.
 */
@Service
@Transactional
public class AttributServiceImpl implements AttributService {

    private final Logger log = LoggerFactory.getLogger(AttributServiceImpl.class);

    private final AttributRepository attributRepository;

    private final AttributMapper attributMapper;

    private final AttributSearchRepository attributSearchRepository;

    public AttributServiceImpl(AttributRepository attributRepository, AttributMapper attributMapper, AttributSearchRepository attributSearchRepository) {
        this.attributRepository = attributRepository;
        this.attributMapper = attributMapper;
        this.attributSearchRepository = attributSearchRepository;
    }

    /**
     * Save a attribut.
     *
     * @param attributDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AttributDTO save(AttributDTO attributDTO) {
        log.debug("Request to save Attribut : {}", attributDTO);
        Attribut attribut = attributMapper.toEntity(attributDTO);
        attribut = attributRepository.save(attribut);
        AttributDTO result = attributMapper.toDto(attribut);
        attributSearchRepository.save(attribut);
        return result;
    }

    /**
     * Get all the attributs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttributDTO> findAll() {
        log.debug("Request to get all Attributs");
        return attributRepository.findAll().stream()
            .map(attributMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one attribut by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttributDTO> findOne(Long id) {
        log.debug("Request to get Attribut : {}", id);
        return attributRepository.findById(id)
            .map(attributMapper::toDto);
    }

    /**
     * Delete the attribut by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attribut : {}", id);
        attributRepository.deleteById(id);
        attributSearchRepository.deleteById(id);
    }

    /**
     * Search for the attribut corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttributDTO> search(String query) {
        log.debug("Request to search Attributs for query {}", query);
        return StreamSupport
            .stream(attributSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(attributMapper::toDto)
        .collect(Collectors.toList());
    }
}
