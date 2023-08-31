package lt.home.aggregator.utilits

import lt.home.aggregator.dto.MarriedStatus
import lt.home.aggregator.dto.TypeBank
import lt.home.aggregator.dto.interaction.RequestToFastBank
import lt.home.aggregator.dto.interaction.RequestToSolidBank
import spock.lang.Specification

class RequestValidationSpec extends Specification {
    def "Should return true if the customer is valid"() {
        given:
            def request = new RequestToFastBank(
                "+37111111111",
                "test@test.com",
                150.0,
                10.0,
                0,
                true,
                150.0
        );
        when:
            def result = RequestValidation.check(request, TypeBank.FAST)
        then:
            result == true
    }

    def "Should return false if the customer equals null"() {
        given:
            def request = null;
        when:
            def result = RequestValidation.check(request, TypeBank.FAST)
        then:
            result == false;
    }

    def "Should return false if the customer has wrong email"() {
        given:
            def request = new RequestToFastBank(
                "+37111111111",
                "test@testcom",
                150.0,
                10.0,
                0,
                true,
                150.0
        );
        when:
            def result = RequestValidation.check(request, TypeBank.FAST)
        then:
            result == false
    }

    def "Should return false if the customer has wrong phone number"() {
        given:
            def request = new RequestToFastBank(
                "+37011111111",
                "test@test.com",
                150.0,
                10.0,
                0,
                true,
                150.0
        );
        when:
            def result = RequestValidation.check(request, TypeBank.FAST)
        then:
            result == false
    }

    def "Should return true if the customer has right phone number for Solid bank"() {
        given:
            def request = new RequestToSolidBank(
                "+37011111111",
                "test@test.com",
                150.0,
                10.0,
                MarriedStatus.MARRIED,
                true,
                150.0
        );
        when:
            def result = RequestValidation.check(request, TypeBank.SOLID)
        then:
            result == true
    }
}