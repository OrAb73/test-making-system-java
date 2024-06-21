package or_abramovitch;

import java.io.Serializable;

import or_abramovitch.Question.Difficulty;

public class QuestionPool implements Cloneable, Serializable {

	protected Question[] questions; 
	private int numOfQuestions;

	public QuestionPool (int numOfQuestions) {
		questions = new Question[numOfQuestions];
		this.numOfQuestions = 0;
	}

	public QuestionPool (QuestionPool other) {
		this.numOfQuestions = other.numOfQuestions;
		questions = new Question[other.questions.length];
		for (int i = 0; i < other.numOfQuestions; i++) {
			questions[i] = other.questions[i];
		}
	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public Question getQuestionByText (String question) {
		for (int i = 0; i < numOfQuestions; i++) {
			if (questions[i].getQuestion().equalsIgnoreCase(question)) {
				return questions[i];
			}
		}

		return null;
	}

	public Question getQuestionByIndex (int index) {
		if (questions[index-1] != null) {
			return questions[index-1];
		}
		else {
			return null;
		}

	}

	public boolean addNewOpenQuestion(String question, Difficulty difficultyLevel, String schoolAnswer) {

		if (numOfQuestions >= questions.length) {
			return false; //full capacity 
		}
		else {
			questions[numOfQuestions++] = new OpenQuestion(question, difficultyLevel, schoolAnswer);
			return true;
		} 
	}


	public boolean addNewMultiQuestion(String question, Difficulty difficultyLevel) {
		if (numOfQuestions >= questions.length) {
			return false; //full capacity 
		} 
		else {
			questions[numOfQuestions++] = new MultipleChoiceQuestion(question, difficultyLevel);
			return true;
		}
		
	}

	public boolean addExistingMultiQuestion(MultipleChoiceQuestion question) {
		if (numOfQuestions < questions.length) {
			questions[numOfQuestions++] = new MultipleChoiceQuestion(question);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addExistingOpenQuestion(OpenQuestion question) {
		if (numOfQuestions < questions.length) {
			questions[numOfQuestions++] = new OpenQuestion(question);
			return true;
		}
		else {
			return false;
		}
	}



	public boolean removeQuestion(String question) {
		for (int i = 0; i < numOfQuestions; i++) {
			if (questions[i].getQuestion().equalsIgnoreCase(question)) {
				questions[i] = questions[--numOfQuestions];
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("The questions are: \n\n");
		for (int i = 0; i < numOfQuestions; i++) {
			str.append("Question " + (i+1) + ": " + questions[i].toString() + "\n");
		}
		return str.toString();
	}

	public String toStringWithoutAnswers() { 
		StringBuffer str = new StringBuffer("The questions are: \n\n");
		for (int i = 0; i < numOfQuestions; i++) {
			str.append("Question " + (i+1) + ": " + questions[i].toStringWithoutAnswers() + "\n");
		}
		return str.toString();
	}
	
	public QuestionPool clone() throws CloneNotSupportedException {
		QuestionPool temp = (QuestionPool)super.clone();
		temp.questions = questions.clone();
		return temp;
	}


}
