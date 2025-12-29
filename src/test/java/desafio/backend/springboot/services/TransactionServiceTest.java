package desafio.backend.springboot.services;

import desafio.backend.springboot.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given TransactionService")
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService.clearTransactions();
    }

    @Nested
    @DisplayName("When addTransaction is called")
    public class WhenAddTransactionIsCalled {

        @BeforeEach
        void setUp() {
            Transaction transaction = new Transaction(100.0, OffsetDateTime.now());
            transactionService.addTransaction(transaction);
        }

        @DisplayName("Then adds a transaction")
        @Test
        void addTransaction() {
            assertEquals(1, transactionService.getStatistics().getCount());
        }
    }

    @Nested
    @DisplayName("When clearTransactions is called")
    public class WhenClearTransactionsIsCalled {

        @BeforeEach
        void setUp() {
            transactionService.addTransaction(new Transaction(50.0, OffsetDateTime.now()));
            transactionService.clearTransactions();
        }

        @Test
        @DisplayName("Then clears all transactions")
        public void clearTransactions() {
            assertEquals(0, transactionService.getStatistics().getCount());
        }
    }

    @Nested
    @DisplayName("When getStatistics is called")
    public class WhenDoubleSummaryStatistics {

        private DoubleSummaryStatistics result;

        @BeforeEach
        void setUp() {
            OffsetDateTime now = OffsetDateTime.now();

            transactionService.addTransaction(new Transaction(10.0, now.minusSeconds(10)));
            transactionService.addTransaction(new Transaction(20.0, now.minusSeconds(20)));

            result = transactionService.getStatistics();
        }

        @Test
        @DisplayName("Then returns the count")
        public void getCount() {
            assertEquals(2, result.getCount());
        }

        @Test
        @DisplayName("Then returns the sum")
        public void getSum() {
            assertEquals(30.0, result.getSum());
        }

        @Test
        @DisplayName("Then returns the avarage")
        public void getAverage() {
            assertEquals(15.0, result.getAverage());
        }
    }
}