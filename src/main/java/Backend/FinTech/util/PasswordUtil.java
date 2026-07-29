package Backend.FinTech.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public  String encode(String password){

        return encoder.encode(password);

    }

//    public static String decode(String password){
//        return "xxssx";
//    }

    public  boolean matches(String rawpassword, String encodedpassword) {
        return encoder.matches(rawpassword, encodedpassword);
    }
}
