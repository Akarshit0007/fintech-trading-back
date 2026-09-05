package Backend.FinTech.controller.transactions;

import Backend.FinTech.dto.request.transaction.TransactionRequest;
import Backend.FinTech.dto.response.transaction.TransactionResponse;
import Backend.FinTech.repository.TransactionRepository;
import Backend.FinTech.service.Interface.transaction.TransactionService;
import Backend.FinTech.service.implementation.transaction.TransactionServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public  TransactionController(TransactionRepository transactionRepository, TransactionServiceImp transactionServiceImp, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @PostMapping("/pay")
    public ResponseEntity<TransactionResponse> createTransaction( @Valid @RequestBody TransactionRequest transactionRequest) {
        System.out.println("Incoming Sender ID from JSON: " + transactionRequest.getSenderId());
        System.out.println("Incoming Receiver ID from JSON: " + transactionRequest.getReceiverId());
         TransactionResponse response = transactionService.processTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
