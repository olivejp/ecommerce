package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.AttributValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributValue} and its DTO {@link AttributValueDTO}.
 */
@Mapper(componentModel = "spring", uses = {AttributMapper.class})
public interface AttributValueMapper extends EntityMapper<AttributValueDTO, AttributValue> {

    @Mapping(source = "attribut.id", target = "attributId")
    AttributValueDTO toDto(AttributValue attributValue);

    @Mapping(source = "attributId", target = "attribut")
    AttributValue toEntity(AttributValueDTO attributValueDTO);

    default AttributValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttributValue attributValue = new AttributValue();
        attributValue.setId(id);
        return attributValue;
    }
}
