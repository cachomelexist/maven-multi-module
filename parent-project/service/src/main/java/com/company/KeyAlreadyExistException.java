package com.company;

import java.lang.Exception;

public class KeyAlreadyExistException extends Exception {
	public KeyAlreadyExistException(){}
	public KeyAlreadyExistException(String message){
		super(message);
	}
}