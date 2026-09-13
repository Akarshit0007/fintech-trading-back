package Backend.FinTech.exception.auth;

import Backend.FinTech.enums.ErrorCode;
import Backend.FinTech.exception.base.BaseException;

public class EmptyEmailException extends BaseException {
    public EmptyEmailException(String message) {

        super(message, ErrorCode.INVALID_CREDENTIALS);

    }
}
