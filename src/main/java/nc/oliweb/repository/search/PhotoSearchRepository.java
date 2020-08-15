package nc.oliweb.repository.search;

import nc.oliweb.domain.Photo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Photo} entity.
 */
public interface PhotoSearchRepository extends ElasticsearchRepository<Photo, Long> {
}
