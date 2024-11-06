package com.kt.dpla.support.lamp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kt.dpla.support.lamp.type.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Response {
	private ResponseType type;
	private	String code;
	private	String desc;
	private	String duration;

	public static Response success(){
		return new Response(ResponseType.INFORMATION, "", "", "");
	}
}
