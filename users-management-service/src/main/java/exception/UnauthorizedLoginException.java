package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedLoginException extends RuntimeException {
	private static final long serialVersionUID = -2095793919029767140L;

	public UnauthorizedLoginException() {
	}

	public UnauthorizedLoginException(String message) {
		super(message);
	}

	public UnauthorizedLoginException(Throwable cause) {
		super(cause);
	}

	public UnauthorizedLoginException(String message, Throwable cause) {
		super(message, cause);
	}
}
