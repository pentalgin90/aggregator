package lt.home.aggregator.service.impl;

import lombok.RequiredArgsConstructor;
import lt.home.aggregator.converter.ResponseConverter;
import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.entities.CustomerEntity;
import lt.home.aggregator.entities.OfferEntity;
import lt.home.aggregator.entities.ResponseEntity;
import lt.home.aggregator.repositories.CustomerRepo;
import lt.home.aggregator.repositories.ResponseRepo;
import lt.home.aggregator.service.OfferService;
import lt.home.aggregator.service.ResponseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepo responseRepo;
    private final CustomerRepo customerRepo;
    private final ResponseConverter responseConverter;
    private final OfferService offerService;

    @Override
    @Transactional
    public void saveResponseList(List<Response> responseList, UUID customerId) {
        Optional<CustomerEntity> optionalCustomer = customerRepo.findById(customerId);
        optionalCustomer.ifPresent(customerEntity -> responseList.stream()
                .map(responseConverter::dtoToEntity)
                .forEach(response -> {
                    response.setCustomerEntity(customerEntity);
                    responseRepo.save(response);
                }));
    }

    @Override
    @Transactional
    public void update(Response response) {
        ResponseEntity newResponseEntity = responseConverter.dtoToEntity(response);
        Optional<ResponseEntity> responseEntityOptional = responseRepo.findById(newResponseEntity.getResponseId());
        responseEntityOptional.ifPresent(responseEntity -> {
            OfferEntity offer = newResponseEntity.getOfferEntity();
            OfferEntity offerEntity = null;
            if (Objects.nonNull(offer)) {
                offer.setResponseEntity(responseEntity);
                offerEntity = offerService.save(offer);
            }
            responseEntity.setStatus(newResponseEntity.getStatus());
            responseEntity.setOfferEntity(offerEntity);
            responseRepo.save(responseEntity);
        });
    }

    @Override
    public List<ResponseEntity> getAllResponse() {
        return responseRepo.findAll();
    }
}
