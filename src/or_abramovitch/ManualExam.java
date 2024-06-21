package or_abramovitch;

import exceptions.NumOfQuestionsException;

public class ManualExam extends Exam  {
	
	public ManualExam(int numOfQuestionsExam) throws NumOfQuestionsException, CloneNotSupportedException {
		super(numOfQuestionsExam);
	}
	
	public boolean createExam(QuestionPool quesPoolForExam) throws CloneNotSupportedException {
		for (int i = 0; i < quesPoolForExam.getNumOfQuestions(); i++) {
			questionsForTest[i] = (quesPoolForExam.getQuestionByIndex(i+1)).clone();
		}
		return true;
	
	}



}
