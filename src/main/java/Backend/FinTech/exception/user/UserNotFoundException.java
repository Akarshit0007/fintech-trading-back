package Backend.FinTech.exception.user;

import Backend.FinTech.enums.ErrorCode;
import Backend.FinTech.exception.base.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String email) {
        super(
                "User not found with email:" + email,
                ErrorCode.USER_NOT_FOUND
        );

    }

}
