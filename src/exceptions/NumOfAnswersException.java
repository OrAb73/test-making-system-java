package exceptions;

public class NumOfAnswersException extends Exception {

	public NumOfAnswersException() {
		super("Faild creating the exam because the chosen question has 3 answers or under.");
	}
	
}

