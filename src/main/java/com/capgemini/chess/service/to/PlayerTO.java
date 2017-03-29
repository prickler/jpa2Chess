package com.capgemini.chess.service.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class PlayerTO {
	private Long userID;
	private Long level;
	private Long ranking;
	private String username;
	private String email;
}
