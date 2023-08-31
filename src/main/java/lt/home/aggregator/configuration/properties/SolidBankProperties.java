package lt.home.aggregator.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;
@Data
@ConfigurationProperties(prefix = "financing-institutions.solid-bank")
public class SolidBankProperties {
    private String url;
}
