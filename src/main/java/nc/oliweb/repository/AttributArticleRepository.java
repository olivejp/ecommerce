package nc.oliweb.repository;

import nc.oliweb.domain.AttributArticle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttributArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributArticleRepository extends JpaRepository<AttributArticle, Long> {
}
