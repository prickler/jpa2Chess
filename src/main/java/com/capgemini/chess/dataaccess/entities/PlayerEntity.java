package com.capgemini.chess.dataaccess.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.capgemini.chess.queries.QueryNames;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "player")
@Getter
@Setter
@NamedQueries({
		@NamedQuery(name = QueryNames.PLAYER_ENTITY_FIND_BY_LVL, query = "select p from PlayerEntity p where p.stats.level = :level "),
		@NamedQuery(name = QueryNames.PLAYER_ENTITY_FIND_BY_ID, query = "select p from PlayerEntity p where p.id = :id ") })
public class PlayerEntity extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "username", nullable = false, length = 45)
	private String username;

	@Column(name = "password", nullable = false, length = 45)
	private String password;

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@Column(name = "surname", nullable = false, length = 45)
	private String surname;

	@Column(name = "email", nullable = true, length = 65, unique = true)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", nullable = false)
	private Calendar birthDate;

	@Embedded
	private StatsEntity stats;
}
