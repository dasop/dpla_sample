package com.kt.dpla.support.lamp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@ToString
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Host {
	private String name;
	private String ip;
}
