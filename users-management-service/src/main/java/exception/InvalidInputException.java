package exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidInputException extends RuntimeException {
	private static final long serialVersionUID = -2095793919029767140L;

	public InvalidInputException() {
	}

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
