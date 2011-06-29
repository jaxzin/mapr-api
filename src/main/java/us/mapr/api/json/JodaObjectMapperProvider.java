package us.mapr.api.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.mapr.api.model.CheckinSource;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.text.DateFormat;

/**
 * Configures Jackson JSON marshalling for Jersey
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@Provider
public class JodaObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JodaObjectMapperProvider.class);

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper
            .getSerializationConfig()
            .setDateFormat(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));

        // Hack since Groovy enums don't seem to have everything Jackson needs
        SimpleModule checkinSourceModule = new SimpleModule("CheckinSourceModule", new Version(1,0,0,null));
        checkinSourceModule.addSerializer(CheckinSource.class, new JsonSerializer<CheckinSource>() {
            @Override
            public void serialize(CheckinSource value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                LOGGER.info("Serializing CheckinSource {}", value);
                jgen.writeString(value.name());
            }
        });

        mapper.registerModule(checkinSourceModule);


        return mapper;
    }
}
