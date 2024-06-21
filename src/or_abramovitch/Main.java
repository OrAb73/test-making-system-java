package or_abramovitch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import exceptions.NumOfAnswersException;
import exceptions.NumOfQuestionsException;

public class Main {

	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws NumOfQuestionsException, CloneNotSupportedException, IOException, ClassNotFoundException {

		final int EXIT = -1;
		int answer;
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		String testFile = "exam_"+date.format(dateFormat)+".txt";
		String solutionFile = "solution_"+date.format(dateFormat)+".txt";

//		QuestionPool quesPool = new QuestionPool(5); //Creating a question pool manually as requested:
//
//		quesPool.addNewMultiQuestion("What is the sum of 2+2?", Difficulty.Easy);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("What is the sum of 2+2?")).addAnswer("11", false);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("What is the sum of 2+2?")).addAnswer("4", true);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("What is the sum of 2+2?")).addAnswer("2", false);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("What is the sum of 2+2?")).addAnswer("7", false);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("What is the sum of 2+2?")).addAnswer("9", false);
//
//
//		quesPool.addNewMultiQuestion("In what year was the state of Israel established?", Difficulty.Fair);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("In what year was the state of Israel established?")).addAnswer("1960", false);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("In what year was the state of Israel established?")).addAnswer("1948", true);
//		((MultipleChoiceQuestion)quesPool.getQuestionByText("In what year was the state of Israel established?")).addAnswer("1970", false);
//
//		quesPool.addNewOpenQuestion("What year did Neil Armstrong first land on the moon?", Difficulty.Hard, "1969");
//		
//		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("QuestionPool.dat"));
//		outFile.writeObject(quesPool);
//		outFile.close();
		
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("QuestionPool.dat"));
		QuestionPool quesPool = (QuestionPool)inFile.readObject();
//		System.out.println(quesPoolFromFile.toString());
		inFile.close();


