package lt.home.aggregator.service.impl;

import lombok.RequiredArgsConstructor;
import lt.home.aggregator.converter.CustomerConverter;
import lt.home.aggregator.dto.Customer;
import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.service.ApplicationService;
import lt.home.aggregator.service.CustomerService;
import lt.home.aggregator.service.FinancingService;
import lt.home.aggregator.service.ResponseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApplicationCustomerImpl implements ApplicationService {

    private final CustomerService customerService;
    private final ResponseService responseService;
    private final FastBankService fastBankService;
    private final SolidBankService solidBankService;

    @Override
    @Transactional
    public Customer createAnApplication(Customer customer) {
        List<FinancingService> financingServiceList = List.of(fastBankService, solidBankService);
        List<Response> responseList = financingServiceList.stream()
                .map(financingService -> {
                    financingService.makeRequest(customer);
                    return financingService.applyForLoan();
                })
                .toList();
        customer.getResponseList().addAll(responseList);
        Customer saved = customerService.save(customer);
        if (!responseList.isEmpty()) {
            responseService.saveResponseList(responseList, saved.getCustomerId());
        }
        return saved;
    }
}
