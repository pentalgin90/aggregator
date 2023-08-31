package lt.home.aggregator.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lt.home.aggregator.converter.CustomerConverter;
import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.entities.CustomerEntity;
import lt.home.aggregator.entities.ResponseEntity;
import lt.home.aggregator.repositories.CustomerRepo;
import lt.home.aggregator.service.CustomerService;
import lt.home.aggregator.service.FinancingService;
import lt.home.aggregator.service.ResponseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final ResponseService responseService;
    private final CustomerConverter customerConverter;
    private final FastBankService fastBankService;
    private final SolidBankService solidBankService;

    @Override
    @Transactional
    public Customer createAnApplication(Customer customer) {
        List<FinancingService> financingServiceList = List.of(fastBankService, solidBankService);
        List<Response> responseList = financingServiceList.stream()
                .map(financingService -> {
                    financingService.makeRequest(customer);
                    return financingService.applyForLoan();
                })
                .toList();
        customer.getResponseList().addAll(responseList);
        CustomerEntity customerEntity = customerConverter.dtoToEntity(customer);
        customerEntity.setDateCreated(LocalDateTime.now());
        CustomerEntity saved = customerRepo.save(customerEntity);
        responseService.saveResponseList(saved.getResponseEntityList(), saved);
        return customerConverter.entityToDto(saved);
    }

    @Override
    public Customer getById(UUID customerId) {
        CustomerEntity byId = customerRepo.findById(customerId).orElse(null);
        customerConverter.entityToDto(byId);
        return customerConverter.entityToDto(byId);
    }
}
