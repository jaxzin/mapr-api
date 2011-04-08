package us.mapr.api.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.spring.SpringIntegration;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.security.oauth2.consumer.OAuth2ErrorHandler;
import org.springframework.security.oauth2.consumer.OAuth2RestTemplate;
import org.springframework.security.oauth2.consumer.webserver.WebServerProfileResourceDetails;
import us.mapr.users.GaeDatastoreUserRegistry;
import us.mapr.users.UserRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Guice Module for Spring Integration
 *
 */
public class SpringContextModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextModule.class);

    @Override
    protected void configure() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext*.xml");

        SpringIntegration.bindAll(binder(), applicationContext);

        LOGGER.info("Bound {} Spring beans to Guice.", applicationContext.getBeanDefinitionCount());
    }

//    @Provides
//    @Google
//    public OAuth2RestTemplate provideGoogleRestTemplate(@Named("google") OAuth2ProtectedResourceDetails resourceDetails) {
//        final OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
//        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//        messageConverters.add(new AtomFeedHttpMessageConverter());
//        restTemplate.setMessageConverters(messageConverters);
//        return restTemplate;
//    }

    /**
     *
     * @param resourceDetails The Spring bound WebServerProfileResourceDetails.
     *    NOTE: Spring OAuth binds to WebServerProfileResourceDetails and not OAuth2ProtectedResourceDetails
     * @return A RestTemplate that can connect to Facebook
     *
     * @see org.springframework.security.oauth2.config.OAuth2ResourceBeanDefinitionParser#getBeanClass
     */
    @Provides
    @Facebook
    public OAuth2RestTemplate provideFacebookRestTemplate(@Named("facebook") WebServerProfileResourceDetails resourceDetails) {
        final OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);

        final MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Arrays.asList(
                // facebook sends its json as text/javascript for some reason
                new MediaType("text","javascript"),
                new MediaType("application", "json")
            ));
        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(messageConverter);
        restTemplate.setMessageConverters(messageConverters);

        restTemplate.setErrorHandler(
                new OAuth2ErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        String body = IOUtils.toString(response.getBody());
                        LOGGER.error("Received the following response: {} {}\n{}", new Object[]{response.getStatusCode(), response.getStatusText(), body});
                        super.handleError(response);
                    }
                }
        );
        return restTemplate;
    }

    @Provides
    public UserRegistry provideUserRegistry(@Named("userRegistry") GaeDatastoreUserRegistry userRegistry) {
        return userRegistry;
    }
}
