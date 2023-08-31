package lt.home.aggregator.service;

import lt.home.aggregator.dto.Customer;

import java.util.UUID;

public interface CustomerService {
    Customer createAnApplication(Customer customer);

    Customer getById(UUID customerId);
}
