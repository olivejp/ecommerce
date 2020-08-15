package nc.oliweb.repository;

import nc.oliweb.domain.AttributValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttributValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributValueRepository extends JpaRepository<AttributValue, Long> {
}
