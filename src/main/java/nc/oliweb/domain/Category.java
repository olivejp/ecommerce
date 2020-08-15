package nc.oliweb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "transiant")
    private Boolean transiant;

    @OneToOne
    @JoinColumn(unique = true)
    private Category categoryParent;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Article> articles = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Attribut> attributs = new HashSet<>();

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

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isTransiant() {
        return transiant;
    }

    public Category transiant(Boolean transiant) {
        this.transiant = transiant;
        return this;
    }

    public void setTransiant(Boolean transiant) {
        this.transiant = transiant;
    }

    public Category getCategoryParent() {
        return categoryParent;
    }

    public Category categoryParent(Category category) {
        this.categoryParent = category;
        return this;
    }

    public void setCategoryParent(Category category) {
        this.categoryParent = category;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Category articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Category addArticle(Article article) {
        this.articles.add(article);
        article.setCategory(this);
        return this;
    }

    public Category removeArticle(Article article) {
        this.articles.remove(article);
        article.setCategory(null);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Attribut> getAttributs() {
        return attributs;
    }

    public Category attributs(Set<Attribut> attributs) {
        this.attributs = attributs;
        return this;
    }

    public Category addAttribut(Attribut attribut) {
        this.attributs.add(attribut);
        attribut.setCategory(this);
        return this;
    }

    public Category removeAttribut(Attribut attribut) {
        this.attributs.remove(attribut);
        attribut.setCategory(null);
        return this;
    }

    public void setAttributs(Set<Attribut> attributs) {
        this.attributs = attributs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", transiant='" + isTransiant() + "'" +
            "}";
    }
}
