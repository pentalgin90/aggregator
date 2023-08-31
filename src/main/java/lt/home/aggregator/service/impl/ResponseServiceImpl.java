package lt.home.aggregator.service.impl;

import lombok.RequiredArgsConstructor;
import lt.home.aggregator.converter.ResponseConverter;
import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.entities.CustomerEntity;
import lt.home.aggregator.entities.OfferEntity;
import lt.home.aggregator.entities.ResponseEntity;
import lt.home.aggregator.repositories.ResponseRepo;
import lt.home.aggregator.service.OfferService;
import lt.home.aggregator.service.ResponseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepo responseRepo;
    private final ResponseConverter responseConverter;
    private final OfferService offerService;

    @Override
    @Transactional
    public void saveResponseList(List<ResponseEntity> responseEntityList, CustomerEntity customerEntity) {
        responseEntityList.forEach(response -> {
            response.setCustomerEntity(customerEntity);
            responseRepo.save(response);
        });
    }
    @Override
    @Transactional
    public void update(Response response) {
        ResponseEntity newResponseEntity = responseConverter.dtoToEntity(response);
        ResponseEntity responseFromDB = responseRepo.getReferenceById(newResponseEntity.getResponseId());
        if (Objects.nonNull(responseFromDB)) {
            OfferEntity offer = newResponseEntity.getOfferEntity();
            OfferEntity offerEntity = null;
            if (Objects.nonNull(offer)) {
                offer.setResponseEntity(responseFromDB);
                offerEntity = offerService.save(offer);
            }
            responseFromDB.setStatus(newResponseEntity.getStatus());
            responseFromDB.setOfferEntity(offerEntity);
            responseRepo.save(responseFromDB);
        }
    }

    @Override
    public List<ResponseEntity> getAllResponse() {
        return responseRepo.findAll();
    }
}
