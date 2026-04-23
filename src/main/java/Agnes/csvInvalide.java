package Agnes;

public class csvInvalide extends RuntimeException {
    public csvInvalide(String message) {
        super(message);
    }
    public csvInvalide(String message, Throwable cause) {
        super(message, cause);
    }

}
