/**
 * 
 */
package com.tec.model;

public class ResponseBean  {

		public boolean valid = true;

	public String message;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
