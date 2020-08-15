package nc.oliweb.web.rest;

import nc.oliweb.service.AttributArticleService;
import nc.oliweb.web.rest.errors.BadRequestAlertException;
import nc.oliweb.service.dto.AttributArticleDTO;

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
 * REST controller for managing {@link nc.oliweb.domain.AttributArticle}.
 */
@RestController
@RequestMapping("/api")
public class AttributArticleResource {

    private final Logger log = LoggerFactory.getLogger(AttributArticleResource.class);

    private static final String ENTITY_NAME = "attributArticle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributArticleService attributArticleService;

    public AttributArticleResource(AttributArticleService attributArticleService) {
        this.attributArticleService = attributArticleService;
    }

    /**
     * {@code POST  /attribut-articles} : Create a new attributArticle.
     *
     * @param attributArticleDTO the attributArticleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributArticleDTO, or with status {@code 400 (Bad Request)} if the attributArticle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribut-articles")
    public ResponseEntity<AttributArticleDTO> createAttributArticle(@RequestBody AttributArticleDTO attributArticleDTO) throws URISyntaxException {
        log.debug("REST request to save AttributArticle : {}", attributArticleDTO);
        if (attributArticleDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributArticleDTO result = attributArticleService.save(attributArticleDTO);
        return ResponseEntity.created(new URI("/api/attribut-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribut-articles} : Updates an existing attributArticle.
     *
     * @param attributArticleDTO the attributArticleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributArticleDTO,
     * or with status {@code 400 (Bad Request)} if the attributArticleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributArticleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribut-articles")
    public ResponseEntity<AttributArticleDTO> updateAttributArticle(@RequestBody AttributArticleDTO attributArticleDTO) throws URISyntaxException {
        log.debug("REST request to update AttributArticle : {}", attributArticleDTO);
        if (attributArticleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttributArticleDTO result = attributArticleService.save(attributArticleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributArticleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attribut-articles} : get all the attributArticles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributArticles in body.
     */
    @GetMapping("/attribut-articles")
    public List<AttributArticleDTO> getAllAttributArticles() {
        log.debug("REST request to get all AttributArticles");
        return attributArticleService.findAll();
    }

    /**
     * {@code GET  /attribut-articles/:id} : get the "id" attributArticle.
     *
     * @param id the id of the attributArticleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributArticleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribut-articles/{id}")
    public ResponseEntity<AttributArticleDTO> getAttributArticle(@PathVariable Long id) {
        log.debug("REST request to get AttributArticle : {}", id);
        Optional<AttributArticleDTO> attributArticleDTO = attributArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributArticleDTO);
    }

    /**
     * {@code DELETE  /attribut-articles/:id} : delete the "id" attributArticle.
     *
     * @param id the id of the attributArticleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribut-articles/{id}")
    public ResponseEntity<Void> deleteAttributArticle(@PathVariable Long id) {
        log.debug("REST request to delete AttributArticle : {}", id);
        attributArticleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/attribut-articles?query=:query} : search for the attributArticle corresponding
     * to the query.
     *
     * @param query the query of the attributArticle search.
     * @return the result of the search.
     */
    @GetMapping("/_search/attribut-articles")
    public List<AttributArticleDTO> searchAttributArticles(@RequestParam String query) {
        log.debug("REST request to search AttributArticles for query {}", query);
        return attributArticleService.search(query);
    }
}
