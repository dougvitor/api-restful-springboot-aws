package br.com.home.api.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ApiErrorList extends ApiError{
    @Serial
    private static final long serialVersionUID = -4137397667329120474L;

    private List<String> errors;

    public ApiErrorList(int code, String message, Date date, List<String> errors){
        super(code, message, date);
        this.errors = errors;
    }
}
