package com.sejong.sejongpeer.global.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlConstants {
	DEV_SERVER_URL("https://api-sejongpeer.shop"),
	LOCAL_SERVER_URL("http://localhost:8080"),

	DEV_DOMAIN_URL("https://www.api-sejongpeer.shop"),
	LOCAL_DOMAIN_URL("http://localhost:3000"),
	LOCAL_SECURE_DOMAIN_URL("https://localhost:3000"),
	;

	private String value;
}
