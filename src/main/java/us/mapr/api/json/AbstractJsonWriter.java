/*
 *  Revision Information:
 *  $Id$
 *  $Author$
 *  $DateTime$
 *
 * Copyright ©2011 ESPN.com and Disney Interactive Media Group.  All rights reserved.
 */
package us.mapr.api.json;

import net.sf.json.JSON;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public abstract class AbstractJsonWriter<T> implements MessageBodyWriter<T> {
    public boolean isWriteable(Class<?> aClass,
                               Type type,
                               Annotation[] annotations,
                               MediaType mediaType) {
        return MediaType.APPLICATION_JSON_TYPE.equals(mediaType)
                || (mediaType != null
                && mediaType.getSubtype() != null
                && mediaType.getSubtype().endsWith("+json"));
    }

    protected abstract JSON toJSON(T object);

    public void writeTo(T t,
                        Class<?> aClass,
                        Type type,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream outputStream) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.append(toJSON(t).toString());
        writer.flush();
    }
}