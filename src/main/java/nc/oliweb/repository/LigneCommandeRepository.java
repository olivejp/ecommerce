package nc.oliweb.repository;

import nc.oliweb.domain.LigneCommande;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LigneCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}
