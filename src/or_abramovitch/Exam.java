package or_abramovitch;


import exceptions.NumOfAnswersException;

public abstract class Exam implements Examable {
	protected Question[] questionsForTest;
	protected int numOfQuestionsExam;

	
	public Exam(int numOfQuestionsExam) {
		this.numOfQuestionsExam = numOfQuestionsExam;
		questionsForTest =  new Question[numOfQuestionsExam];
	}
	

	public int getNumOfQuestionsExam() {
		return numOfQuestionsExam;
	}

	public Question[] getQuestionsForTes() {
		return questionsForTest;
	}
	
	public abstract boolean createExam(QuestionPool quesPool) throws NumOfAnswersException, CloneNotSupportedException;
	
	public String toString() {
		StringBuffer str = new StringBuffer("The questions are: \n\n");
		for (int i = 0; i < numOfQuestionsExam; i++) {
			str.append("Question " + (i+1) + ": " + questionsForTest[i].toString() + "\n");
		}
		return str.toString();
	}

	public String toStringWithoutAnswers() { 
		StringBuffer str = new StringBuffer("The questions are: \n\n");
		for (int i = 0; i < numOfQuestionsExam; i++) {
			str.append("Question " + (i+1) + ": " + questionsForTest[i].toStringWithoutAnswers() + "\n");
		}
		return str.toString();
	}




}
