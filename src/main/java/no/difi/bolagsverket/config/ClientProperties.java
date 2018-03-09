package no.difi.bolagsverket.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.security.KeyStore;

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
    private KeyStoreProperties keystore;

    @Valid
    private KeyStoreProperties truststore;
    @Valid
    private ValidationProperties validation;

    @Data
    @ToString(exclude = {"password", "keyPassword"})
    public static class KeyStoreProperties {
        private String type = KeyStore.getDefaultType();
        @NotNull
        private Resource path;
        @NotNull
        private String password;
        private String keyAlias;
        private String keyPassword;
    }

    @Data
    public static class ValidationProperties {
        @NotNull
        private boolean enabled;
        @NotNull
        private boolean callingBolagsverket;
    }
}
