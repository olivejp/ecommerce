package nc.oliweb.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link nc.oliweb.domain.LigneCommande} entity.
 */
public class LigneCommandeDTO implements Serializable {
    
    private Long id;

    private Float total;

    private Integer number;


    private Long modelId;

    private Long commandeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneCommandeDTO)) {
            return false;
        }

        return id != null && id.equals(((LigneCommandeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneCommandeDTO{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", number=" + getNumber() +
            ", modelId=" + getModelId() +
            ", commandeId=" + getCommandeId() +
            "}";
    }
}
