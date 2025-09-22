package co.pragma.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode code;

    protected BusinessException(ErrorCode code){
        super(code.getDefaultMessage());
        this.code = code;
    }

    protected BusinessException(ErrorCode code, String customMessage) {
        super(customMessage);
        this.code = code;
    }
}
