package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.ModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Model} and its DTO {@link ModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleMapper.class})
public interface ModelArticleMapper extends EntityMapper<ModelDTO, Model> {

    @Mapping(source = "article.id", target = "articleId")
    ModelDTO toDto(Model model);

    @Mapping(target = "photoUrls", ignore = true)
    @Mapping(target = "removePhotoUrl", ignore = true)
    @Mapping(source = "articleId", target = "article")
    Model toEntity(ModelDTO modelDTO);

    default Model fromId(Long id) {
        if (id == null) {
            return null;
        }
        Model model = new Model();
        model.setId(id);
        return model;
    }
}
