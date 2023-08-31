package lt.home.aggregator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.home.aggregator.configuration.properties.SolidBankProperties;
import lt.home.aggregator.converter.RequestConverter;
import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.dto.MarriedStatus;
import lt.home.aggregator.dto.TypeBank;
import lt.home.aggregator.dto.interaction.Offer;
import lt.home.aggregator.dto.interaction.RequestToSolidBank;
import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.dto.interaction.StatusResponse;
import lt.home.aggregator.service.FinancingService;
import lt.home.aggregator.utilits.RequestValidation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@EnableConfigurationProperties(SolidBankProperties.class)
@RequiredArgsConstructor
public class SolidBankService implements FinancingService {

    private final SolidBankProperties properties;
    private final RestTemplate restTemplate;
    private final RequestConverter requestConverter;
    private RequestToSolidBank request;
    private final ObjectMapper objectMapper;

    @Override
    public Response applyForLoan() {
        try {
            if (Objects.nonNull(request)) {
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(properties.getUrl(), request, String.class);
                return mappedJson(responseEntity);
            }
            throw new Exception("Request not ready for sending to solid bank");
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Response retrieval(UUID responseId) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(properties.getUrl() + "/" + responseId, String.class);
            return mappedJson(responseEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void makeRequest(Customer customer) {
        RequestToSolidBank request = requestConverter.makeRequestToSolidBank(customer);
        if (RequestValidation.check(request, TypeBank.SOLID)) {
            this.request = request;
        }
    }

    private Response mappedJson(ResponseEntity<String> responseEntity) {
        try {
            Offer offer = null;
            JsonNode root = objectMapper.readTree(responseEntity.getBody());
            String id = root.path("id").asText();
            String status = root.path("status").asText();
            JsonNode requestRoot = root.path("request");
            String phone = requestRoot.path("phone").asText();
            String email = requestRoot.path("email").asText();
            double monthlyIncomeAmount = requestRoot.path("monthlyIncome").asDouble();
            double monthlyCreditLiabilities = requestRoot.path("monthlyExpenses").asDouble();
            String maritalStatus = requestRoot.path("maritalStatus").asText();
            boolean agreeToDataSharing = requestRoot.path("agreeToBeScored").asBoolean();
            double amount = requestRoot.path("amount").asDouble();
            JsonNode offerRoot = root.path("offer");
            if (!offerRoot.isEmpty()) {
                double monthlyPaymentAmount = offerRoot.path("monthlyPaymentAmount").asDouble();
                double totalRepaymentAmount = offerRoot.path("totalRepaymentAmount").asDouble();
                long numberOfPayments = offerRoot.path("numberOfPayments").asLong();
                double annualPercentageRate = offerRoot.path("annualPercentageRate").asDouble();
                String firstRepaymentDate = offerRoot.path("firstRepaymentDate").asText();
                offer = new Offer(
                        BigDecimal.valueOf(monthlyPaymentAmount),
                        BigDecimal.valueOf(totalRepaymentAmount),
                        numberOfPayments,
                        BigDecimal.valueOf(annualPercentageRate),
                        LocalDate.parse(firstRepaymentDate, DTF)
                );
            }
            RequestToSolidBank requestToSolidBank = new RequestToSolidBank(
                    phone,
                    email,
                    BigDecimal.valueOf(monthlyIncomeAmount),
                    BigDecimal.valueOf(monthlyCreditLiabilities),
                    MarriedStatus.valueOf(maritalStatus),
                    agreeToDataSharing,
                    BigDecimal.valueOf(amount)
            );
            return new Response(
                    UUID.fromString(id),
                    TypeBank.SOLID,
                    StatusResponse.valueOf(status),
                    offer,
                    requestToSolidBank
            );
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
