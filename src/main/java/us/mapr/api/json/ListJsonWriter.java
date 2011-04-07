package us.mapr.api.json;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Responsible for marshaling a top-level List to a JSON array
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@Provider
public class ListJsonWriter extends AbstractJsonWriter<List<?>> {

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return List.class.isAssignableFrom(aClass) && super.isWriteable(aClass, type, annotations, mediaType);
    }

    @Override
    protected JSON toJSON(List<?> object) {
        return JSONArray.fromObject(object);
    }
}