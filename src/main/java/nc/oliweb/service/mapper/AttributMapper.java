package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.AttributDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attribut} and its DTO {@link AttributDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface AttributMapper extends EntityMapper<AttributDTO, Attribut> {

    @Mapping(source = "category.id", target = "categoryId")
    AttributDTO toDto(Attribut attribut);

    @Mapping(target = "values", ignore = true)
    @Mapping(target = "removeValues", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    Attribut toEntity(AttributDTO attributDTO);

    default Attribut fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attribut attribut = new Attribut();
        attribut.setId(id);
        return attribut;
    }
}
