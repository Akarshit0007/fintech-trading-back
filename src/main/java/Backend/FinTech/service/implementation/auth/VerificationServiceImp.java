package Backend.FinTech.service.implementation.auth;

import Backend.FinTech.dto.request.auth.CodeRequest;
import Backend.FinTech.dto.response.auth.CodeRespose;
import Backend.FinTech.entity.Users;
import Backend.FinTech.exception.user.UserNotFoundException;
import Backend.FinTech.repository.UserRepository;
import Backend.FinTech.service.Interface.auth.VerificationService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class VerificationServiceImp implements VerificationService {
    private final StringRedisTemplate redisTemplate;
//    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public VerificationServiceImp(StringRedisTemplate redisTemplate, UserRepository userRepository){
        this.redisTemplate = redisTemplate;
//        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @Override
    public CodeRespose generateVerificationCode(CodeRequest codeRequest) {
    System.out.println(codeRequest.getEmail());
        // Checks Whether Email Exist In database Or Not
       Users user = userRepository.findByEmail(codeRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(codeRequest.getEmail()));

        String code = generateRandomCode();
        String redisKey = "verification:" + user.getEmail();

        // Store in Redis with an expiration time of 300 seconds (5 minutes)
        try {
            redisTemplate.opsForValue().set(redisKey, code, 300, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("REDIS ERROR: " + e.getMessage());
            throw e;
        }

        // Send the actual email
        System.out.println("Verification Email: " + user.getEmail());
        System.out.println("Verification Code: " + code);
        return CodeRespose.builder()
                .verificationCode(Integer.parseInt(code))
                .build();

    }

    @Override
    public boolean verifyCode(String email, String code) {
        String redisKey = "verification:" + email;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode == null) {
            return false; // Expired or doesn't exist
        }

        if (storedCode.equals(code)) {
            // Match successful! Delete key immediately to prevent reuse
            redisTemplate.delete(redisKey);
            return true;
        }

        return false;
    }

    private String generateRandomCode() {
        int code = 100000 + new Random().nextInt(900000);
        return String.valueOf(code);
    }

//    private void sendEmail(String toEmail, String code) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Your Verification Code");
//        message.setText("Your verification code is: " + code + "\nThis code expires in 5 minutes.");
//
//        mailSender.send(message);
//    }
}
