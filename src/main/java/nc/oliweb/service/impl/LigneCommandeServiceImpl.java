package nc.oliweb.service.impl;

import nc.oliweb.service.LigneCommandeService;
import nc.oliweb.domain.LigneCommande;
import nc.oliweb.repository.LigneCommandeRepository;
import nc.oliweb.repository.search.LigneCommandeSearchRepository;
import nc.oliweb.service.dto.LigneCommandeDTO;
import nc.oliweb.service.mapper.LigneCommandeMapper;
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
 * Service Implementation for managing {@link LigneCommande}.
 */
@Service
@Transactional
public class LigneCommandeServiceImpl implements LigneCommandeService {

    private final Logger log = LoggerFactory.getLogger(LigneCommandeServiceImpl.class);

    private final LigneCommandeRepository ligneCommandeRepository;

    private final LigneCommandeMapper ligneCommandeMapper;

    private final LigneCommandeSearchRepository ligneCommandeSearchRepository;

    public LigneCommandeServiceImpl(LigneCommandeRepository ligneCommandeRepository, LigneCommandeMapper ligneCommandeMapper, LigneCommandeSearchRepository ligneCommandeSearchRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.ligneCommandeMapper = ligneCommandeMapper;
        this.ligneCommandeSearchRepository = ligneCommandeSearchRepository;
    }

    /**
     * Save a ligneCommande.
     *
     * @param ligneCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LigneCommandeDTO save(LigneCommandeDTO ligneCommandeDTO) {
        log.debug("Request to save LigneCommande : {}", ligneCommandeDTO);
        LigneCommande ligneCommande = ligneCommandeMapper.toEntity(ligneCommandeDTO);
        ligneCommande = ligneCommandeRepository.save(ligneCommande);
        LigneCommandeDTO result = ligneCommandeMapper.toDto(ligneCommande);
        ligneCommandeSearchRepository.save(ligneCommande);
        return result;
    }

    /**
     * Get all the ligneCommandes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LigneCommandeDTO> findAll() {
        log.debug("Request to get all LigneCommandes");
        return ligneCommandeRepository.findAll().stream()
            .map(ligneCommandeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ligneCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LigneCommandeDTO> findOne(Long id) {
        log.debug("Request to get LigneCommande : {}", id);
        return ligneCommandeRepository.findById(id)
            .map(ligneCommandeMapper::toDto);
    }

    /**
     * Delete the ligneCommande by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneCommande : {}", id);
        ligneCommandeRepository.deleteById(id);
        ligneCommandeSearchRepository.deleteById(id);
    }

    /**
     * Search for the ligneCommande corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LigneCommandeDTO> search(String query) {
        log.debug("Request to search LigneCommandes for query {}", query);
        return StreamSupport
            .stream(ligneCommandeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ligneCommandeMapper::toDto)
        .collect(Collectors.toList());
    }
}
