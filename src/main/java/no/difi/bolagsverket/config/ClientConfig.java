package no.difi.bolagsverket.config;

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
    public WebServiceTemplate bolagsverketClient(ClientProperties properties, Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setDefaultUri(properties.getServiceEndpoint().toString());
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);

        return template;
    }

}
