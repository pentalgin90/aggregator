package lt.home.aggregator.dto.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.home.aggregator.dto.MarriedStatus;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestToSolidBank implements Request {
    private String phone;
    private String email;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private MarriedStatus maritalStatus;
    private Boolean agreeToBeScored = false;
    private BigDecimal amount;
}
