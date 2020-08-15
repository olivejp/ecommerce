package nc.oliweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "date_payment")
    private Instant datePayment;

    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LigneCommande> lignes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public Commande date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getDatePayment() {
        return datePayment;
    }

    public Commande datePayment(Instant datePayment) {
        this.datePayment = datePayment;
        return this;
    }

    public void setDatePayment(Instant datePayment) {
        this.datePayment = datePayment;
    }

    public Set<LigneCommande> getLignes() {
        return lignes;
    }

    public Commande lignes(Set<LigneCommande> ligneCommandes) {
        this.lignes = ligneCommandes;
        return this;
    }

    public Commande addLignes(LigneCommande ligneCommande) {
        this.lignes.add(ligneCommande);
        ligneCommande.setCommande(this);
        return this;
    }

    public Commande removeLignes(LigneCommande ligneCommande) {
        this.lignes.remove(ligneCommande);
        ligneCommande.setCommande(null);
        return this;
    }

    public void setLignes(Set<LigneCommande> ligneCommandes) {
        this.lignes = ligneCommandes;
    }

    public Client getClient() {
        return client;
    }

    public Commande client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", datePayment='" + getDatePayment() + "'" +
            "}";
    }
}
