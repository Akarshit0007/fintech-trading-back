package Backend.FinTech.exception.auth;

import Backend.FinTech.enums.ErrorCode;
import Backend.FinTech.exception.base.BaseException;

public class AccountLockedException extends BaseException {

    public AccountLockedException(String email) {
        super(
                "Your Account get Locked Retry after some time" + email,
                ErrorCode.ACCOUNT_IS_LOCKED
        );
    }
}
