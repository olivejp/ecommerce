package nc.oliweb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link nc.oliweb.domain.Model} entity.
 */
public class ModelDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private Long count;


    private Long articleId;
    
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelDTO)) {
            return false;
        }

        return id != null && id.equals(((ModelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", count=" + getCount() +
            ", articleId=" + getArticleId() +
            "}";
    }
}
