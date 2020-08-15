package nc.oliweb.repository;

import nc.oliweb.domain.Model;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Model entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
}
