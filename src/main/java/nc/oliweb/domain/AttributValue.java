package nc.oliweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A AttributValue.
 */
@Entity
@Table(name = "attribut_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "attributvalue")
public class AttributValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = "values", allowSetters = true)
    private Attribut attribut;

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

    public AttributValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Attribut getAttribut() {
        return attribut;
    }

    public AttributValue attribut(Attribut attribut) {
        this.attribut = attribut;
        return this;
    }

    public void setAttribut(Attribut attribut) {
        this.attribut = attribut;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributValue)) {
            return false;
        }
        return id != null && id.equals(((AttributValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributValue{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
