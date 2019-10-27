package seedu.address.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventRecord;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.model.statistics.StatisticsRecord;
import seedu.address.model.student.StudentRecord;

public class QuestionDeleteCommandTest {

    private Model model = new ModelManager(new AddressBook(), new StudentRecord(),
        getTypicalSavedQuestions(), new SavedQuizzes(), new NotesRecord(), new EventRecord(),
        new StatisticsRecord(),
        new UserPrefs());

    @Test
    public void execute_deleteQuestion_success() {
        Index index = Index.fromOneBased(1);
        QuestionDeleteCommand deleteCommand = new QuestionDeleteCommand(index);

        Question expectedQuestion = model.getQuestion(index);
        String expectedMessage = "Deleted question: " + expectedQuestion;
        assertCommandSuccess(deleteCommand, model, expectedMessage, model,
            CommandResultType.SHOW_QUESTION);
    }

    @Test
    public void execute_invalidQuestionIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getAllQuestions().size() + 1);
        QuestionDeleteCommand deleteCommand = new QuestionDeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX, ()
            -> deleteCommand.execute(model));
    }

    @Test
    public void equals() {
        QuestionDeleteCommand deleteCommand = new QuestionDeleteCommand(Index.fromOneBased(1));

        // Same object
        assertTrue(deleteCommand.equals(deleteCommand));

        // Null
        assertFalse(deleteCommand.equals(null));

        // Different index
        QuestionDeleteCommand deleteCommandDiffIndex = new QuestionDeleteCommand(
            Index.fromOneBased(2));
        assertFalse(deleteCommand.equals(deleteCommandDiffIndex));
    }

}
