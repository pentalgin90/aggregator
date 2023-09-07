package lt.home.aggregator.service.impl

import lt.home.aggregator.converter.ResponseConverter
import lt.home.aggregator.dto.interaction.Response
import lt.home.aggregator.dto.interaction.StatusResponse
import lt.home.aggregator.entities.CustomerEntity
import lt.home.aggregator.entities.OfferEntity
import lt.home.aggregator.entities.ResponseEntity
import lt.home.aggregator.repositories.CustomerRepo
import lt.home.aggregator.repositories.ResponseRepo
import lt.home.aggregator.service.OfferService
import spock.lang.Specification

class ResponseServiceImplSpec extends Specification {
    def responseRepo = Mock(ResponseRepo)
    def customerRepo = Mock(CustomerRepo)
    def responseConverter = Mock(ResponseConverter)
    def offerService = Mock(OfferService)
    def responseService = new ResponseServiceImpl(responseRepo, customerRepo, responseConverter, offerService)

    def "Should return list responses with status DRAFT"() {
        given:
            def response = new ResponseEntity()
            response.setResponseId(UUID.randomUUID())
            response.setStatus(StatusResponse.DRAFT)

            def responseList = List.of(response)

            responseRepo.findAllStatusDraft() >> responseList
        when:
            def result = responseService.allResponseDraft
        then:
            result.stream().allMatch {item -> item.status == StatusResponse.DRAFT}
    }

    def "Should saved response list by customer id"() {
        given:
            def customerId = UUID.randomUUID()

            def responseId = UUID.randomUUID()

            def customerEntity = new CustomerEntity();
            customerEntity.setCustomerId(customerId)

            def response = new Response()
            response.setResponseId(responseId)

            def responseEntity = new ResponseEntity()
            responseEntity.setResponseId(responseId)

            def responseList = List.of(response)

            def optionalCustomer = Optional.of(customerEntity)

            customerRepo.findById(customerId) >> optionalCustomer
            responseConverter.dtoToEntity(response) >> responseEntity
            responseRepo.save(responseEntity) >> responseEntity
        when:
            responseService.saveResponseList(responseList, customerId)
            def resultEntity = responseEntity.getCustomerEntity()
        then:
            resultEntity.equals(customerEntity)
    }

    def "Shouldn return exception if customer is not exist"() {
        given:
            def customerId = UUID.randomUUID()

            def responseId = UUID.randomUUID()

            def response = new Response()
            response.setResponseId(responseId)

            def responseList = List.of(response)

            customerRepo.findById(customerId) >> Optional.empty()
        when:
            responseService.saveResponseList(responseList, customerId)
        then:
            thrown(RuntimeException)
    }

    def "Should update status for already existing response"() {
        given:
            def responseId = UUID.randomUUID()
            def response = new Response()
            response.setResponseId(responseId)
            response.setStatus(StatusResponse.PROCESSED)

            def responseEntity = new ResponseEntity()
            responseEntity.setResponseId(responseId)
            responseEntity.setStatus(StatusResponse.DRAFT)

            def newResponseEntity = new ResponseEntity()
            newResponseEntity.setResponseId(responseId)
            newResponseEntity.setStatus(StatusResponse.PROCESSED)

            def offer = new OfferEntity()
            offer.setOfferId(UUID.randomUUID())
            newResponseEntity.setOfferEntity(offer)

            responseConverter.dtoToEntity(response) >> newResponseEntity
            responseRepo.findById(responseId) >> Optional.of(responseEntity)
            offerService.save(offer) >> offer
            responseRepo.save(responseEntity) >> responseEntity
        when:
            responseService.update(response)
        then:
            responseEntity.getStatus() == StatusResponse.PROCESSED
    }

    def "Should return exception if response is not exist" () {
        given:
            def responseId = UUID.randomUUID()

            def response = new Response()
            response.setResponseId(responseId)
            response.setStatus(StatusResponse.PROCESSED)

            def newResponseEntity = new ResponseEntity()
            newResponseEntity.setResponseId(responseId)
            newResponseEntity.setStatus(StatusResponse.PROCESSED)

            responseConverter.dtoToEntity(response) >> newResponseEntity
            responseRepo.findById(responseId) >> Optional.empty()
        when:
            responseService.update(response)
        then:
            thrown(RuntimeException)
    }

    def "Should update status without offers"() {
        given:
            def responseId = UUID.randomUUID()
            def response = new Response()
            response.setResponseId(responseId)
            response.setStatus(StatusResponse.PROCESSED)

            def responseEntity = new ResponseEntity()
            responseEntity.setResponseId(responseId)
            responseEntity.setStatus(StatusResponse.DRAFT)

            def newResponseEntity = new ResponseEntity()
            newResponseEntity.setResponseId(responseId)
            newResponseEntity.setStatus(StatusResponse.PROCESSED)

            responseConverter.dtoToEntity(response) >> newResponseEntity
            responseRepo.findById(responseId) >> Optional.of(responseEntity)
            responseRepo.save(responseEntity) >> responseEntity
        when:
            responseService.update(response)
        then:
            responseEntity.getStatus() == StatusResponse.PROCESSED
    }
}
