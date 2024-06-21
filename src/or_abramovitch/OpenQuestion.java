package or_abramovitch;

public class OpenQuestion extends Question {

	private Answer schoolAnswer;
	private int getNumOfPossibleAnswers = 1;

	public OpenQuestion(String question, Difficulty difficultyLevel, String schoolAnswer) {
		super(question, difficultyLevel);
		this.schoolAnswer = new Answer(schoolAnswer, true);
	}
	
	public OpenQuestion(OpenQuestion other) {
		super(other.question, other.difficultyLevel);
		this.serialNum = other.serialNum;
		this.schoolAnswer = new Answer(other.schoolAnswer.getAnswer(), true);
	}
	
	public int getNumOfPossibleAnswers() {
		return getNumOfPossibleAnswers;
	}
	
	public String getSchoolAnswer() {
		return schoolAnswer.getAnswer();
	}
	
	public Answer getAnswerByIndex(int index) {
		return schoolAnswer;
	}


	@Override
	public String toString() {
		StringBuffer str = new StringBuffer(super.toString()); 
		str.append(schoolAnswer.getAnswer());

		return str.toString();
	}

	public String toStringWithoutAnswers() {
		return super.toString();
	}
	
	



}
