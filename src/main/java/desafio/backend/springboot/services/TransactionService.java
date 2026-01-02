package desafio.backend.springboot.services;

import desafio.backend.springboot.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransactionService {

    // salvar transações na memória
    private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<Transaction>();

    // adiciona a transação na memória
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // limpa todas as transações
    public void clearTransactions() {
        transactions.clear();
    }

    // retorna as estatísticas
    public DoubleSummaryStatistics getStatistics() {
        OffsetDateTime now = OffsetDateTime.now();
        return transactions.stream()
//                .filter(t -> t.getDataHora().isAfter(now.minusSeconds(60)))
                .mapToDouble(Transaction::getValor)
                .summaryStatistics();
    }
}
