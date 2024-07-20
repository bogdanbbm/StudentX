package students.x.studentsx.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomException extends RuntimeException {
    private final String errorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getBody() {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("message", super.getMessage());
        return errorResponse;
    }
}