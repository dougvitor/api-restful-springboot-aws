package br.com.home.api.domain;

import java.util.Date;

import br.com.home.api.domain.enums.RequestState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestStage {
	private Long id;
	private String description;
	private Date realizationDate;
	private RequestState state;
	private Request request;
	private User user;
}
