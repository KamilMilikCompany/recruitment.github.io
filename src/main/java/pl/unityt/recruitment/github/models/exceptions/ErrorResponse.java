package pl.unityt.recruitment.github.models.exceptions;

public class ErrorResponse {

    private final int status;
    private final String message;
    private final String path;

    public ErrorResponse(RestTemplateException ex, String path) {
        this.status = ex.getStatusCode().value();
        this.message = ex.getError();
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}