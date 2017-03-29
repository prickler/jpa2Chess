package com.capgemini.chess.dataaccess.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class StatsEntity {
	@Column(name = "number_of_wins", nullable = false, length = 45)
	@ColumnDefault("0")
	private Long numberOfWins;

	@Column(name = "number_of_losses", nullable = false, length = 45)
	@ColumnDefault("0")
	private Long numberOfLosses;

	@Column(name = "number_of_ties", nullable = false, length = 45)
	@ColumnDefault("0")
	private Long numberOfTies;

	@Column(name = "level", nullable = false, length = 45)
	@ColumnDefault("1")
	private Long level;

	@Column(name = "ranking", nullable = false, length = 45)
	@ColumnDefault("1000")
	private Long ranking;
}
