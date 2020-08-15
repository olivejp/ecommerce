package nc.oliweb.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link nc.oliweb.domain.AttributArticle} entity.
 */
public class AttributArticleDTO implements Serializable {
    
    private Long id;

    private String value;


    private Long attributId;

    private Long articleId;
    
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

    public void setAttributId(Long attributValueId) {
        this.attributId = attributValueId;
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
        if (!(o instanceof AttributArticleDTO)) {
            return false;
        }

        return id != null && id.equals(((AttributArticleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributArticleDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", attributId=" + getAttributId() +
            ", articleId=" + getArticleId() +
            "}";
    }
}
