package us.mapr.api.json;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A container for a list of Throwable errors.
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class Errors {

    public static Builder error(String message, Throwable t) {
        return new Builder().addError(message, t);
    }


    public static class Builder {
        List<MessageWithStacktrace> errors = new ArrayList<MessageWithStacktrace>();
        private Builder() {

        }

        public Builder addError(String message, Throwable t) {
            errors.add(new MessageWithStacktrace(message, t));
            return this;
        }

        public Errors build() {
            return new Errors(Collections.unmodifiableList(errors));
        }

    }

    List<MessageWithStacktrace> errors;

    private Errors(List<MessageWithStacktrace> errors) {
        this.errors = errors;
    }

    public List<MessageWithStacktrace> getErrors() {
        return errors;
    }

    public static class MessageWithStacktrace {
        private String message;
        private String stacktrace;

        private MessageWithStacktrace(String message, Throwable stacktrace) {
            this.message = message;
            final StringWriter stringWriter = new StringWriter();
            stacktrace.printStackTrace(new PrintWriter(stringWriter));
            this.stacktrace = stringWriter.toString();
        }

        public String getMessage() {
            return message;
        }

        public String getStacktrace() {
            return stacktrace;
        }
    }

}
