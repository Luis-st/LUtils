package net.luis.utils.data.tag.exception;

/**
 *
 * @author Luis-st
 *
 */

public class InvalidTagException extends TagException {
	
	private static final long serialVersionUID = -8636790467022682132L;
	
	public InvalidTagException(int id) {
		super("Invalid tag ID: " + id);
	}
	
	public InvalidTagException(int id, Throwable cause) {
		super("Invalid tag ID: " + id, cause);
	}
	
}