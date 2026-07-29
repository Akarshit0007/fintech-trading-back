package Backend.FinTech.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(404,"USER_404"),
    INVALID_CREDENTIALS(401, "AUTH_401"),
    ACCOUNT_IS_LOCKED(402, "ACCOUNT_GET_LOCKED"),
    ;
    private final int status;

    private final String errorCode;

    ErrorCode(int status, String errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }

}
