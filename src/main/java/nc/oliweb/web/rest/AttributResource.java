package nc.oliweb.web.rest;

import nc.oliweb.service.AttributService;
import nc.oliweb.web.rest.errors.BadRequestAlertException;
import nc.oliweb.service.dto.AttributDTO;

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
 * REST controller for managing {@link nc.oliweb.domain.Attribut}.
 */
@RestController
@RequestMapping("/api")
public class AttributResource {

    private final Logger log = LoggerFactory.getLogger(AttributResource.class);

    private static final String ENTITY_NAME = "attribut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributService attributService;

    public AttributResource(AttributService attributService) {
        this.attributService = attributService;
    }

    /**
     * {@code POST  /attributs} : Create a new attribut.
     *
     * @param attributDTO the attributDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributDTO, or with status {@code 400 (Bad Request)} if the attribut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attributs")
    public ResponseEntity<AttributDTO> createAttribut(@RequestBody AttributDTO attributDTO) throws URISyntaxException {
        log.debug("REST request to save Attribut : {}", attributDTO);
        if (attributDTO.getId() != null) {
            throw new BadRequestAlertException("A new attribut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributDTO result = attributService.save(attributDTO);
        return ResponseEntity.created(new URI("/api/attributs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attributs} : Updates an existing attribut.
     *
     * @param attributDTO the attributDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributDTO,
     * or with status {@code 400 (Bad Request)} if the attributDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attributs")
    public ResponseEntity<AttributDTO> updateAttribut(@RequestBody AttributDTO attributDTO) throws URISyntaxException {
        log.debug("REST request to update Attribut : {}", attributDTO);
        if (attributDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttributDTO result = attributService.save(attributDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attributs} : get all the attributs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributs in body.
     */
    @GetMapping("/attributs")
    public List<AttributDTO> getAllAttributs() {
        log.debug("REST request to get all Attributs");
        return attributService.findAll();
    }

    /**
     * {@code GET  /attributs/:id} : get the "id" attribut.
     *
     * @param id the id of the attributDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attributs/{id}")
    public ResponseEntity<AttributDTO> getAttribut(@PathVariable Long id) {
        log.debug("REST request to get Attribut : {}", id);
        Optional<AttributDTO> attributDTO = attributService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributDTO);
    }

    /**
     * {@code DELETE  /attributs/:id} : delete the "id" attribut.
     *
     * @param id the id of the attributDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attributs/{id}")
    public ResponseEntity<Void> deleteAttribut(@PathVariable Long id) {
        log.debug("REST request to delete Attribut : {}", id);
        attributService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/attributs?query=:query} : search for the attribut corresponding
     * to the query.
     *
     * @param query the query of the attribut search.
     * @return the result of the search.
     */
    @GetMapping("/_search/attributs")
    public List<AttributDTO> searchAttributs(@RequestParam String query) {
        log.debug("REST request to search Attributs for query {}", query);
        return attributService.search(query);
    }
}
