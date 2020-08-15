package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.AttributArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributArticle} and its DTO {@link AttributArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = {AttributValueMapper.class, ArticleMapper.class})
public interface AttributArticleMapper extends EntityMapper<AttributArticleDTO, AttributArticle> {

    @Mapping(source = "attribut.id", target = "attributId")
    @Mapping(source = "article.id", target = "articleId")
    AttributArticleDTO toDto(AttributArticle attributArticle);

    @Mapping(source = "attributId", target = "attribut")
    @Mapping(source = "articleId", target = "article")
    AttributArticle toEntity(AttributArticleDTO attributArticleDTO);

    default AttributArticle fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttributArticle attributArticle = new AttributArticle();
        attributArticle.setId(id);
        return attributArticle;
    }
}
