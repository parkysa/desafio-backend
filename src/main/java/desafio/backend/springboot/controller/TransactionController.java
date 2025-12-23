package desafio.backend.springboot.controller;

import desafio.backend.springboot.model.Transaction;
import desafio.backend.springboot.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService transactionService;

    // injeta a service
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody Transaction request) {
        if (request.getDataHora().isAfter(OffsetDateTime.now())|| request.getValor() <= 0) {
            return ResponseEntity.unprocessableEntity().build(); // resposta 422
        }
        transactionService.addTransaction(new Transaction(request.getValor(), request.getDataHora()));
        return ResponseEntity.status(HttpStatus.CREATED).build(); // resposta 201
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransaction(@Valid @RequestBody Transaction request) {
        transactionService.clearTransactions();
        return ResponseEntity.ok().build(); // resposta 200
    }
}
