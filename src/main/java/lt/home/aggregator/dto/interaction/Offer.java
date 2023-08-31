package lt.home.aggregator.dto.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    private BigDecimal monthlyPaymentAmount;
    private BigDecimal totalRepaymentAmount;
    private Long numberOfPayments;
    private BigDecimal annualPercentageRate;
    private LocalDate firstRepaymentDate;
}
