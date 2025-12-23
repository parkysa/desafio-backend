package desafio.backend.springboot.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class TransactionRequest {

    @NotNull
    private double valor;

    @NotNull
    private OffsetDateTime dataHora;

    public TransactionRequest(OffsetDateTime dataHora, double valor) {
        this.dataHora = dataHora;
        this.valor = valor;
    }
}
