package us.mapr.api.json;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.ext.Provider;

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
//                            return JSONObject.fromObject(dateTimeFmt.print((DateTime) bean));
//                        }
//                        return null;
//                    }
//                }
//        );
        return JSONObject.fromObject(object, config);
    }
}
