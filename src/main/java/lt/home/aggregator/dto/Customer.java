package lt.home.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.home.aggregator.dto.interaction.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private UUID customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private MarriedStatus marriedStatus;
    private Integer dependents;
    private Boolean agreeToBeScored = false;
    private BigDecimal amount;
    private List<Response> responseList = new ArrayList<>();
}
