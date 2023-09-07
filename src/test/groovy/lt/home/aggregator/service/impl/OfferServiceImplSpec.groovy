package lt.home.aggregator.service.impl

import lt.home.aggregator.entities.OfferEntity
import lt.home.aggregator.repositories.OfferRepo
import spock.lang.Specification

class OfferServiceImplSpec extends Specification {
    def offerRepo = Mock(OfferRepo)
    def offerService = new OfferServiceImpl(offerRepo)

    def "Should return Offer object"() {
        given:
            def offerEntity = new OfferEntity()
            offerEntity.setOfferId(UUID.randomUUID())
            offerRepo.save(offerEntity) >> offerEntity
        when:
            def result = offerService.save(offerEntity)
         then:
         offerEntity.getOfferId().equals(result.getOfferId())
    }
}
