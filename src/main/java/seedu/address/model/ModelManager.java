package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteList;
import seedu.address.model.person.Person;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBank;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizBank;
import seedu.address.model.student.Student;
import seedu.address.model.group.Group;



/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final QuestionBank questionBank;
    private final QuizBank quizBank;
    private final ListOfGroups groupList;
    private final NoteList notes;
    private final UserPrefs userPrefs;
    private final StudentRecord studentRecord;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyStudentRecord studentRecord,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine(
            "Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.questionBank = new QuestionBank();
        this.quizBank = new QuizBank();
        this.groupList = new ListOfGroups();
        this.notes = new NoteList();
        this.userPrefs = new UserPrefs(userPrefs);
        this.studentRecord = new StudentRecord(studentRecord);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredStudents = new FilteredList<>(this.studentRecord.getStudentList());
    }

    public ModelManager() {
        this(new AddressBook(), new StudentRecord(), new UserPrefs());
    }

    //region PREFERENCES & SETTINGS

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //endregion

    //region AddressBook
    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //endregion

    //region FilteredPerson List Accessors
    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    //endregion

    //region Person
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }
    //endregion

    //region StudentRecord
    @Override
    public void setStudentRecordFilePath(Path studentRecordFilePath) {
        requireNonNull(studentRecordFilePath);
        userPrefs.setStudentRecordFilePath(studentRecordFilePath);
    }

    @Override
    public Path getStudentRecordFilePath() {
        return userPrefs.getStudentRecordFilePath();
    }

    @Override
    public void setStudentRecord(ReadOnlyStudentRecord studentRecord) {
        this.studentRecord.resetData(studentRecord);
    }

    @Override
    public ReadOnlyStudentRecord getStudentRecord() {
        return studentRecord;
    }
    //endregion

    //region FilteredStudent List Accessors
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return null;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }
    //endregion

    //region Students
    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return studentRecord.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        studentRecord.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        studentRecord.addStudent(student);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        studentRecord.setStudent(target, editedStudent);
    }
    //endregion

    //region Group
    /**
     * Creates a group manually.
     */
    public void createGroupManually(String groupId, ArrayList<Integer> studentNumbers){
        Group group = new Group(groupId);

        ArrayList<Student> students = new ArrayList<>();
        for (Integer i : studentNumbers) {
            students.add(filteredStudents.get(i+1));
        }

        for (Student s : students) {
            group.addStudent(s);
        }

        groupList.addGroup(group);
    };

    /**
     * Adds a student to a group.
     * {@code groupId} Must already exist in the list of groups.
     * {@code studentNumber} Must already exist in the list of students.
     * {@code groupIndexNumber} Must already exist in the quiz.
     */
    public boolean addStudentToGroup(String groupId, int studentNumber, int groupIndexNumber){
        int questionIndex = studentNumber - 1;
        Student student  = filteredStudents.get(questionIndex);

        int groupIndex = groupList.getGroupIndex(groupId);
        if (groupIndex != -1) {
            Group group = groupList.getGroup(groupIndex);
            return group.addStudent(groupIndexNumber, student);
        }
        return false;
    };

    /**
     * Removes a student from a group.
     */
    public void removeStudentFromGroup(String groupId, int groupIndexNumber){
        int groupIndex = groupList.getGroupIndex(groupId);
        if (groupIndex != -1) {
            Group group = groupList.getGroup(groupIndex);
            group.removeStudent(groupIndexNumber);
        }
    };

    /**
     * Returns a students from a group in list view.
     */
    public String getStudentsFromGroup(String groupId){
        String students = "";
        int groupIndex = groupList.getGroupIndex(groupId);
        if (groupIndex != -1) {
            Group group = groupList.getGroup(groupIndex);
            students = group.getStudentsFormatted();
        }
        return students;
    };

    //region Questions

    @Override
    public void addQuestion(Question question) {
        questionBank.addQuestion(question);
    }

    @Override
    public Question deleteQuestion(Index index) {
        return questionBank.deleteQuestion(index);
    }

    @Override
    public Question getQuestion(Index index) {
        return questionBank.getQuestion(index);
    }

    @Override
    public void setQuestion(Index index, Question question) {
        questionBank.setQuestion(index, question);
    }

    @Override
    public String getQuestionsSummary() {
        return questionBank.getQuestionsSummary();
    }

    //endregion

    //region Quizzes

    @Override
    public void createQuizManually(String quizId, ArrayList<Integer> questionNumbers) {
        Quiz quiz = new Quiz(quizId);

        ArrayList<Question> questions = new ArrayList<>();
        for (Integer i : questionNumbers) {
            questions.add(questionBank.getQuestion(Index.fromOneBased(i)));
        }

        for (Question q : questions) {
            quiz.addQuestion(q);
        }

        quizBank.addQuiz(quiz);
    }

    @Override
    public void createQuizAutomatically(String quizId, int numQuestions, String type) {
        Quiz quiz = new Quiz(quizId);

        ArrayList<Question> relevantQuestions = new ArrayList<>();
        switch (type) {
        case "mcq":
            relevantQuestions = questionBank.getMcqQuestions();
            break;
        case "open":
            relevantQuestions = questionBank.getOpenEndedQuestions();
            break;
        case "all":
            relevantQuestions = questionBank.getAllQuestions();
            break;
        default:
            break;
        }

        int listSize = relevantQuestions.size();

        if (listSize > numQuestions) {
            for (int i = 0; i < numQuestions; i++) {
                int randomQuestionIndex = getRandomQuestionIndex(listSize);
                Question randomQuestion = relevantQuestions.get(randomQuestionIndex);
                boolean isSuccess = quiz.addQuestion(randomQuestion);
                while (!isSuccess) {
                    randomQuestionIndex = getRandomQuestionIndex(listSize);
                    randomQuestion = relevantQuestions.get(randomQuestionIndex);
                    isSuccess = quiz.addQuestion(randomQuestion);
                }
            }
        } else {
            for (Question q : relevantQuestions) {
                quiz.addQuestion(q);
            }
        }

        quizBank.addQuiz(quiz);
    }

    @Override
    public boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber) {
        int questionIndex = questionNumber - 1;
        Question question = questionBank.getQuestion(Index.fromZeroBased(questionIndex));

        int quizIndex = quizBank.getQuizIndex(quizId);
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            return quiz.addQuestion(quizQuestionNumber, question);
        }
        return false;
    }

    @Override
    public void removeQuizQuestion(String quizId, int questionNumber) {
        int quizIndex = quizBank.getQuizIndex(quizId);
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            quiz.removeQuestion(questionNumber);
        }
    }

    @Override
    public String getQuestionsAndAnswers(String quizId) {
        String questions = "";
        String answers = "";
        int quizIndex = quizBank.getQuizIndex(quizId);
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            questions = quiz.getFormattedQuestions();
            answers = quiz.getFormattedAnswers();
        }
        return questions + answers;
    }

    private static int getRandomQuestionIndex(int listSize) {
        return (int) Math.floor(Math.random() * listSize);
    }

    //endregion

    //region Notes

    @Override
    public void addNote(Note note) {
        notes.addNote(note);
    }

    @Override
    public Note deleteNote(Index index) {
        return notes.deleteNote(index);
    }

    @Override
    public Note getNote(Index index) {
        return notes.getNote(index);
    }

    @Override
    public void setNote(Index index, Note question) {
        notes.setNote(index, question);
    }

    @Override
    public List<Note> getNotes() {
        return notes.getNotes();
    }

    @Override
    public String getNoteSummary() {
        return notes.getNoteSummary();
    }

    //endregion

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons);
    }

}
