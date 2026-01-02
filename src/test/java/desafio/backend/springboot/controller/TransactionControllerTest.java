package desafio.backend.springboot.controller;

import desafio.backend.springboot.model.Transaction;
import desafio.backend.springboot.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given TransactionController")
public class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;

    @Mock
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        reset(transactionService);
    }

    @Nested
    @DisplayName("When createTransaction is called")
    class WhenCreateTransactionIsCalled {

        @Nested
        @DisplayName("And the call is a success")
        class AndTheCallIsSuccessfuly {

            private ResponseEntity<Void> response;

            @BeforeEach
            void setUp() {
                // Cenário: Tudo válido
                Transaction validTransaction = new Transaction(10.50, OffsetDateTime.now().minusSeconds(10));
                response = transactionController.createTransaction(validTransaction);
            }

            @Test
            @DisplayName("Then returns 201 created")
            void thenReturns201() {
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
            }

            @Test
            @DisplayName("Then adds a transaction")
            void thenAddsTransaction() {
                verify(transactionService, times(1)).addTransaction(any(Transaction.class));
            }
        }

        @Nested
        @DisplayName("And the call fails")
        class AndTheCallFails {

            @Nested
            @DisplayName("Due to future date")
            class DueToFutureDate {
                private ResponseEntity<Void> response;

                @BeforeEach
                void setUp() {
                    Transaction invalidTransaction = new Transaction(10.50, OffsetDateTime.now().plusDays(1));
                    response = transactionController.createTransaction(invalidTransaction);
                }

                @Test
                @DisplayName("Then returns 422 unprocessableEntity")
                void thenReturns422UnprocessableEntity() {
                    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
                }
            }

            @Nested
            @DisplayName("Due to invalid value")
            class DueToInvalidValue {
                private ResponseEntity<Void> response;

                @BeforeEach
                void setUp() {
                    // Cenário necessário para 100% Branches: Data OK, mas valor <= 0
                    Transaction invalidTransaction = new Transaction(-1.0, OffsetDateTime.now().minusSeconds(1));
                    response = transactionController.createTransaction(invalidTransaction);
                }

                @Test
                @DisplayName("Then returns 422 unprocessableEntity")
                void thenReturns422UnprocessableEntity() {
                    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
                }
            }
        }
    }

    @Nested
    @DisplayName("When deleteTransaction is called")
    class WhenDeleteTransactionIsCalled {

        private ResponseEntity<Void> response;

        @BeforeEach
        void setUp() {
            Transaction dummy = new Transaction(0.0, OffsetDateTime.now());
            response = transactionController.deleteTransaction();
        }

        @Test
        @DisplayName("Then clearTransactions is called")
        void thenClearTransactions() {
            verify(transactionService, times(1)).clearTransactions();
        }

        @Test
        @DisplayName("Then returns 200 ok")
        void thenReturns200Ok() {
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }
}