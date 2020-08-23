package nc.oliweb.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CategoryAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String entityName;
    private final String errorKey;
    private final String errorAttribute;

    public CategoryAlertException(String defaultMessage, String entityName, String errorKey, String errorAttribute) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey, errorAttribute);
    }

    public CategoryAlertException(URI type, String defaultMessage, String entityName, String errorKey, String errorAttribute) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
        this.errorAttribute = errorAttribute;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public String getErrorAttribute() {
        return errorAttribute;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
