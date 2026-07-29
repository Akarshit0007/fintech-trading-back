package Backend.FinTech.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder

public class ErrorResponse {
    private boolean success;
    private String message;
    private int status;
    private String errorCode;
    private LocalDateTime timestamp;
    private String path;


}
