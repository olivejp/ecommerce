package nc.oliweb.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link nc.oliweb.domain.Attribut} entity.
 */
public class AttributDTO implements Serializable {
    
    private Long id;

    private String name;


    private Long categoryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributDTO)) {
            return false;
        }

        return id != null && id.equals(((AttributDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
