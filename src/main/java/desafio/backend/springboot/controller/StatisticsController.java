package desafio.backend.springboot.controller;

import desafio.backend.springboot.dto.StatisticsResponse;
import desafio.backend.springboot.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class StatisticsController {

    private final TransactionService transactionService;

    public StatisticsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<StatisticsResponse> getStatistics() {
        try {
            DoubleSummaryStatistics statistics = transactionService.getStatistics();
            log.info("Total of transactions: {}", statistics.getCount());
            return ResponseEntity.ok(new StatisticsResponse(statistics));
        }
        catch (Exception e) {
            log.error("Error trying to get statistics: {}", e.getMessage());
            throw e;
        }
    }
}
