package lt.home.aggregator.service.impl;

import lombok.RequiredArgsConstructor;
import lt.home.aggregator.converter.CustomerConverter;
import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.entities.CustomerEntity;
import lt.home.aggregator.repositories.CustomerRepo;
import lt.home.aggregator.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerConverter customerConverter;

    @Override
    public Customer getById(UUID customerId) {
        CustomerEntity byId = customerRepo.findById(customerId).orElse(null);
        return customerConverter.entityToDto(byId);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = customerConverter.dtoToEntity(customer);
        customerEntity.setDateCreated(LocalDateTime.now());
        CustomerEntity saved = customerRepo.save(customerEntity);
        return customerConverter.entityToDto(saved);
    }
}
