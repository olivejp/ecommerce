package nc.oliweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Model.
 */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "model")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count")
    private Long count;

    @OneToMany(mappedBy = "model")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Photo> photoUrls = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "models", allowSetters = true)
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Model name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public Model count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Set<Photo> getPhotoUrls() {
        return photoUrls;
    }

    public Model photoUrls(Set<Photo> photos) {
        this.photoUrls = photos;
        return this;
    }

    public Model addPhotoUrl(Photo photo) {
        this.photoUrls.add(photo);
        photo.setModel(this);
        return this;
    }

    public Model removePhotoUrl(Photo photo) {
        this.photoUrls.remove(photo);
        photo.setModel(null);
        return this;
    }

    public void setPhotoUrls(Set<Photo> photos) {
        this.photoUrls = photos;
    }

    public Article getArticle() {
        return article;
    }

    public Model article(Article article) {
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
        if (!(o instanceof Model)) {
            return false;
        }
        return id != null && id.equals(((Model) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Model{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", count=" + getCount() +
            "}";
    }
}
