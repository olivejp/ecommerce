package nc.oliweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Attribut.
 */
@Entity
@Table(name = "attribut")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "attribut")
public class Attribut implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "attribut")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AttributValue> values = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "attributs", allowSetters = true)
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

    public Attribut name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AttributValue> getValues() {
        return values;
    }

    public Attribut values(Set<AttributValue> attributValues) {
        this.values = attributValues;
        return this;
    }

    public Attribut addValues(AttributValue attributValue) {
        this.values.add(attributValue);
        attributValue.setAttribut(this);
        return this;
    }

    public Attribut removeValues(AttributValue attributValue) {
        this.values.remove(attributValue);
        attributValue.setAttribut(null);
        return this;
    }

    public void setValues(Set<AttributValue> attributValues) {
        this.values = attributValues;
    }

    public Category getCategory() {
        return category;
    }

    public Attribut category(Category category) {
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
        if (!(o instanceof Attribut)) {
            return false;
        }
        return id != null && id.equals(((Attribut) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attribut{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
