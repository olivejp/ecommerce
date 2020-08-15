package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.PhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModelArticleMapper.class})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    @Mapping(source = "model.id", target = "modelId")
    PhotoDTO toDto(Photo photo);

    @Mapping(source = "modelId", target = "model")
    Photo toEntity(PhotoDTO photoDTO);

    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
