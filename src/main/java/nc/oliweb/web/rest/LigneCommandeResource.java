package nc.oliweb.web.rest;

import nc.oliweb.service.LigneCommandeService;
import nc.oliweb.web.rest.errors.BadRequestAlertException;
import nc.oliweb.service.dto.LigneCommandeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link nc.oliweb.domain.LigneCommande}.
 */
@RestController
@RequestMapping("/api")
public class LigneCommandeResource {

    private final Logger log = LoggerFactory.getLogger(LigneCommandeResource.class);

    private static final String ENTITY_NAME = "ligneCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneCommandeService ligneCommandeService;

    public LigneCommandeResource(LigneCommandeService ligneCommandeService) {
        this.ligneCommandeService = ligneCommandeService;
    }

    /**
     * {@code POST  /ligne-commandes} : Create a new ligneCommande.
     *
     * @param ligneCommandeDTO the ligneCommandeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneCommandeDTO, or with status {@code 400 (Bad Request)} if the ligneCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-commandes")
    public ResponseEntity<LigneCommandeDTO> createLigneCommande(@RequestBody LigneCommandeDTO ligneCommandeDTO) throws URISyntaxException {
        log.debug("REST request to save LigneCommande : {}", ligneCommandeDTO);
        if (ligneCommandeDTO.getId() != null) {
            throw new BadRequestAlertException("A new ligneCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneCommandeDTO result = ligneCommandeService.save(ligneCommandeDTO);
        return ResponseEntity.created(new URI("/api/ligne-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-commandes} : Updates an existing ligneCommande.
     *
     * @param ligneCommandeDTO the ligneCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the ligneCommandeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-commandes")
    public ResponseEntity<LigneCommandeDTO> updateLigneCommande(@RequestBody LigneCommandeDTO ligneCommandeDTO) throws URISyntaxException {
        log.debug("REST request to update LigneCommande : {}", ligneCommandeDTO);
        if (ligneCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LigneCommandeDTO result = ligneCommandeService.save(ligneCommandeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCommandeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ligne-commandes} : get all the ligneCommandes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneCommandes in body.
     */
    @GetMapping("/ligne-commandes")
    public List<LigneCommandeDTO> getAllLigneCommandes() {
        log.debug("REST request to get all LigneCommandes");
        return ligneCommandeService.findAll();
    }

    /**
     * {@code GET  /ligne-commandes/:id} : get the "id" ligneCommande.
     *
     * @param id the id of the ligneCommandeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneCommandeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-commandes/{id}")
    public ResponseEntity<LigneCommandeDTO> getLigneCommande(@PathVariable Long id) {
        log.debug("REST request to get LigneCommande : {}", id);
        Optional<LigneCommandeDTO> ligneCommandeDTO = ligneCommandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneCommandeDTO);
    }

    /**
     * {@code DELETE  /ligne-commandes/:id} : delete the "id" ligneCommande.
     *
     * @param id the id of the ligneCommandeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-commandes/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Long id) {
        log.debug("REST request to delete LigneCommande : {}", id);
        ligneCommandeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ligne-commandes?query=:query} : search for the ligneCommande corresponding
     * to the query.
     *
     * @param query the query of the ligneCommande search.
     * @return the result of the search.
     */
    @GetMapping("/_search/ligne-commandes")
    public List<LigneCommandeDTO> searchLigneCommandes(@RequestParam String query) {
        log.debug("REST request to search LigneCommandes for query {}", query);
        return ligneCommandeService.search(query);
    }
}
