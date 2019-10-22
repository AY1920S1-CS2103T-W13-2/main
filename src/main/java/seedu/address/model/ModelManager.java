package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.VElement;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
//import seedu.address.model.event.EventRecord;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.group.Group;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.note.Note;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.person.Person;
import seedu.address.model.question.Question;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.model.statistics.ReadOnlyStatisticsRecord;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.StatisticsRecord;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final ListOfGroups groupList;
    private final UserPrefs userPrefs;
    private final StudentRecord studentRecord;
    private final SavedQuestions savedQuestions;
    private final EventRecord eventRecord;
    private final SavedQuizzes savedQuizzes;
    private final NotesRecord notesRecord;
    private final StatisticsRecord statisticsRecord;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Note> filteredNotes;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyStudentRecord studentRecord,
                        ReadOnlyQuestions savedQuestions,
                        ReadOnlyQuizzes savedQuizzes,
                        ReadOnlyNotesRecord notesRecord,
                        ReadOnlyEvents readEvents,
                        ReadOnlyStatisticsRecord statisticsRecord,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, studentRecord, savedQuestions,
                savedQuizzes, notesRecord, statisticsRecord, userPrefs);

        logger.fine(
                "Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.groupList = new ListOfGroups();
        this.userPrefs = new UserPrefs(userPrefs);
        this.studentRecord = new StudentRecord(studentRecord);
        this.savedQuestions = new SavedQuestions(savedQuestions);
        this.eventRecord = new EventRecord(readEvents);
        this.savedQuizzes = new SavedQuizzes(savedQuizzes);
        this.notesRecord = new NotesRecord(notesRecord);
        this.statisticsRecord = new StatisticsRecord(statisticsRecord);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredStudents = new FilteredList<>(this.studentRecord.getStudentList());
        filteredNotes = new FilteredList<>(this.notesRecord.getNotesList());
    }

    public ModelManager() {
        this(new AddressBook(), new StudentRecord(), new SavedQuestions(), new SavedQuizzes(),
                new NotesRecord(), new EventRecord(), new StatisticsRecord(), new UserPrefs());
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
        return studentRecord.getStudentList();
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }
    //endregion

    //region Statistics
    @Override
    public ReadOnlyStatisticsRecord getStatisticsRecord() {
        return statisticsRecord;
    }

    @Override
    public ObservableList<Statistics> getProcessedStatistics() {
        return statisticsRecord.getProcessedStatistics();
    }

    @Override
    public void addStatistics(Statistics statistic) {
        statisticsRecord.setStatistics(statistic);
    }
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

    @Override
    public String getStudentSummary() {
        return studentRecord.getStudentSummary();
    }
    //endregion

    //region Group
    /**
     * Creates a group manually.
     */
    public void createGroupManually(String groupId, ArrayList<Integer> studentNumbers) {
        Group group = new Group(groupId);

        ArrayList<Student> students = new ArrayList<>();
        for (Integer i : studentNumbers) {
            students.add(filteredStudents.get(i - 1));
        }

        for (Student s : students) {
            group.addStudent(s);
        }

        groupList.addGroup(group);
    }

    /**
     * Adds a student to a group.
     * {@code groupId} Must already exist in the list of groups.
     * {@code studentNumber} Must already exist in the list of students.
     * {@code groupIndexNumber} Must already exist in the quiz.
     */
    public boolean addStudentToGroup(String groupId, int studentNumber, int groupIndexNumber) {
        int questionIndex = studentNumber - 1;
        Student student = filteredStudents.get(questionIndex);

        int groupIndex = groupList.getGroupIndex(groupId);
        if (groupIndex != -1) {
            Group group = groupList.getGroup(groupIndex);
            return group.addStudent(groupIndexNumber, student);
        }
        return false;
    }

    /**
     * Removes a student from a group.
     */
    public void removeStudentFromGroup(String groupId, int groupIndexNumber) {
        int groupIndex = groupList.getGroupIndex(groupId);
        if (groupIndex != -1) {
            Group group = groupList.getGroup(groupIndex);
            group.removeStudent(groupIndexNumber);
        }
    }

    /**
     * Returns a students from a group in list view.
     */
    public String getStudentsFromGroup(String groupId) {
        String students = "";
        int groupIndex = groupList.getGroupIndex(groupId);
        if (groupIndex != -1) {
            Group group = groupList.getGroup(groupIndex);
            students = group.getStudentsFormatted();
        }
        return students;
    }
    //endregion

    //region Questions
    @Override
    public void addQuestion(Question question) {
        savedQuestions.addQuestion(question);
    }

    @Override
    public Question deleteQuestion(Index index) {
        return savedQuestions.deleteQuestion(index);
    }

    @Override
    public Question getQuestion(Index index) {
        return savedQuestions.getQuestion(index);
    }

    @Override
    public void setQuestion(Index index, Question question) {
        savedQuestions.setQuestion(index, question);
    }

    @Override
    public String getQuestionsSummary() {
        return savedQuestions.getQuestionsSummary();
    }
    //endregion

    //region SavedQuestions
    @Override
    public void setSavedQuestionsFilePath(Path savedQuestionsFilePath) {
        requireNonNull(savedQuestionsFilePath);
        userPrefs.setSavedQuestionsFilePath(savedQuestionsFilePath);
    }

    @Override
    public Path getSavedQuestionsFilePath() {
        return userPrefs.getSavedQuestionsFilePath();
    }

    @Override
    public void setSavedQuestions(ReadOnlyQuestions savedQuestions) {
        this.savedQuestions.resetData(savedQuestions);
    }

    @Override
    public ReadOnlyQuestions getSavedQuestions() {
        return savedQuestions;
    }
    //endregion

    //region Quizzes

    @Override
    public void createQuizManually(String quizId, ArrayList<Integer> questionNumbers) {
        savedQuizzes.createQuizManually(quizId, questionNumbers, savedQuestions);
    }

    @Override
    public void createQuizAutomatically(String quizId, int numQuestions, String type) {
        savedQuizzes.createQuizAutomatically(quizId, numQuestions, type, savedQuestions);
    }

    @Override
    public boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber) {
        return savedQuizzes.addQuizQuestion(quizId, questionNumber, quizQuestionNumber, savedQuestions);
    }

    @Override
    public void removeQuizQuestion(String quizId, int questionNumber) {
        savedQuizzes.removeQuizQuestion(quizId, questionNumber);
    }

    @Override
    public String getQuestionsAndAnswers(String quizId) {
        return savedQuizzes.getQuestionsAndAnswers(quizId);
    }

    //endregion

    //region SavedQuizzes
    @Override
    public void setSavedQuizzesFilePath(Path savedQuizzesFilePath) {
        requireNonNull(savedQuizzesFilePath);
        userPrefs.setSavedQuizzesFilePath(savedQuizzesFilePath);
    }

    @Override
    public Path getSavedQuizzesFilePath() {
        return userPrefs.getSavedQuizzesFilePath();
    }

    @Override
    public void setSavedQuizzes(ReadOnlyQuizzes savedQuizzes) {
        this.savedQuizzes.resetData(savedQuizzes);
    }

    @Override
    public ReadOnlyQuizzes getSavedQuizzes() {
        return savedQuizzes;
    }
    //endregion

    //region NotesRecord
    @Override
    public Path getNotesRecordFilePath() {
        return userPrefs.getNotesRecordFilePath();
    }

    @Override
    public void setNotesRecordFilePath(Path notesRecordFilePath) {
        requireNonNull(notesRecordFilePath);
        userPrefs.setNotesRecordFilePath(notesRecordFilePath);
    }

    @Override
    public void setNotesRecord(ReadOnlyNotesRecord notesRecord) {
        this.notesRecord.resetData(notesRecord);
    }

    @Override
    public ReadOnlyNotesRecord getNotesRecord() {
        return notesRecord;
    }
    //endregion

    //region FilteredNote List Accessors
    /**
     * Returns an unmodifiable view of the list of {@code Note} backed by the internal list of notes record.
     */
    @Override
    public ObservableList<Note> getFilteredNotesList() {
        return filteredNotes;
    }

    @Override
    public void updateFilteredNotesList(Predicate<Note> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
    }
    //endregion

    //region Notes
    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notesRecord.hasNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        notesRecord.removeNote(note);
    }

    @Override
    public void addNote(Note note) {
        notesRecord.addNote(note);
        updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);
        notesRecord.setNote(target, editedNote);
    }
    //endregion

    //region EventRecord
    @Override
    public void setEventRecord(Path eventRecordFilePath) {
        requireNonNull(eventRecordFilePath);
        userPrefs.setEventRecordFilePath(eventRecordFilePath);
    }

    @Override
    public Path getEventRecordFilePath() {
        return userPrefs.getEventRecordFilePath();
    }

    @Override
    public void setEventRecord(ReadOnlyEvents events) {
        this.eventRecord.resetData(events);
    }

    @Override
    public ReadOnlyEvents getEventRecord() {
        return eventRecord;
    }

    //endregion

    //region Events
    @Override
    public boolean hasVEvent(VEvent vEvent) {
        requireNonNull(vEvent);
        return eventRecord.contains(vEvent);
    }

    @Override
    public void deleteVEvent(VEvent vEvent) {
        eventRecord.deleteVEvent(vEvent);
    }

    @Override
    public void addVEvent(VEvent vEvent) {
        eventRecord.addVEvent(vEvent);
    }

    @Override
    public void setVEvent(VEvent target, VEvent editedVEvent) {
        requireAllNonNull(target, editedVEvent);
        eventRecord.setVEvent(target, editedVEvent);
    }

    @Override
    public String getVEventSummary() {
        return eventRecord.getVEventSummary();
    }

    @Override
    public ObservableList<VEvent> getVEventList() {
        return eventRecord.getVEventList();
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
