package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.ArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

    @Mapping(source = "category.id", target = "categoryId")
    ArticleDTO toDto(Article article);

    @Mapping(target = "models", ignore = true)
    @Mapping(target = "removeModels", ignore = true)
    @Mapping(target = "attributs", ignore = true)
    @Mapping(target = "removeAttribut", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    Article toEntity(ArticleDTO articleDTO);

    default Article fromId(Long id) {
        if (id == null) {
            return null;
        }
        Article article = new Article();
        article.setId(id);
        return article;
    }
}
