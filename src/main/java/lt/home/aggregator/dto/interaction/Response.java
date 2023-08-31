package lt.home.aggregator.dto.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.home.aggregator.dto.TypeBank;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private UUID responseId;
    private TypeBank bank;
    private StatusResponse status;
    private Offer offer;
    private Request request;
}
