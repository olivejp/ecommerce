package nc.oliweb.repository.search;

import nc.oliweb.domain.AttributArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AttributArticle} entity.
 */
public interface AttributArticleSearchRepository extends ElasticsearchRepository<AttributArticle, Long> {
}
