package br.com.home.api.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.home.api.domain.enums.RequestState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Request implements Serializable{
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -2365091225366075077L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 75, nullable = false)
	private String subject;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@Column(name= "creation_date" , nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(length = 12, nullable = false)
	@Enumerated(EnumType.STRING)
	private RequestState state;
	
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "request")
	private List<RequestStage> stages = new ArrayList<>();
}
