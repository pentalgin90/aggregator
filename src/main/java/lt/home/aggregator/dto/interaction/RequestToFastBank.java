package lt.home.aggregator.dto.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestToFastBank implements Request {
    private String phoneNumber;
    private String email;
    private BigDecimal monthlyIncomeAmount;
    private BigDecimal monthlyCreditLiabilities;
    private Integer dependents;
    private Boolean agreeToDataSharing = false;
    private BigDecimal amount;
}
