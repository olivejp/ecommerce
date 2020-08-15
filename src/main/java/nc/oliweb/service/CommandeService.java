package nc.oliweb.service;

import nc.oliweb.service.dto.CommandeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.Commande}.
 */
public interface CommandeService {

    /**
     * Save a commande.
     *
     * @param commandeDTO the entity to save.
     * @return the persisted entity.
     */
    CommandeDTO save(CommandeDTO commandeDTO);

    /**
     * Get all the commandes.
     *
     * @return the list of entities.
     */
    List<CommandeDTO> findAll();


    /**
     * Get the "id" commande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommandeDTO> findOne(Long id);

    /**
     * Delete the "id" commande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the commande corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CommandeDTO> search(String query);
}
