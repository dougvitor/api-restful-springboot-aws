package br.com.home.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageModel<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -5524754496236730177L;

    private int totalElements;

    private int pageSize;

    private int totalPages;

    private Collection<T> elements;
}
