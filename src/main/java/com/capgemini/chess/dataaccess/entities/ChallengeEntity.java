package com.capgemini.chess.dataaccess.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.queries.QueryNames;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "challenge")
@NamedQueries({
		@NamedQuery(name = QueryNames.CHALLENGE_ENTITY_FIND_BY_CHALLENGER_ID_AND_STATUS, query = "select c from ChallengeEntity c where c.challengingPlayer.id = :ID and c.status = :status"),
		@NamedQuery(name = QueryNames.CHALLENGE_ENTITY_FIND_BY_CHALLENGED_ID_AND_STATUS, query = "select c from ChallengeEntity c where c.challengedPlayer.id = :ID and c.status = :status"),
		@NamedQuery(name = QueryNames.CHALLENGE_ENTITY_FIND_BY_CHALLENGER_ID, query = "select c from ChallengeEntity c where c.challengingPlayer.id = :ID")})
public class ChallengeEntity extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "status", nullable = false, length = 45)
	private ChallengeStatus status;

	@ManyToOne
	@JoinColumn(name = "challenging_player", nullable = false)
	private PlayerEntity challengingPlayer;

	@ManyToOne
	@JoinColumn(name = "challenged_player", nullable = false)
	private PlayerEntity challengedPlayer;
}
