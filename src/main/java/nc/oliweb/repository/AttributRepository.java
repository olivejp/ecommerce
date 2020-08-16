package nc.oliweb.repository;

import nc.oliweb.domain.Attribut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Attribut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributRepository extends JpaRepository<Attribut, Long> {
    List<Attribut> findAllByCategoryId(Long id);
}
