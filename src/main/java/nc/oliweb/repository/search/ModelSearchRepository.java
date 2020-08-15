package nc.oliweb.repository.search;

import nc.oliweb.domain.Model;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Model} entity.
 */
public interface ModelSearchRepository extends ElasticsearchRepository<Model, Long> {
}
