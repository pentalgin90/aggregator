package lt.home.aggregator.repositories;

import lt.home.aggregator.entities.ResponseEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResponseRepo extends ListCrudRepository<ResponseEntity, UUID> {
}
