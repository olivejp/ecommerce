package nc.oliweb.repository.search;

import nc.oliweb.domain.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Article} entity.
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {
}
