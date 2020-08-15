package nc.oliweb.service.impl;

import nc.oliweb.service.CommandeService;
import nc.oliweb.domain.Commande;
import nc.oliweb.repository.CommandeRepository;
import nc.oliweb.repository.search.CommandeSearchRepository;
import nc.oliweb.service.dto.CommandeDTO;
import nc.oliweb.service.mapper.CommandeMapper;
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
 * Service Implementation for managing {@link Commande}.
 */
@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {

    private final Logger log = LoggerFactory.getLogger(CommandeServiceImpl.class);

    private final CommandeRepository commandeRepository;

    private final CommandeMapper commandeMapper;

    private final CommandeSearchRepository commandeSearchRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository, CommandeMapper commandeMapper, CommandeSearchRepository commandeSearchRepository) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
        this.commandeSearchRepository = commandeSearchRepository;
    }

    /**
     * Save a commande.
     *
     * @param commandeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CommandeDTO save(CommandeDTO commandeDTO) {
        log.debug("Request to save Commande : {}", commandeDTO);
        Commande commande = commandeMapper.toEntity(commandeDTO);
        commande = commandeRepository.save(commande);
        CommandeDTO result = commandeMapper.toDto(commande);
        commandeSearchRepository.save(commande);
        return result;
    }

    /**
     * Get all the commandes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommandeDTO> findAll() {
        log.debug("Request to get all Commandes");
        return commandeRepository.findAll().stream()
            .map(commandeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one commande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CommandeDTO> findOne(Long id) {
        log.debug("Request to get Commande : {}", id);
        return commandeRepository.findById(id)
            .map(commandeMapper::toDto);
    }

    /**
     * Delete the commande by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commande : {}", id);
        commandeRepository.deleteById(id);
        commandeSearchRepository.deleteById(id);
    }

    /**
     * Search for the commande corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommandeDTO> search(String query) {
        log.debug("Request to search Commandes for query {}", query);
        return StreamSupport
            .stream(commandeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(commandeMapper::toDto)
        .collect(Collectors.toList());
    }
}
