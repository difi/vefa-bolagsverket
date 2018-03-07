package no.difi.bolagsverket.config;

import no.difi.bolagsverket.client.BolagsverketClient;
import no.difi.bolagsverket.request.Base64RequestProviderImpl;
import no.difi.bolagsverket.service.BolagsverketValidatorServiceImpl;
import no.difi.bolagsverket.service.IdentifierValidatorServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

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
        template.setDefaultUri(properties.getServiceEndpoint().toString());
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        return new BolagsverketClient(properties, template, new Base64RequestProviderImpl());
    }

    @Bean
    public BolagsverketValidatorServiceImpl bolagsverketValidator(BolagsverketClient client) {
        return new BolagsverketValidatorServiceImpl(client, new IdentifierValidatorServiceImpl());
    }

}
