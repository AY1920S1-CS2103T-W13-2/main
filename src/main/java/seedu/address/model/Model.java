package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.question.Question;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    //region PREFERENCES & SETTINGS
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //endregion

    //region AddressBook
    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();
    //endregion

    //region Person
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //endregion

    //region StudentRecord
    Path getStudentRecordFilePath();

    void setStudentRecordFilePath(Path addressBookFilePath);

    void setStudentRecord(ReadOnlyStudentRecord studentRecord);

    ReadOnlyStudentRecord getStudentRecord();

    //endregion

    //region Students
    boolean hasStudent(Student student);
    void deleteStudent(Student target);
    void addStudent(Student student);
    void setStudent(Student target, Student editedStudent);
    ObservableList<Student> getFilteredStudentList();
    void updateFilteredStudentList(Predicate<Student> predicate);

    //endregion

    //region Group
    /**
     * Creates a group manually.
     */
    void createGroupManually(String groupId, ArrayList<Integer> studentNumbers);

    /**
     * Adds a student to a group.
     * {@code groupId} Must already exist in the list of groups.
     * {@code studentNumber} Must already exist in the list of students.
     * {@code groupIndexNumber} Must already exist in the quiz.
     */
    boolean addStudentToGroup(String groupId, int studentNumber, int groupIndexNumber);

    /**
     * Removes a student from a group.
     */
    void removeStudentFromGroup(String groupId, int groupIndexNumber);

    /**
     * Returns a students from a group in list view.
     */
    String getStudentsFromGroup(String groupId);

    //region Questions
    /**
     * Adds the given question.
     * {@code question} must not exist in the question list.
     */
    void addQuestion(Question question);

    /**
     * Returns the question that has been deleted based on the index.
     */
    Question deleteQuestion(Index index);

    /**
     * Returns the question based on its Index.
     */
    Question getQuestion(Index index);

    /**
     * Replaces the question at the specified index.
     */
    void setQuestion(Index index, Question question);

    /**
     * Returns the questions summary.
     *
     * @return Summary of questions list.
     */
    String getQuestionsSummary();

    //endregion

    //region Quizzes
    /**
     * Creates a quiz manually.
     */
    void createQuizManually(String quizId, ArrayList<Integer> questionNumbers);

    /**
     * Creates a quiz automatically.
     */
    void createQuizAutomatically(String quizId, int numQuestions, String type);

    /**
     * Adds a question to a quiz.
     * {@code quizId} Must already exist in the quiz bank.
     * {@code questionNumber} Must already exist in the question bank.
     * {@code quizQuestionNumber} Must already exist in the quiz.
     */
    boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber);

    /**
     * Removes a question from a quiz.
     */
    void removeQuizQuestion(String quizId, int questionNumber);

    /**
     * Returns a quiz's questions and answers, for testing purposes.
     */
    String getQuestionsAndAnswers(String quizId);

    //endregion

    //region Notes
    /**
     * Adds the given note.
     * {@code note} must not exist in the note list.
     */
    void addNote(Note note);

    /**
     * Returns the note that has been deleted based on the index.
     */
    Note deleteNote(Index index);

    /**
     * Returns the note based on its Index.
     */
    Note getNote(Index index);

    /**
     * Replaces the note at the specified index.
     */
    void setNote(Index index, Note question);

    /**
     * Returns the notes summary.
     *
     * @return Summary of notes list.
     */
    String getNoteSummary();

    List<Note> getNotes();

    //endregion
}
