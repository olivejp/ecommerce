package nc.oliweb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link nc.oliweb.domain.Category} entity.
 */
public class CategoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private Boolean transiant;


    private Long categoryParentId;
    
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

    public Boolean isTransiant() {
        return transiant;
    }

    public void setTransiant(Boolean transiant) {
        this.transiant = transiant;
    }

    public Long getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Long categoryId) {
        this.categoryParentId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", transiant='" + isTransiant() + "'" +
            ", categoryParentId=" + getCategoryParentId() +
            "}";
    }
}
