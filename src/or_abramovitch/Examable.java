package or_abramovitch;

import exceptions.NumOfAnswersException;

public interface Examable {
	
	boolean createExam(QuestionPool quesPool) throws NumOfAnswersException, CloneNotSupportedException;

}
