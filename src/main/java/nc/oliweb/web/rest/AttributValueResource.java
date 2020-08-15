package nc.oliweb.web.rest;

import nc.oliweb.service.AttributValueService;
import nc.oliweb.web.rest.errors.BadRequestAlertException;
import nc.oliweb.service.dto.AttributValueDTO;

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
 * REST controller for managing {@link nc.oliweb.domain.AttributValue}.
 */
@RestController
@RequestMapping("/api")
public class AttributValueResource {

    private final Logger log = LoggerFactory.getLogger(AttributValueResource.class);

    private static final String ENTITY_NAME = "attributValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributValueService attributValueService;

    public AttributValueResource(AttributValueService attributValueService) {
        this.attributValueService = attributValueService;
    }

    /**
     * {@code POST  /attribut-values} : Create a new attributValue.
     *
     * @param attributValueDTO the attributValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributValueDTO, or with status {@code 400 (Bad Request)} if the attributValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribut-values")
    public ResponseEntity<AttributValueDTO> createAttributValue(@RequestBody AttributValueDTO attributValueDTO) throws URISyntaxException {
        log.debug("REST request to save AttributValue : {}", attributValueDTO);
        if (attributValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributValueDTO result = attributValueService.save(attributValueDTO);
        return ResponseEntity.created(new URI("/api/attribut-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribut-values} : Updates an existing attributValue.
     *
     * @param attributValueDTO the attributValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributValueDTO,
     * or with status {@code 400 (Bad Request)} if the attributValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribut-values")
    public ResponseEntity<AttributValueDTO> updateAttributValue(@RequestBody AttributValueDTO attributValueDTO) throws URISyntaxException {
        log.debug("REST request to update AttributValue : {}", attributValueDTO);
        if (attributValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttributValueDTO result = attributValueService.save(attributValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attribut-values} : get all the attributValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributValues in body.
     */
    @GetMapping("/attribut-values")
    public List<AttributValueDTO> getAllAttributValues() {
        log.debug("REST request to get all AttributValues");
        return attributValueService.findAll();
    }

    /**
     * {@code GET  /attribut-values/:id} : get the "id" attributValue.
     *
     * @param id the id of the attributValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribut-values/{id}")
    public ResponseEntity<AttributValueDTO> getAttributValue(@PathVariable Long id) {
        log.debug("REST request to get AttributValue : {}", id);
        Optional<AttributValueDTO> attributValueDTO = attributValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributValueDTO);
    }

    /**
     * {@code DELETE  /attribut-values/:id} : delete the "id" attributValue.
     *
     * @param id the id of the attributValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribut-values/{id}")
    public ResponseEntity<Void> deleteAttributValue(@PathVariable Long id) {
        log.debug("REST request to delete AttributValue : {}", id);
        attributValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/attribut-values?query=:query} : search for the attributValue corresponding
     * to the query.
     *
     * @param query the query of the attributValue search.
     * @return the result of the search.
     */
    @GetMapping("/_search/attribut-values")
    public List<AttributValueDTO> searchAttributValues(@RequestParam String query) {
        log.debug("REST request to search AttributValues for query {}", query);
        return attributValueService.search(query);
    }
}
