package nc.oliweb.service.mapper;


import nc.oliweb.domain.*;
import nc.oliweb.service.dto.LigneCommandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LigneCommande} and its DTO {@link LigneCommandeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModelArticleMapper.class, CommandeMapper.class})
public interface LigneCommandeMapper extends EntityMapper<LigneCommandeDTO, LigneCommande> {

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "commande.id", target = "commandeId")
    LigneCommandeDTO toDto(LigneCommande ligneCommande);

    @Mapping(source = "modelId", target = "model")
    @Mapping(source = "commandeId", target = "commande")
    LigneCommande toEntity(LigneCommandeDTO ligneCommandeDTO);

    default LigneCommande fromId(Long id) {
        if (id == null) {
            return null;
        }
        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setId(id);
        return ligneCommande;
    }
}
