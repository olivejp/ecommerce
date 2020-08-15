package nc.oliweb.web.rest;

import nc.oliweb.domain.LigneCommande;
import nc.oliweb.repository.LigneCommandeRepository;
import nc.oliweb.repository.search.LigneCommandeSearchRepository;
import nc.oliweb.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link nc.oliweb.domain.LigneCommande}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LigneCommandeResource {

    private final Logger log = LoggerFactory.getLogger(LigneCommandeResource.class);

    private static final String ENTITY_NAME = "ligneCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneCommandeRepository ligneCommandeRepository;

    private final LigneCommandeSearchRepository ligneCommandeSearchRepository;

    public LigneCommandeResource(LigneCommandeRepository ligneCommandeRepository, LigneCommandeSearchRepository ligneCommandeSearchRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.ligneCommandeSearchRepository = ligneCommandeSearchRepository;
    }

    /**
     * {@code POST  /ligne-commandes} : Create a new ligneCommande.
     *
     * @param ligneCommande the ligneCommande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneCommande, or with status {@code 400 (Bad Request)} if the ligneCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-commandes")
    public ResponseEntity<LigneCommande> createLigneCommande(@RequestBody LigneCommande ligneCommande) throws URISyntaxException {
        log.debug("REST request to save LigneCommande : {}", ligneCommande);
        if (ligneCommande.getId() != null) {
            throw new BadRequestAlertException("A new ligneCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneCommande result = ligneCommandeRepository.save(ligneCommande);
        ligneCommandeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ligne-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-commandes} : Updates an existing ligneCommande.
     *
     * @param ligneCommande the ligneCommande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCommande,
     * or with status {@code 400 (Bad Request)} if the ligneCommande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneCommande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-commandes")
    public ResponseEntity<LigneCommande> updateLigneCommande(@RequestBody LigneCommande ligneCommande) throws URISyntaxException {
        log.debug("REST request to update LigneCommande : {}", ligneCommande);
        if (ligneCommande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LigneCommande result = ligneCommandeRepository.save(ligneCommande);
        ligneCommandeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCommande.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ligne-commandes} : get all the ligneCommandes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneCommandes in body.
     */
    @GetMapping("/ligne-commandes")
    public List<LigneCommande> getAllLigneCommandes() {
        log.debug("REST request to get all LigneCommandes");
        return ligneCommandeRepository.findAll();
    }

    /**
     * {@code GET  /ligne-commandes/:id} : get the "id" ligneCommande.
     *
     * @param id the id of the ligneCommande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneCommande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-commandes/{id}")
    public ResponseEntity<LigneCommande> getLigneCommande(@PathVariable Long id) {
        log.debug("REST request to get LigneCommande : {}", id);
        Optional<LigneCommande> ligneCommande = ligneCommandeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ligneCommande);
    }

    /**
     * {@code DELETE  /ligne-commandes/:id} : delete the "id" ligneCommande.
     *
     * @param id the id of the ligneCommande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-commandes/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Long id) {
        log.debug("REST request to delete LigneCommande : {}", id);
        ligneCommandeRepository.deleteById(id);
        ligneCommandeSearchRepository.deleteById(id);
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
    public List<LigneCommande> searchLigneCommandes(@RequestParam String query) {
        log.debug("REST request to search LigneCommandes for query {}", query);
        return StreamSupport
            .stream(ligneCommandeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
