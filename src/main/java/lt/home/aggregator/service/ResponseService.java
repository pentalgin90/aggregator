package lt.home.aggregator.service;

import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.entities.CustomerEntity;
import lt.home.aggregator.entities.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ResponseService {
    void saveResponseList(List<Response> responseList, UUID customerId);
    void update(Response response);

    List<ResponseEntity> getAllResponse();
}
