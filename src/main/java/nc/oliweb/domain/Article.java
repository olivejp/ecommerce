package nc.oliweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "price")
    private Float price;

    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Model> models = new HashSet<>();

    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AttributArticle> attributs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "articles", allowSetters = true)
    private Category category;

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

    public Article name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Article description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Article available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Float getPrice() {
        return price;
    }

    public Article price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<Model> getModels() {
        return models;
    }

    public Article models(Set<Model> models) {
        this.models = models;
        return this;
    }

    public Article addModels(Model model) {
        this.models.add(model);
        model.setArticle(this);
        return this;
    }

    public Article removeModels(Model model) {
        this.models.remove(model);
        model.setArticle(null);
        return this;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    public Set<AttributArticle> getAttributs() {
        return attributs;
    }

    public Article attributs(Set<AttributArticle> attributArticles) {
        this.attributs = attributArticles;
        return this;
    }

    public Article addAttribut(AttributArticle attributArticle) {
        this.attributs.add(attributArticle);
        attributArticle.setArticle(this);
        return this;
    }

    public Article removeAttribut(AttributArticle attributArticle) {
        this.attributs.remove(attributArticle);
        attributArticle.setArticle(null);
        return this;
    }

    public void setAttributs(Set<AttributArticle> attributArticles) {
        this.attributs = attributArticles;
    }

    public Category getCategory() {
        return category;
    }

    public Article category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", available='" + isAvailable() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
