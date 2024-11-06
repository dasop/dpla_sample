package com.kt.dpla.support.lamp.vo;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class User {
	private	String id;
	private	String ip;
	private	String type;
	private	String agent;
}
