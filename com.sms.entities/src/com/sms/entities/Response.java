package com.sms.entities;

public class Response<T> {
	public T data;
	public int code;
	public String errorOutput;
	public boolean success=false;
}
