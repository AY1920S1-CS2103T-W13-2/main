package seedu.address.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventRecord;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.model.statistics.StatisticsRecord;
import seedu.address.model.student.StudentRecord;

public class QuestionEditCommandTest {

    private Model model = new ModelManager(new AddressBook(), new StudentRecord(),
        getTypicalSavedQuestions(), new SavedQuizzes(), new NotesRecord(),  new EventRecord(), new StatisticsRecord(),
        new UserPrefs());

    @Test
    public void execute_editQuestionWithDifferentType_success() {
        Index index = Index.fromOneBased(1);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");

        HashMap<String, String> options = new HashMap<>();
        options.put("optionA", "1");
        options.put("optionB", "2");
        options.put("optionC", "3");
        options.put("optionD", "4");
        QuestionEditCommand editCommand = new QuestionEditCommand(index, fields, options);

        Question expectedQuestion = new McqQuestion(fields.get("question"), fields.get("answer"),
            options.get("optionA"), options.get("optionB"), options.get("optionC"),
            options.get("optionD"));
        String expectedMessage = "Edited question: " + expectedQuestion;

        assertCommandSuccess(editCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_editQuestionWithSameType_success() {
        Index index = Index.fromOneBased(1);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "open");
        QuestionEditCommand editCommand = new QuestionEditCommand(index, fields);

        Question expectedQuestion = new OpenEndedQuestion(fields.get("question"),
            fields.get("answer"));
        String expectedMessage = "Edited question: " + expectedQuestion;
        assertCommandSuccess(editCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidQuestionIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getAllQuestions().size() + 1);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");
        QuestionEditCommand editCommand = new QuestionEditCommand(outOfBoundIndex, fields);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX, ()
            -> editCommand.execute(model));
    }

    @Test
    public void equals() {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");
        QuestionEditCommand editCommand = new QuestionEditCommand(Index.fromOneBased(1), fields);

        // Same object
        assertTrue(editCommand.equals(editCommand));

        // Null
        assertFalse(editCommand.equals(null));

        // Different index
        QuestionEditCommand editCommandDiffIndex = new QuestionEditCommand(Index.fromOneBased(2),
            fields);
        assertFalse(editCommand.equals(editCommandDiffIndex));

        // Different question
        HashMap<String, String> fieldsDiffQuestion = new HashMap<>();
        fields.put("question", "Test Edit Question");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");
        QuestionEditCommand editCommandDiffQuestion = new QuestionEditCommand(Index.fromOneBased(2),
            fieldsDiffQuestion);
        assertFalse(editCommand.equals(editCommandDiffQuestion));

        // Different answer
        HashMap<String, String> fieldsDiffAnswer = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer Different");
        fields.put("type", "mcq");
        QuestionEditCommand editCommandDiffAnswer = new QuestionEditCommand(Index.fromOneBased(1),
            fieldsDiffAnswer);
        assertFalse(editCommand.equals(editCommandDiffAnswer));

        // Different type
        HashMap<String, String> fieldsDiffType = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "open");
        QuestionEditCommand editCommandDiffType = new QuestionEditCommand(Index.fromOneBased(1),
            fieldsDiffType);
        assertFalse(editCommand.equals(editCommandDiffType));
    }

}
