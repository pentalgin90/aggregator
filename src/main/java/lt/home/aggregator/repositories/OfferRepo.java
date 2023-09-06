package lt.home.aggregator.repositories;

import lt.home.aggregator.entities.OfferEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OfferRepo extends CrudRepository<OfferEntity, UUID> {
}
