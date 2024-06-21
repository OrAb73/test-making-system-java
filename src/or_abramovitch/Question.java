package or_abramovitch;

import java.io.Serializable;

public abstract class Question implements Cloneable, Serializable{
	
	public enum Difficulty {Easy, Fair, Hard};
	protected Difficulty difficultyLevel;
	protected String question;
	protected static int counter = 1;
	protected int serialNum;


	public Question(String question, Difficulty difficultyLevel) {
		this.question = question;
		this.difficultyLevel = difficultyLevel;
		serialNum = counter++;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public int getSerialNum() {
		return serialNum;
	}
	
	public abstract int getNumOfPossibleAnswers();
	
	public abstract Answer getAnswerByIndex(int index);
	
	
	public String toString() {
		StringBuffer str = new StringBuffer(question + " (Question's serial number: " + serialNum+ " ,difficulty level: " +difficultyLevel+")" + "\n");
		return str.toString();
	}
	
	public abstract String toStringWithoutAnswers();
	
	@Override
	public Question clone() throws CloneNotSupportedException { //think how to make deep clone*****
		return (Question)super.clone();
	}

}
