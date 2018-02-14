package no.difi.bolagsverket.config;

import no.difi.bolagsverket.client.BolagsverketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class ClientConfig {

    @Autowired
    private ClientProperties properties;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("no.difi.bolagsverket.xml");
        return marshaller;
    }

    @Bean
    public BolagsverketClient bolagsverketClient(ClientProperties properties, Jaxb2Marshaller marshaller) {
        BolagsverketClient client = new BolagsverketClient();
        client.setDefaultUri(properties.getServiceEndpoint().toString());
        client.setMarshaller(marshaller);

        return client;
    }

}
