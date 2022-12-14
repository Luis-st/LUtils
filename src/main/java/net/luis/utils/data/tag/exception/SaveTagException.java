package net.luis.utils.data.tag.exception;

import net.luis.utils.data.tag.Tag;

import java.nio.file.Path;

/**
 *
 * @author Luis-st
 *
 */

public class SaveTagException extends TagException {
	
	private static final long serialVersionUID = 1058513685709077997L;
	
	public SaveTagException() {
		super();
	}
	
	public SaveTagException(String message) {
		super(message);
	}
	
	public SaveTagException(Throwable cause) {
		super(cause);
	}
	
	public SaveTagException(Tag tag, Path path, Throwable cause) {
		super("Tag of type " + tag.getType().getVisitorName() + " cannot be stored in file " + path.toString(), cause);
	}
	
}
