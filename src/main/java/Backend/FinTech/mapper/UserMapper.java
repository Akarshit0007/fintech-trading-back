package Backend.FinTech.mapper;


import Backend.FinTech.dto.request.auth.SignupRequest;
import Backend.FinTech.dto.response.auth.UserResponse;
import Backend.FinTech.entity.Users;
import Backend.FinTech.enums.UserRole;
import org.springframework.stereotype.Component;
/*
It Keeps conversion logic seperate
avoid duplication mapping code
follows SRP
 */


@Component
public class UserMapper {


    public static Users toEntity(SignupRequest signupRequest , String encodedPassword) {
//        Users users = new Users();
//
//        users.setName(signupRequest.getName());
//        users.setEmail(signupRequest.getEmail());
//        users.setPassword(signupRequest.getPassword());
//        users.setRole(UserRole.ROLE_USER);
//        return users;
        return Users.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .role(UserRole.ROLE_USER)
                .failedAttempts(0)
                .AccountNonLocked(true)
                .build();
    }


    public static UserResponse toResponse(Users users) {

        return UserResponse.builder()
                .id(users.getId())
                .name(users.getName())
                .email(users.getEmail())
                .role(users.getRole())
                .build();
    }
}
