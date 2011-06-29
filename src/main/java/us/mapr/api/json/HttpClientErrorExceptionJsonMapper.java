package us.mapr.api.json;

import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.PrintWriter;
import java.io.StringWriter;

// I suspect this exception mapper interferes with Spring Security OAuth because it absorbs the exception
// prematurely in the filter chain.
//@Provider
public class HttpClientErrorExceptionJsonMapper implements ExceptionMapper<HttpClientErrorException> {
    @Override
    public Response toResponse(final HttpClientErrorException e) {
        final StringWriter buffer = new StringWriter();
        PrintWriter writer = new PrintWriter(buffer);
        e.printStackTrace(writer);
        final String stacktrace = buffer.toString();

        return Response
                .status(Response.Status.BAD_REQUEST)
                .type("application/json")
                .entity(Errors
                            .error("There was a problem connecting to an external resource, " + e.getMessage(), e)
                            .build())
                .build();
    }
}
