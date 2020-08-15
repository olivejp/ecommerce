package nc.oliweb.service;

import nc.oliweb.service.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.Client}.
 */
public interface ClientService {

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    ClientDTO save(ClientDTO clientDTO);

    /**
     * Get all the clients.
     *
     * @return the list of entities.
     */
    List<ClientDTO> findAll();


    /**
     * Get the "id" client.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientDTO> findOne(Long id);

    /**
     * Delete the "id" client.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the client corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ClientDTO> search(String query);
}
