package nc.oliweb.repository.search;

import nc.oliweb.domain.Client;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Client} entity.
 */
public interface ClientSearchRepository extends ElasticsearchRepository<Client, Long> {
}
