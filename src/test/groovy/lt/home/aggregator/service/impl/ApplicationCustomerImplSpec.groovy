package lt.home.aggregator.service.impl

import lt.home.aggregator.dto.Customer
import lt.home.aggregator.dto.TypeBank
import lt.home.aggregator.dto.interaction.Response
import lt.home.aggregator.dto.interaction.StatusResponse
import lt.home.aggregator.service.CustomerService
import lt.home.aggregator.service.ResponseService
import spock.lang.Specification

class ApplicationCustomerImplSpec extends Specification {
    def customerService = Mock(CustomerService);
    def responseService = Mock(ResponseService);
    def fastBankService = Mock(FastBankService);
    def solidBankService = Mock(SolidBankService);
    def applicationService = new ApplicationCustomerImpl(
            customerService,
            responseService,
            fastBankService,
            solidBankService
    )

    def "Should return customer with requests to bank"() {
        given:
            def customerId = UUID.randomUUID();

            def customer = new Customer();
            customer.setCustomerId(customerId)

            def responseFast = new Response()
            responseFast.setResponseId(UUID.randomUUID())
            responseFast.setStatus(StatusResponse.DRAFT)
            responseFast.setBank(TypeBank.FAST)

            def responseSolid = new Response()
            responseSolid.setResponseId(UUID.randomUUID())
            responseSolid.setStatus(StatusResponse.DRAFT)
            responseSolid.setBank(TypeBank.FAST)

            def listOfResponses = List.of(responseFast, responseSolid)

            fastBankService.makeRequest(customer) >> {}
            solidBankService.makeRequest(customer) >> {}
            fastBankService.applyForLoan() >> responseFast
            solidBankService.applyForLoan() >> responseSolid
            customerService.save(customer) >> customer
            responseService.saveResponseList(listOfResponses, customerId) >> {}
        when:
            def result = applicationService.createAnApplication(customer)
        then:
            !result.getResponseList().isEmpty()
    }

    def "Should return customer without requests to bank"() {
        given:
            def customerId = UUID.randomUUID();

            def customer = new Customer();
            customer.setCustomerId(customerId)

            def listOfResponses = new ArrayList<Response>()

            fastBankService.makeRequest(customer) >> {}
            solidBankService.makeRequest(customer) >> {}
            fastBankService.applyForLoan() >> null
            solidBankService.applyForLoan() >> null
            customerService.save(customer) >> customer
            responseService.saveResponseList(listOfResponses, customerId) >> {}
        when:
            def result = applicationService.createAnApplication(customer)
        then:
            result.getResponseList().isEmpty()
    }
}
