package us.mapr.api.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Guice replacement for web.xml
 *
 */
public class WebConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new SpringContextModule(),
                new ServletModule() {
                    @Override
                    protected void configureServlets() {

                        final Map<String, String> params = new HashMap<String, String>();
                        params.put(PackagesResourceConfig.PROPERTY_PACKAGES, "us.mapr.api");
                        serve("/*").with(GuiceContainer.class, params);

                        // Spring Security
//                        bind(DelegatingFilterProxy.class).in(Singleton.class);
//                        filter("/*").through(DelegatingFilterProxy.class);
//                        bind(org.springframework.web.context.ContextLoaderListener.class);
                    }
                }
        );
    }
}
