package co.pragma.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    FORBIDDEN("No tiene permisos para realizar esta acción."),
    TECHNICAL_ERROR("Ocurrió un error técnico, intente más tarde."),
    INVALID_REQUEST("La solicitud es inválida o malformada."),
    INTERNAL_SERVER_ERROR("Ocurrió un error interno, por favor intente más tarde."),
    DB_ERROR("Error de base de datos");

    private final String defaultMessage;

    ErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
