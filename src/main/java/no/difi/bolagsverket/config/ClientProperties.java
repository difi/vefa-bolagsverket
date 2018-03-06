package no.difi.bolagsverket.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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

    @Valid
    private KeyStoreProperties keyStore;

    @Data
    @ToString(exclude = "password")
    public static class KeyStoreProperties {
        private Resource path;
        private String password;
    }
}