		do {
			System.out.println();
			System.out.println("Choose one of the following options: ");
			System.out.println("1) Show all the questions and their answers");
			System.out.println("2) Add new answer to an exiting question");
			System.out.println("3) Add new question");
			System.out.println("4) Remove answer to an exiting question");
			System.out.println("5) Remove question"); 
			System.out.println("6) Create test and solution files manually");
			System.out.println("7) Create test and solution files automatically");
			System.out.println(EXIT + ") To exit");
			System.out.println("Enter your choice: ");
			answer = input.nextInt();
			input.nextLine();

			switch (answer) {
			case 1:
				System.out.println(quesPool.toString());
				break;
			case 2:
				addAnswersToQuestions(quesPool);
				break;
			case 3:
				addNewQuestion(quesPool);
				break;
			case 4: 
				removeAnswer(quesPool);
				break; 
			case 5:
				removeQuestion(quesPool);
				break;
			case 6:
				createManualTest(quesPool, testFile, solutionFile);
				break;
			case 7:
				createAutomaticTest(quesPool, testFile, solutionFile);
				break;
			case EXIT:
				System.out.println("Goodbye!"); 
				break;
			default:
				System.out.println("Invaild option.");
				break;
			}

		} while (answer != EXIT);
	}


	public static void addAnswersToQuestions(QuestionPool quesPool) {
		boolean isContinue = true;

		do {
			int theQuestion;
			String answerText;
			boolean truthValue;
			System.out.println(quesPool.toString());
			System.out.println("Please enter the question's number (Please note that option is only available for multiple choice questions): ");
			theQuestion = input.nextInt();
			input.nextLine();
			if (quesPool.getQuestionByIndex(theQuestion) == null) {
				System.out.println("Question not found.");
				return;
			}
			if (quesPool.getQuestionByIndex(theQuestion) instanceof OpenQuestion) {
				System.out.println("Cannot add answers to an open question!");
				return;
			}
			MultipleChoiceQuestion chosenQues = (MultipleChoiceQuestion)quesPool.getQuestionByIndex(theQuestion);
			if(chosenQues.getNumOfPossibleAnswers() >= 8) {
				System.out.println("Could not add more answers to this question.");
				return;
			}
			System.out.println(chosenQues.toString());
			System.out.println("Please enter the answer you would like to add to this question: ");
			answerText = input.nextLine();
			System.out.println("Is this answer true or false?");
			truthValue = input.nextBoolean();
			input.nextLine();
			boolean res = chosenQues.addAnswer(answerText, truthValue);

			if(res) {
				System.out.println("The answer has been added to the selected question.");
				System.out.println("Add another answer? Please type 'Y' for 'yes', or 'N' for 'no'");
				char answer = input.next().charAt(0);
				input.nextLine();
				if (answer == 'n' || answer == 'N') {
					isContinue = false;
				}
				else if (answer != 'y' && answer != 'Y') {
					System.out.println("Invalid input.");
				}

			}
			else {
				System.out.println("Could not add more answers to this question.");
				isContinue = false;
			}


		} while (isContinue);
	}

	public static void addNewQuestion(QuestionPool quesPool) {
		boolean isContinue = true;
		do {
			int questionType;
			String newQuestion;
			int numOfAnswers;
			String answerText;
			boolean truthValue;
			System.out.println("For your convenience, these are the questions and answers in the system so far: ");
			System.out.println(quesPool.toString());
			System.out.println("Please choose '0' for open question or '1' for multiple choice question: ");
			questionType = input.nextInt();
			input.nextLine();
			if (questionType == 0) {
				System.out.println("Please enter new question: ");
				newQuestion = input.nextLine();
				System.out.println("Please enter difficulty level ('Easy', 'Fair', 'Hard'");
				Question.Difficulty level = Question.Difficulty.valueOf(input.nextLine());
				System.out.println("Please enter school's answer: ");
				answerText = input.nextLine();
				quesPool.addNewOpenQuestion(newQuestion, level, answerText);

			}
			else if (questionType == 1) {
				System.out.println("Please enter new question: ");
				newQuestion = input.nextLine();
				System.out.println("Please enter difficulty level ('Easy', 'Fair', 'Hard'");
				Question.Difficulty level = Question.Difficulty.valueOf(input.nextLine());
				quesPool.addNewMultiQuestion(newQuestion, level);
				System.out.println("How many answers would you like to enter to this question? Please note that you cannot add more than 8 answers."); //Saving places for the default answers: "More than..." and "Neither answer..."
				numOfAnswers = input.nextInt();
				if(numOfAnswers > 8) {
					System.out.println("Cannot add more than 8 answers!");
					return;
				}
				input.nextLine();
				for (int i = 0; i < numOfAnswers; i++) {
					System.out.println("Please enter answer: ");
					answerText = input.nextLine();
					System.out.println("Is this answer true or false?");
					truthValue = input.nextBoolean();
					input.nextLine();
					((MultipleChoiceQuestion)(quesPool.getQuestionByText(newQuestion))).addAnswer(answerText, truthValue);
				}

			}

			System.out.println("The question has been added.");
			System.out.println("Add another question? Please type 'Y' for 'yes', or 'N' for 'no'");
			char answer1 = input.next().charAt(0);
			input.nextLine();
			if (answer1 == 'n' || answer1 == 'N') {
				isContinue = false;
			}
			else if (answer1 != 'y' && answer1 != 'Y') {
				System.out.println("Invalid input.");
			}

		} while (isContinue);

	}


	public static void removeAnswer(QuestionPool quesPool) {
		boolean isContinue = true;
		do {
			int theQuestion;
			int answerToRemove;
			System.out.println("For your convenience, these are the questions and answers in the system so far: ");
			System.out.println(quesPool.toString());
			System.out.println("Please enter the question's number (Please note that option is only available for multiple choice questions): ");
			theQuestion = input.nextInt();
			input.nextLine();
			if (quesPool.getQuestionByIndex(theQuestion) == null) {
				System.out.println("Question not found.");
				return;
			}
			if (quesPool.getQuestionByIndex(theQuestion) instanceof OpenQuestion) {
				System.out.println("Cannot remove default school's answer!");
				return;
			}
			System.out.println("Please enter the answer you would like to remove from this question: ");
			answerToRemove = input.nextInt();
			input.nextLine();
			MultipleChoiceQuestion chosenQues = (MultipleChoiceQuestion)quesPool.getQuestionByIndex(theQuestion); 
			if (chosenQues.getAnswerByIndex(answerToRemove) == null) {
				System.out.println("Answer not found.");
				return;
			}
			String answerText = chosenQues.getAnswerByIndex(answerToRemove).getAnswer();

			boolean res = chosenQues.removeAnswer(answerText);
			if(res) {
				System.out.println("The answer has been removed from the selected question.");
				System.out.println("Remove another answer? Please type 'Y' for 'yes', or 'N' for 'no'");
				char answer1 = input.next().charAt(0);
				input.nextLine();
				if (answer1 == 'n' || answer1 == 'N') {
					isContinue = false;
				}
				else if (answer1 != 'y' && answer1 != 'Y') {
					System.out.println("Invalid input.");
				}

			}

		} while (isContinue);

	}

	public static void removeQuestion(QuestionPool quesPool) {
		boolean isContinue = true;
		do {
			int questionToRemove;
			System.out.println("For your convenience, these are the questions and answers in the system so far: ");
			System.out.println(quesPool.toString());
			System.out.println("Please enter the question's number you would like to remove: ");
			questionToRemove = input.nextInt();
			input.nextLine();
			if (quesPool.getQuestionByIndex(questionToRemove) == null) {
				System.out.println("Question not found.");
				return;
			}
			String questionText = quesPool.getQuestionByIndex(questionToRemove).getQuestion();
			quesPool.removeQuestion(questionText);
			System.out.println("The question has been removed.");
			System.out.println("Remove another question? Please type 'Y' for 'yes', or 'N' for 'no'");
			char answer1 = input.next().charAt(0);
			input.nextLine();
			if (answer1 == 'n' || answer1 == 'N') {
				isContinue = false;
			}
			else if (answer1 != 'y' && answer1 != 'Y') {
				System.out.println("Invalid input.");
			}

		} while (isContinue);

	}

	public static void createManualTest(QuestionPool quesPool, String testFile, String solutionFile) throws FileNotFoundException, NumOfQuestionsException, CloneNotSupportedException   {
		int numOfQuestionsTest = 0;
		int choice;
		boolean keep;
		boolean isNumOfQuesOk = false;
		boolean isNumOfAnsOk = false;
		int numOfAnsToRemove; //making sure the user won't delete too many answers

		while (!isNumOfQuesOk) {
			System.out.println("Here is the current question pool: ");
			System.out.println(quesPool.toString());
			try {
				System.out.println("How many questions would you like to put in the test?");
				numOfQuestionsTest = input.nextInt();
				if (numOfQuestionsTest > 3) 
					throw new NumOfQuestionsException();

				isNumOfQuesOk = true;	

			} catch (NumOfQuestionsException e) {
				System.out.println(e.getMessage());
				input.nextLine();
			}

		}
		if(numOfQuestionsTest > quesPool.getNumOfQuestions()) {
			System.out.println("There aren't enough questions in the current question pool.");
			return;
		}
		input.nextLine();
		QuestionPool questionsForTest = new QuestionPool(numOfQuestionsTest);
		while (!isNumOfAnsOk) {
			try { 
				for (int i = 0; i < numOfQuestionsTest; i++) {
					System.out.println("Please enter the number of question you would like to put in the test: ");
					choice = input.nextInt();
					input.nextLine();
					if (quesPool.getQuestionByIndex(choice) instanceof OpenQuestion) {
						OpenQuestion chosenQuestion = new OpenQuestion((OpenQuestion)quesPool.getQuestionByIndex(choice));
						questionsForTest.addExistingOpenQuestion(chosenQuestion);
					}
					if (quesPool.getQuestionByIndex(choice) instanceof MultipleChoiceQuestion) {
						if (((MultipleChoiceQuestion)quesPool.getQuestionByIndex(choice)).getNumOfPossibleAnswers() <= 3) {
							throw new NumOfAnswersException();
						}
						isNumOfAnsOk = true;
						MultipleChoiceQuestion chosenQuestion = new MultipleChoiceQuestion((MultipleChoiceQuestion)quesPool.getQuestionByIndex(choice));
						System.out.println("Would you like to remove answers from this question? Please replay with 'true' or 'false': ");
						keep = input.nextBoolean();
						input.nextLine();
						if (keep) {
							int numOfAnsBeforeChanges = chosenQuestion.getNumOfPossibleAnswers();
							if (numOfAnsBeforeChanges == 4) {
								System.out.println("Question must have 4 answers or more. Faild to remove answers.");
							}
							else {
								numOfAnsToRemove = numOfAnsBeforeChanges-4;
								for(int j = 0; j < numOfAnsToRemove; j++) {
									System.out.println("Please enter the number of answer you would like to remove: ");
									choice = input.nextInt();
									input.nextLine();
									chosenQuestion.removeAnswer(chosenQuestion.getAnswerByIndex(choice).getAnswer());
									System.out.println("Answer has been removed.");
								}
							}

						}
						chosenQuestion.createDefaultAnswersManualAndAuto(1);
						questionsForTest.addExistingMultiQuestion(chosenQuestion);
					}

				}
				ManualExam manuelExam = new ManualExam(numOfQuestionsTest);
				manuelExam.createExam(questionsForTest);
				createTestFile(manuelExam, testFile);
				createSolutionFile(manuelExam, solutionFile);

			} catch (NumOfAnswersException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public static void createAutomaticTest(QuestionPool quesPool, String testFile, String solutionFile) throws FileNotFoundException, NumOfQuestionsException, CloneNotSupportedException   {
		int numOfQuestionsTest = 0;
		boolean isNumOfQuesOk = false;

		while (!isNumOfQuesOk) {
			try {
				System.out.println("How many questions would you like to put in the test?");
				numOfQuestionsTest = input.nextInt();
				if (numOfQuestionsTest > 3) 
					throw new NumOfQuestionsException();

				isNumOfQuesOk = true;	

			} catch (NumOfQuestionsException e) {
				System.out.println(e.getMessage());
				input.nextLine();
			}

		}

		input.nextLine();
		AutomaticExam automaticExam = new AutomaticExam(quesPool, numOfQuestionsTest);
		boolean isOK = automaticExam.createExam(quesPool);
		if (!isOK) {
			System.out.println("There aren't enough valid questions.");
			return;
		}
		createTestFile(automaticExam, testFile);
		createSolutionFile(automaticExam, solutionFile);


	}



	public static void createTestFile(Exam questionsForTest,String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(file);
		pw.println(questionsForTest.toStringWithoutAnswers());
		pw.close();
		System.out.println("Exam file has been created successfully.");
	}

	public static void createSolutionFile(Exam questionsForTest,String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(file);
		pw.println(questionsForTest.toString());
		pw.close();
		System.out.println("Solution file has been created successfully.");
	}
	

}
