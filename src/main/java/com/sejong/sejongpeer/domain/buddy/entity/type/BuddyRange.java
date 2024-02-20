package com.sejong.sejongpeer.domain.buddy.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BuddyRange {
	COLLEGE("단과대"),
	DEPARTMENT("학과"),
	SAME_COLLEGE("같은 단과대"),
	SAME_DEPARTMENT("같은 학과"),
	ALL("상관없음");
	private final String value;
}
