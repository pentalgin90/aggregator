package lt.home.aggregator.service;

import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.entities.CustomerEntity;
import lt.home.aggregator.entities.ResponseEntity;

import java.util.List;

public interface ResponseService {
    void saveResponseList(List<ResponseEntity> responseEntityList, CustomerEntity customerEntity);
    void update(Response response);

    List<ResponseEntity> getAllResponse();
}
