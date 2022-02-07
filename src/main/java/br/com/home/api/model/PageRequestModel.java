package br.com.home.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageRequestModel {

    private int page = 0;

    private int size = 10;

    private String sort = "";

    private String search = "";

    public PageRequestModel(Map<String, String> params){
        if(params.containsKey("page")) page = Integer.parseInt(params.get("page"));
        if(params.containsKey("size")) size = Integer.parseInt(params.get("size"));
        if(params.containsKey("sort")) sort = params.get("sort");
        if(params.containsKey("search")) search = params.get("search");
    }

    public PageRequest toSpringPageRequest(){
        List<Sort.Order> orders = Arrays.stream(sort.split(","))
                .filter(prop -> prop.trim().length() > 0)
                .map(column -> {
                    column = column.trim();
                    if(column.startsWith("-")){
                        column = column.replace("-", "").trim();
                        return Sort.Order.desc(column);
                    }
                    return Sort.Order.asc(column);
                })
                .collect(Collectors.toList());

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
