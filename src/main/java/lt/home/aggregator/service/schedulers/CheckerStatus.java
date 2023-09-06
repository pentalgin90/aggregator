package lt.home.aggregator.service.schedulers;

import lombok.RequiredArgsConstructor;
import lt.home.aggregator.dto.interaction.Response;
import lt.home.aggregator.dto.interaction.StatusResponse;
import lt.home.aggregator.entities.ResponseEntity;
import lt.home.aggregator.service.ResponseService;
import lt.home.aggregator.service.impl.FastBankService;
import lt.home.aggregator.service.impl.SolidBankService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckerStatus {
    private final FastBankService fastBankService;
    private final SolidBankService solidBankService;
    private final ResponseService responseService;

    @Scheduled(fixedDelay = 5_000)
    public void start() {
        List<ResponseEntity> responses = responseService.getAllResponse();
        if (!responses.isEmpty()) {
            responses.stream()
                    .filter(response -> response.getStatus() == StatusResponse.DRAFT)
                    .forEach(response -> {
                        Response newResponse = switch (response.getBank()) {
                            case SOLID -> solidBankService.retrieval(response.getResponseId());
                            case FAST -> fastBankService.retrieval(response.getResponseId());
                        };
                        responseService.update(newResponse);
                    });
        }
    }
}
