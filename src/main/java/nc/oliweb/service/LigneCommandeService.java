package nc.oliweb.service;

import nc.oliweb.service.dto.LigneCommandeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link nc.oliweb.domain.LigneCommande}.
 */
public interface LigneCommandeService {

    /**
     * Save a ligneCommande.
     *
     * @param ligneCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    LigneCommandeDTO save(LigneCommandeDTO ligneCommandeDTO);

    /**
     * Get all the ligneCommandes.
     *
     * @return the list of entities.
     */
    List<LigneCommandeDTO> findAll();


    /**
     * Get the "id" ligneCommande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneCommandeDTO> findOne(Long id);

    /**
     * Delete the "id" ligneCommande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the ligneCommande corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LigneCommandeDTO> search(String query);
}
