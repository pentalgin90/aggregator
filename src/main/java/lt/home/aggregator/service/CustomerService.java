package lt.home.aggregator.service;

import lt.home.aggregator.dto.Customer;

import java.util.UUID;

public interface CustomerService {
    Customer getById(UUID customerId);

    Customer save(Customer customer);
}
