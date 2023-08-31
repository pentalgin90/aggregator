package lt.home.aggregator.service;

import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.dto.interaction.Response;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public interface FinancingService {
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Response applyForLoan();

    Response retrieval(UUID responseId);

    void makeRequest(Customer customer);
}
