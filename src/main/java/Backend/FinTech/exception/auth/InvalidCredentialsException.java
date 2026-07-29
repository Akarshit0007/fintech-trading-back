package Backend.FinTech.exception.auth;

import Backend.FinTech.enums.ErrorCode;
import Backend.FinTech.exception.base.BaseException;

public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException(String message) {

        super(message, ErrorCode.INVALID_CREDENTIALS);

    }
}
