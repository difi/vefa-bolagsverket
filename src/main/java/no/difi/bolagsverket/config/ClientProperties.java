package no.difi.bolagsverket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.net.URL;

@Data
@Validated
@ConfigurationProperties("bolagsverket")
public class ClientProperties {
    @NotNull
    private URL serviceEndpoint;
    @NotNull
    private String userId;
    @NotNull
    private String certId;
}
