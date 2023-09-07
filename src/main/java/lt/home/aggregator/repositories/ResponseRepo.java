package lt.home.aggregator.repositories;

import lt.home.aggregator.entities.ResponseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResponseRepo extends ListCrudRepository<ResponseEntity, UUID> {
    @Query("select r from ResponseEntity r WHERE r.status = 'DRAFT'")
    List<ResponseEntity> findAllStatusDraft();
}
