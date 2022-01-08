package br.com.home.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 6027804553663180296L;

    private int code;

    private String message;

    private Date date;
}
