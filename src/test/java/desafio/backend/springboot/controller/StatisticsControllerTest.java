package desafio.backend.springboot.controller;

import desafio.backend.springboot.dto.StatisticsResponse;
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

import java.util.DoubleSummaryStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given StatisticsController")
public class StatisticsControllerTest {

    @InjectMocks
    StatisticsController statisticsController;

    @Mock
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        reset(transactionService);
    }

    @Nested
    @DisplayName("When getStatistics is called successfully")
    class WhenGetStatisticsIsCalled {

        private ResponseEntity<StatisticsResponse> response;
        private DoubleSummaryStatistics mockStats;

        @BeforeEach
        void setUp() {
            mockStats = new DoubleSummaryStatistics();
            mockStats.accept(10.0);
            mockStats.accept(20.0);

            when(transactionService.getStatistics()).thenReturn(mockStats);

            response = statisticsController.getStatistics();
        }

        @Test
        @DisplayName("Then get statistics")
        public void getStatistics() {
            verify(transactionService, times(1)).getStatistics();
        }

        @Test
        @DisplayName("Then return 200 ok")
        public void return200Ok() {
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("When getStatistics is called and an error occurred")
    class WhenGetStatisticsIsCalledError {

        private Throwable thrown;

        @BeforeEach
        void setUp() {
            when(transactionService.getStatistics()).thenThrow(new RuntimeException("Database error"));

            thrown = org.assertj.core.api.Assertions.catchThrowable(() ->
                    statisticsController.getStatistics()
            );
        }

        @Test
        @DisplayName("Then throws exception")
        void thenThrowsException() {
            org.junit.jupiter.api.Assertions.assertInstanceOf(RuntimeException.class, thrown);
        }

        @Test
        @DisplayName("Then has correct message")
        void thenHasMessage() {
            assertEquals("Database error", thrown.getMessage());
        }

        @Test
        @DisplayName("Then verify service was called")
        void thenVerifyService() {
            verify(transactionService, times(1)).getStatistics();
        }
    }
}