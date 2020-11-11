package com.neo.visitor.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PagenationResponse<T> {

	private int code = 200;
	private String message;
	private boolean check;
    private List<T> response;
    private Pagenation pagenation;

}
