package lt.home.aggregator.service.impl

import lt.home.aggregator.converter.CustomerConverter
import lt.home.aggregator.dto.Customer
import lt.home.aggregator.entities.CustomerEntity
import lt.home.aggregator.repositories.CustomerRepo
import spock.lang.Specification

class CustomerServiceImplSpec extends Specification {
    def customerRepo = Mock(CustomerRepo)
    def customerConverter = Mock(CustomerConverter)
    def customerService = new CustomerServiceImpl(customerRepo, customerConverter)

    def "Should return customer by id"() {
        given:
            def customerId = UUID.randomUUID()

            def customer = new Customer()
            customer.setCustomerId(customerId)

            def customerEntity = new CustomerEntity();
            customerEntity.setCustomerId(customerId)

            customerRepo.findById(customerId) >> Optional.of(customerEntity)
            customerConverter.entityToDto(customerEntity) >> customer
        when:
            def result = customerService.getById(customerId)
        then:
            result.getCustomerId().equals(customerId)
    }

    def "Should return null if customer is not exist"() {
        given:
            def customerId = UUID.randomUUID()

            def customer = new Customer()
            customer.setCustomerId(customerId)

            def customerEntity = new CustomerEntity();
            customerEntity.setCustomerId(customerId)

            customerRepo.findById(customerId) >> Optional.empty()
            customerConverter.entityToDto(customerEntity) >> customer
        when:
            def result = customerService.getById(customerId)
        then:
            !Objects.nonNull(result)
    }

    def "Should saved new customer"() {
        given:
            def customerId = UUID.randomUUID()

            def customer = new Customer()
            customer.setCustomerId(customerId)

            def customerEntity = new CustomerEntity();
            customerEntity.setCustomerId(customerId)

            customerConverter.dtoToEntity(customer) >> customerEntity
            customerRepo.save(customerEntity) >> customerEntity
            customerConverter.entityToDto(customerEntity) >> customer
        when:
            def result = customerService.save(customer)
        then:
            result.getCustomerId().equals(customerId)
    }
}
