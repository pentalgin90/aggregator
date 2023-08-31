package lt.home.aggregator.repositories;

import lt.home.aggregator.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CustomerRepo extends CrudRepository<CustomerEntity, UUID> {
}
