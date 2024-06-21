package or_abramovitch;

import java.io.Serializable;

public class Answer implements Cloneable, Serializable {

	private String answer;
	private boolean booleanValue;

	public Answer(String answer, boolean booleanValue) {
		this.answer = answer;
		this.booleanValue = (booleanValue);
	}

	public String getAnswer() {
		return answer;
	}

	public boolean getBooleanValue() {
		return booleanValue;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(answer + " - " + booleanValue);
		return str.toString();
	}
	
	public Answer clone() throws CloneNotSupportedException { //think how to make deep clone*****
		return (Answer)super.clone();
	}


}
