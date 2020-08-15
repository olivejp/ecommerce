package nc.oliweb.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link nc.oliweb.domain.Commande} entity.
 */
public class CommandeDTO implements Serializable {
    
    private Long id;

    private Instant date;

    private Instant datePayment;


    private Long clientId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Instant datePayment) {
        this.datePayment = datePayment;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeDTO)) {
            return false;
        }

        return id != null && id.equals(((CommandeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", datePayment='" + getDatePayment() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
