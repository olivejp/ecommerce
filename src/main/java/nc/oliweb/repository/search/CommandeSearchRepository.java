package nc.oliweb.repository.search;

import nc.oliweb.domain.Commande;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Commande} entity.
 */
public interface CommandeSearchRepository extends ElasticsearchRepository<Commande, Long> {
}
