package or_abramovitch;

import java.util.Random;

import exceptions.NumOfQuestionsException;
import or_abramovitch.Question.Difficulty;

public class AutomaticExam extends Exam implements Examable  {


	public AutomaticExam(QuestionPool quesPoolForExam, int numOfQuestions) throws NumOfQuestionsException, CloneNotSupportedException {
		super(numOfQuestions);
	}

	public boolean checkAnswers (MultipleChoiceQuestion q) {
		int counterAnswers = q.possibleAnswers.length;

		if (counterAnswers < 4)
			return false;

		int counterFalseAns = 0;

		for (int i = 0; i < q.getNumOfPossibleAnswers(); i++) {
			if (q.possibleAnswers[i].getBooleanValue() == false) {
				counterFalseAns++;
			}
		}

		if (counterFalseAns < 3)
			return false;

		return true;


	}

	public QuestionPool createOKQuesPool (QuestionPool quesPoolForExam) throws CloneNotSupportedException {

		QuestionPool OKQuesPool = quesPoolForExam.clone();
		int numOfQues = OKQuesPool.questions.length;
		boolean isValidQues;
		OpenQuestion delete = new OpenQuestion("delete", Difficulty.Easy, "delete");


		for (int i = 0; i < numOfQues; i++) {
			if (OKQuesPool.questions[i] == null) {
				OKQuesPool.questions[i] = delete.clone();
			}
			
			if (OKQuesPool.questions[i].getNumOfPossibleAnswers() > 1) {
				isValidQues = checkAnswers((MultipleChoiceQuestion) OKQuesPool.questions[i]);
				if (isValidQues == false)
					OKQuesPool.questions[i] = delete.clone();
			}
			
		}

		removeNullQues(OKQuesPool);
		return OKQuesPool;
	}


	public void defaultAns(Question[] questionsArr) {
		for (int i = 0; i < questionsArr.length; i++) {
			if (questionsArr[i].getNumOfPossibleAnswers() > 1) {
				((MultipleChoiceQuestion)questionsArr[i]).createDefaultAnswersManualAndAuto(2);
			}

		}

	}


	public void removeNullQues(QuestionPool OKQuesPool) throws CloneNotSupportedException {

		for (int i = 0; i < OKQuesPool.getNumOfQuestions(); i++) {
			if (OKQuesPool.questions[i].getQuestion().equalsIgnoreCase("delete")) {
				OKQuesPool.removeQuestion("delete");
			}

		}
	}


	public boolean createExam(QuestionPool quesPoolForExam) throws CloneNotSupportedException {

		Random r = new Random();
		int randomIndexQues;
		int randomIndexAns;
		int currentIndexQues = 0;
		int currentIndexAns = 0;
		int counter = 0;
		boolean isExists;
		int i = 0;
		int j = 0;

		QuestionPool OKQuesPool = createOKQuesPool(quesPoolForExam).clone();
		
		if (OKQuesPool.getNumOfQuestions() < numOfQuestionsExam) 
			return false;

		int[] indexesQues = new int[OKQuesPool.getNumOfQuestions()*2];
		for (int n = 0; n < indexesQues.length; n++) {
			indexesQues[n] = -1;
		}
		
		int[] indexesAns = new int []{-1, -1, -1, -1};
		

		while (currentIndexQues < numOfQuestionsExam) {
			randomIndexQues = r.nextInt(OKQuesPool.getNumOfQuestions());
			isExists = checkIfExists(indexesQues, randomIndexQues);
			if (isExists == true) {
				continue;
			}
			else {
				if (OKQuesPool.questions[randomIndexQues].getNumOfPossibleAnswers() > 1) {
					MultipleChoiceQuestion randQuestion = new MultipleChoiceQuestion(OKQuesPool.questions[randomIndexQues].getQuestion(), OKQuesPool.questions[randomIndexQues].difficultyLevel);
					if (OKQuesPool.questions[randomIndexQues].getNumOfPossibleAnswers() == 4) {
						this.questionsForTest[currentIndexQues] = OKQuesPool.questions[randomIndexQues].clone();
					}
					else {//get 4rand answers
						while (currentIndexAns < 4) {
							randomIndexAns = r.nextInt(OKQuesPool.questions[randomIndexQues].getNumOfPossibleAnswers());
							isExists = checkIfExists(indexesAns, randomIndexAns);
							if (isExists == true)
								continue;
							Answer randAnswer = ((MultipleChoiceQuestion)OKQuesPool.questions[randomIndexQues]).possibleAnswers[randomIndexAns];

							if (randAnswer.getBooleanValue() == true) {
								counter++;
								if (counter <= 1) {
									((MultipleChoiceQuestion)randQuestion).addAnswer(randAnswer.getAnswer(), randAnswer.getBooleanValue());
									currentIndexAns++;
									indexesAns[j] = randomIndexAns;
									j++;
									continue;
								}
								else
									continue;
							}
							((MultipleChoiceQuestion)randQuestion).addAnswer(randAnswer.getAnswer(), randAnswer.getBooleanValue());
							currentIndexAns++;
							indexesAns[j] = randomIndexAns;
							j++;
						}

						this.questionsForTest[currentIndexQues] = randQuestion.clone();
						indexesQues[i] = randomIndexQues;
						i++;
					}
				}

				else { 
					this.questionsForTest[currentIndexQues] = OKQuesPool.questions[randomIndexQues].clone();
					indexesQues[i] = randomIndexQues;
					i++;
				}




			}
			currentIndexQues++;

		}
		
		
		defaultAns(questionsForTest);
		return true;

}


public boolean checkIfExists(int[] arr, int index) {

	int counter = 0;

	for (int i = 0; i < arr.length; i++) {
		if (arr[i] == index)
			counter++;
	}

	if (counter > 0)
		return true;

	return false;



}
}