package exceptions;

public class NumOfQuestionsException extends Exception {
	
	public NumOfQuestionsException() {
		super("Faild creating the exam because more than 3 questions were chosen.");
	}
	

}
