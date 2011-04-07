package us.mapr.api.json;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.ext.Provider;
import java.net.URI;

/**
 * Responsible for marshaling a bean to a JSON object
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@Provider
public class BeanJsonWriter extends AbstractJsonWriter<Object> {

    private DateTimeFormatter dateTimeFmt;

    public BeanJsonWriter() {
        this.dateTimeFmt = ISODateTimeFormat.dateTime();
    }

    @Override
    protected JSON toJSON(Object object) {
        JsonConfig config = new JsonConfig();
//        config.registerJsonBeanProcessor(DateTime.class,
//                new JsonBeanProcessor() {
//                    @Override
//                    public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
//                        if(bean instanceof DateTime) {
//                            return JSONObject.fromObject("'"+dateTimeFmt.print((DateTime) bean)+"'");
//                        }
//                        return null;
//                    }
//                }
//        );
        config.registerJsonValueProcessor(DateTime.class,
                new JsonValueProcessor() {
                    @Override
                    public Object processArrayValue(Object bean, JsonConfig jsonConfig) {
                        return process((DateTime) bean);
                    }

                    @Override
                    public Object processObjectValue(String name, Object bean, JsonConfig jsonConfig) {
                        return process((DateTime) bean);
                    }

                    private String process(DateTime bean) {
                        return dateTimeFmt.print(bean);
                    }
                }
        );

        config.registerJsonValueProcessor(URI.class,
                new JsonValueProcessor() {
                    @Override
                    public Object processArrayValue(Object bean, JsonConfig jsonConfig) {
                        return process((URI) bean);
                    }

                    @Override
                    public Object processObjectValue(String name, Object bean, JsonConfig jsonConfig) {
                        return process((URI) bean);
                    }

                    private String process(URI bean) {
                        return bean.toString();
                    }
                }
        );

        return JSONObject.fromObject(object, config);
//        return JSONObject.fromObject(object);
    }
}
