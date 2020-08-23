package nc.oliweb.repository;

import nc.oliweb.domain.Category;

import nc.oliweb.service.dto.CategoryDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c ORDER BY c.id")
    List<Category> findAllOrderById();

    List<Category> findAllByCategoryParent_Id(Long id);

    @Query("SELECT u from Category u where u.id not in :idListCategory")
    List<Category> findAllByCategoryParentIdIsNotIn(List<Long> idListCategory);
}
