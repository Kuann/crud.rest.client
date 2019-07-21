package me.kuann.crud.rest.client;

public class RestClientException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RestClientException(Exception exception) {
		super(exception);
	}
	
	public RestClientException(String message) {
		super(message);
	}
}
