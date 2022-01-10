package br.com.home.api.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.home.api.domain.enums.Role;
import br.com.home.api.service.util.HashUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 4453811818303420425L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 75, nullable = false)
	private String name;
	
	@Column(length = 75, nullable = false, unique = true)
	private String email;

	@Getter(onMethod = @__({@JsonIgnore}))
	@Setter(onMethod = @__({@JsonProperty}))
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 20, nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "owner")
	private List<Request> requests = new ArrayList<Request>();

	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "owner")
	private List<RequestStage> stages = new ArrayList<RequestStage>();
}
