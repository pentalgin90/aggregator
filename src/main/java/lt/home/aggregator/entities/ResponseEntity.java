package lt.home.aggregator.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.home.aggregator.dto.TypeBank;
import lt.home.aggregator.dto.interaction.StatusResponse;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "responses")
public class ResponseEntity {
    @Id
    @Column(name = "response_id")
    private UUID responseId;

    @Column(name = "bank")
    @Enumerated(EnumType.STRING)
    private TypeBank bank;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusResponse status;

    @OneToOne(mappedBy = "responseEntity", cascade = CascadeType.ALL)
    private OfferEntity offerEntity;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseEntity that = (ResponseEntity) o;
        return Objects.equals(responseId, that.responseId) && bank == that.bank && status == that.status && Objects.equals(offerEntity, that.offerEntity) && Objects.equals(customerEntity, that.customerEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseId, bank, status, offerEntity, customerEntity);
    }
}
