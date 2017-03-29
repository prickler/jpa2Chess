package com.capgemini.chess.service.to;

import com.capgemini.chess.enums.ChallengeStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class ChallengeTO {
	private Long id;
	private PlayerTO challenger;
	private PlayerTO challenged;
	private ChallengeStatus status;
}
