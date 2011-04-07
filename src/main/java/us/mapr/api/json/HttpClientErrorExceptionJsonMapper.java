package us.mapr.api.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

@Provider
public class HttpClientErrorExceptionJsonMapper implements ExceptionMapper<HttpClientErrorException> {
    @Override
    public Response toResponse(HttpClientErrorException e) {
        final StringWriter buffer = new StringWriter();
        PrintWriter writer = new PrintWriter(buffer);
        e.printStackTrace(writer);
        String stacktrace = buffer.toString();

        return Response
                .status(Response.Status.BAD_REQUEST)
                .type("application/json")
                .entity(new JSONObject().element("errors", new JSONArray().element(
                        new JSONObject()
                                .element("message", "There was a problem connecting to an external resource, " + e.getMessage())
                                .element("stacktrace", stacktrace)
                )).toString())
                .build();
    }
}
