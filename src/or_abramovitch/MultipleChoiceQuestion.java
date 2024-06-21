package or_abramovitch;


public class MultipleChoiceQuestion extends Question {

	private static final int MAX_ANSWERS = 10;
	protected Answer[] possibleAnswers; ///////
	private int numOfPossibleAnswers;

	public MultipleChoiceQuestion(String question, Difficulty difficultyLevel) {
		super(question, difficultyLevel);
		this.possibleAnswers = new Answer[MAX_ANSWERS];
		numOfPossibleAnswers = 0;
	}

	public MultipleChoiceQuestion(MultipleChoiceQuestion other) {
		super(other.question, other.difficultyLevel);
		this.serialNum = other.serialNum; 
		numOfPossibleAnswers = other.numOfPossibleAnswers;
		possibleAnswers = new Answer[other.possibleAnswers.length];
		for (int i = 0; i < other.numOfPossibleAnswers; i++) {
			possibleAnswers[i] = other.possibleAnswers[i];
		}

	}

	public int getNumOfPossibleAnswers() {
		return numOfPossibleAnswers;
	}

	public Answer getAnswerByIndex(int index) {
		if (possibleAnswers[index-1] != null) {
			return possibleAnswers[index-1];
		}
		else {
			return null;
		}

	}
	
	

	public boolean addAnswer(String answer, boolean truthValue) {
		if (numOfPossibleAnswers < possibleAnswers.length) {
			possibleAnswers[numOfPossibleAnswers++] = new Answer(answer, truthValue);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean removeAnswer(String answer) {
		int i = 0;
		for (i = 0; i < numOfPossibleAnswers; i++) {
			if (possibleAnswers[i].getAnswer().equalsIgnoreCase(answer)) {
				possibleAnswers[i] = possibleAnswers[--numOfPossibleAnswers];
				return true;
			}
		}
		return false;
	}

	public void createDefaultAnswersManualAndAuto(int choice) {
		int counter = 0;
		int manual = 1;
		for (int i = 0; i < numOfPossibleAnswers; i++) {
			if (possibleAnswers[i].getBooleanValue() == true) {
				counter++;
			}
		}
		
		if (choice == manual) {
			if (counter == 0) {
				possibleAnswers[numOfPossibleAnswers++] = new Answer("More than one correct answer", false);
				possibleAnswers[numOfPossibleAnswers++] = new Answer("Neither answer is correct", true);
			}
			else if (counter > 1) {
				possibleAnswers[numOfPossibleAnswers++] = new Answer("More than one correct answer", true);
				possibleAnswers[numOfPossibleAnswers++] = new Answer("Neither answer is correct", false);
			}
			else {
				possibleAnswers[numOfPossibleAnswers++] = new Answer("More than one correct answer", false);
				possibleAnswers[numOfPossibleAnswers++] = new Answer("Neither answer is correct", false);
			}
		}
		
		else {
			if (counter == 0) {
				possibleAnswers[numOfPossibleAnswers++] = new Answer("Neither answer is correct", true);
			}
			else if (counter > 1) {
				possibleAnswers[numOfPossibleAnswers++] = new Answer("Neither answer is correct", false);
			}
			else {
				possibleAnswers[numOfPossibleAnswers++] = new Answer("Neither answer is correct", false);
			}
		}

	

	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer(super.toString()); 
		for (int i = 0; i < numOfPossibleAnswers; i++) {
			str.append((i+1) + ") " + possibleAnswers[i].toString() + "\n");
		}
		return str.toString();
	}

	public String toStringWithoutAnswers() {
		StringBuffer str = new StringBuffer(super.toString()); 
		for (int i = 0; i < numOfPossibleAnswers; i++) {
			str.append((i+1) + ") " + possibleAnswers[i].getAnswer() + "\n");
		}
		return str.toString();
	}



}
