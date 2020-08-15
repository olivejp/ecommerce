package nc.oliweb.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link nc.oliweb.domain.AttributValue} entity.
 */
public class AttributValueDTO implements Serializable {
    
    private Long id;

    private String value;


    private Long attributId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getAttributId() {
        return attributId;
    }

    public void setAttributId(Long attributId) {
        this.attributId = attributId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributValueDTO)) {
            return false;
        }

        return id != null && id.equals(((AttributValueDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributValueDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", attributId=" + getAttributId() +
            "}";
    }
}
