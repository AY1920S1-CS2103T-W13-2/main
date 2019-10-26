package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an add question command, specific to a quiz.
 */
public class QuizAddQuestionCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an existing question to an existing quiz\n"
            + "Parameters:\n"
            + "add quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "questionNumber/ [QUESTION_NUMBER]\n"
            + "Example: questionNumber/ 3 (Specifies the third question in the question bank)\n\n"
            + "quizQuestionNumber/ [QUIZ_QUESTION_NUMBER]\n"
            + "Example: quizQuestionNumber/ 2 (Specifies the question number in the quiz to add to)";

    private final String quizId;
    private final int questionNumber;
    private final int quizQuestionNumber;

    /**
     * Creates a QuizAddQuestionCommand instance with the appropriate attributes.
     * @param quizId The identifier of the quiz.
     * @param questionNumber The question number in the question bank to be added.
     * @param quizQuestionNumber The quiz question number to be added to.
     */
    public QuizAddQuestionCommand(String quizId, int questionNumber, int quizQuestionNumber) {
        this.quizId = quizId;
        this.questionNumber = questionNumber;
        this.quizQuestionNumber = quizQuestionNumber;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        boolean isSuccess = model.addQuizQuestion(quizId, questionNumber, quizQuestionNumber);
        if (isSuccess) {
            return new CommandResult(generateSuccessMessage());
        } else {
            return new CommandResult(generateFailureMessage());
        }
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Added question: " + questionNumber + " to quiz: " + quizId + ".";
    }

    /**
     * Generates a command execution failure message.
     * @return The String representation of a failure message.
     */
    private String generateFailureMessage() {
        return "There is no quiz with the ID of " + quizId + ".";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuizAddQuestionCommand)) {
            return false;
        }

        // state check
        QuizAddQuestionCommand e = (QuizAddQuestionCommand) other;
        return this.quizId.equals(e.quizId);
    }

}
