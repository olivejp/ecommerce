package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "categoryParent.id", target = "categoryParentId")
    CategoryDTO toDto(Category category);

    @Mapping(source = "categoryParentId", target = "categoryParent")
    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "removeArticle", ignore = true)
    @Mapping(target = "attributs", ignore = true)
    @Mapping(target = "removeAttribut", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
