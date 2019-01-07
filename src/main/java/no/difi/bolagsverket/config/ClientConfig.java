package no.difi.bolagsverket.config;

import lombok.extern.slf4j.Slf4j;
import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.request.Base64RequestProviderImpl;
import no.difi.bolagsverket.security.KeyStoreProvider;
import no.difi.bolagsverket.service.BolagsverketValidatorServiceImpl;
import no.difi.bolagsverket.service.IdentifierValidatorServiceImpl;
import no.difi.bolagsverket.service.ValidatorService;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

@Slf4j
@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class ClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("no.difi.bolagsverket.xml");
        return marshaller;
    }

    @Bean
    public BolagsverketClient bolagsverketClient(ClientProperties properties, Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();

        CloseableHttpClient httpClient = getCloseableHttpClient(properties);
        HttpComponentsMessageSender sender = new HttpComponentsMessageSender(httpClient);
        template.setMessageSender(sender);

        template.setDefaultUri(properties.getServiceEndpoint().toString());
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        return new BolagsverketClient(properties, template, new Base64RequestProviderImpl());
    }

    private CloseableHttpClient getCloseableHttpClient(ClientProperties properties) {
        return HttpClients.custom()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext(properties)))
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .build();
    }

    private SSLContext sslContext(ClientProperties properties) {
        try {
            ClientProperties.KeyStoreProperties keyProperties = properties.getKeystore();
            KeyStore keyStore = KeyStoreProvider.from(keyProperties).getKeyStore();
            ClientProperties.KeyStoreProperties trustProperties = properties.getTruststore();
            return SSLContexts.custom()
                    .loadKeyMaterial(keyStore, keyProperties.getKeyPassword().toCharArray())
                    .loadTrustMaterial(properties.getTruststore().getPath().getFile(), trustProperties.getPassword().toCharArray())
                    .build();
        } catch (NullPointerException e) {
            throw new IllegalStateException("Keystore missing.", e);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not load keystore.", e);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load truststore.", e);
        }
    }

    @Bean
    public ValidatorService bolagsverketValidator(ClientProperties properties, BolagsverketClient client) {
        ClientProperties.ValidationProperties validationProperties = properties.getValidation();
        if (!validationProperties.isEnabled()) {
            return s -> true;
        }
        ValidatorService service;
        if (validationProperties.isCallingBolagsverket()) {
            service = new BolagsverketValidatorServiceImpl(client, new IdentifierValidatorServiceImpl());
        } else {
            service = new IdentifierValidatorServiceImpl();
        }
        return service;
    }

}
