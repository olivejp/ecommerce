package nc.oliweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A AttributArticle.
 */
@Entity
@Table(name = "attribut_article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "attributarticle")
public class AttributArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @OneToOne
    @JoinColumn(unique = true)
    private AttributValue attribut;

    @ManyToOne
    @JsonIgnoreProperties(value = "attributs", allowSetters = true)
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public AttributArticle value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AttributValue getAttribut() {
        return attribut;
    }

    public AttributArticle attribut(AttributValue attributValue) {
        this.attribut = attributValue;
        return this;
    }

    public void setAttribut(AttributValue attributValue) {
        this.attribut = attributValue;
    }

    public Article getArticle() {
        return article;
    }

    public AttributArticle article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributArticle)) {
            return false;
        }
        return id != null && id.equals(((AttributArticle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributArticle{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
