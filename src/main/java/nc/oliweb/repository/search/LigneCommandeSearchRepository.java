package nc.oliweb.repository.search;

import nc.oliweb.domain.LigneCommande;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link LigneCommande} entity.
 */
public interface LigneCommandeSearchRepository extends ElasticsearchRepository<LigneCommande, Long> {
}
