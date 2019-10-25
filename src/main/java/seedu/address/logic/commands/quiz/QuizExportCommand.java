package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.io.IOException;

/**
 * Represents an export command, specific to a quiz.
 */
public class QuizExportCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the questions & answers for a quiz to a html file\n"
            + "Parameters:\n"
            + "export/ quizID/ [QUIZ_ID]\n"
            + "Example: export/ quizID/ CS2103T Finals\n\n";

    private final String quizId;

    /**
     * Creates a QuizExportCommand instance with the appropriate attributes.
     * @param quizId The identifier of the quiz.
     */
    public QuizExportCommand(String quizId) {
        this.quizId = quizId;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException, IOException {
        boolean isSuccess = model.exportQuiz(quizId);

        if(isSuccess) {
            return new CommandResult(generateSuccessMessage(quizId));
        } else {
            return new CommandResult(generateFailureMessage(quizId));
        }
    }

    /**
     * Generates a command execution success message.
     * @param message The relevant message from the model.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage(String message) {
        return "Successfully exported to "
                + message + ".html in your current directory.";
    }

    /**
     * Generates a command execution failure message.
     * @param message The relevant message from the model.
     * @return The String representation of a failure message.
     */
    private String generateFailureMessage(String message) {
        return "Failed to export because file "
                + message + ".html already exists.";
    }

}
