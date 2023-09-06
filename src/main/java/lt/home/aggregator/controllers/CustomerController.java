package lt.home.aggregator.controllers;

import lombok.RequiredArgsConstructor;
import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.service.ApplicationService;
import lt.home.aggregator.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return new ResponseEntity<>(applicationService.createAnApplication(customer), HttpStatus.CREATED);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId) {
        UUID id = UUID.fromString(customerId);
        Customer customer = customerService.getById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
