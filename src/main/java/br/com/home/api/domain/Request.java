package br.com.home.api.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.home.api.domain.enums.RequestState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Request {
	private Long id;
	private String subject;
	private String description;
	private Date creationDate;
	private RequestState state;
	private User user;
	private List<RequestStage> stages = new ArrayList<RequestStage>();
}
