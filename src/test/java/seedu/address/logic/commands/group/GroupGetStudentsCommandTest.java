package seedu.address.logic.commands.group;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.testutil.group.GroupBuilder;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.student.StudentBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.group.GroupCommand.*;
import static seedu.address.logic.commands.group.GroupCreateManuallyCommand.OUT_OF_BOUNDS;

public class GroupGetStudentsCommandTest {

    @Test
    public void equals() {
        String groupId = GroupBuilder.DEFAULT_GROUP_ID;
        String otherGroupId = "Other Group";
        GroupGetStudentsCommand getStudentsCommand = new GroupGetStudentsCommand(groupId);
        GroupGetStudentsCommand otherGetStudentsCommand = new GroupGetStudentsCommand(otherGroupId);
        // same object -> returns true
        assertTrue(getStudentsCommand.equals(getStudentsCommand));

        // same values -> returns true
        GroupGetStudentsCommand getStudentsCommandCopy = new GroupGetStudentsCommand(groupId);
        assertTrue(getStudentsCommand.equals(getStudentsCommandCopy));

        // different types -> returns false
        assertFalse(getStudentsCommand.equals(1));

        // null -> returns false
        assertFalse(getStudentsCommand.equals(null));

        // different group -> returns false
        assertFalse(getStudentsCommand.equals(otherGetStudentsCommand));
    }

    /**
     * Test for getting a group of students successfully.
     */
    @Test
    public void execute_getGroup_Success() throws Exception {
        GroupGetStudentsCommand groupGetStudentsCommand = new GroupGetStudentsCommand("Get");
        Student student = new StudentBuilder().withName(new Name("GetTest")).build();
        ModelStub modelStub = new GroupGetStudentsCommandTest.ModelStubWithGroupWithStudent("Get", student);
        CommandResult commandResult = groupGetStudentsCommand.execute(modelStub);
        assertEquals("Starting group view.", commandResult.getFeedbackToUser());
    }

    /**
     * Test for getting a group of students unsuccessfully, as group with matching group id is not present.
     */
    @Test
    public void execute_getGroupInvalidGroupId_throwsCommandException() throws Exception {
        GroupGetStudentsCommand groupGetStudentsCommand = new GroupGetStudentsCommand("GetNotFound");
        Student student = new StudentBuilder().withName(new Name("GetTestTwo")).build();
        ModelStub modelStub = new GroupGetStudentsCommandTest.ModelStubWithGroupWithStudent("GetTwo", student);
        assertThrows(CommandException.class, () -> groupGetStudentsCommand.execute(modelStub), String.format(GROUP_DOES_NOT_EXIST, "GetNotFound"));
    }

    /**
     * Test for getting a group of students unsuccessfully, as group ID is empty.
     */
    @Test
    public void execute_getGroupEmptyGroupId_throwsCommandException() throws Exception {
        GroupGetStudentsCommand groupGetStudentsCommand = new GroupGetStudentsCommand("");
        Student student = new StudentBuilder().withName(new Name("GetTestThree")).build();
        ModelStub modelStub = new GroupGetStudentsCommandTest.ModelStubWithGroupWithStudent("GetThree", student);
        assertThrows(CommandException.class, () -> groupGetStudentsCommand.execute(modelStub), GROUP_ID_LEFT_EMPTY);
    }



    /**
     * A Model stub that contains a single group with a single student.
     */
    private class ModelStubWithGroupWithStudent extends ModelStub {

        private final ListOfGroups listOfGroups;
        private final StudentRecord studentRecord;
        private final FilteredList<Student> filteredStudents;


        ModelStubWithGroupWithStudent(String groupId, Student student) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            this.listOfGroups = new ListOfGroups();
            group.addStudent(student);
            studentRecord = new StudentRecord();
            studentRecord.addStudent(student);
            listOfGroups.addGroup(group);
            this.filteredStudents = new FilteredList<>(this.studentRecord.getStudentList());
        }

        @Override
        public boolean checkGroupExists(String groupId) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            return listOfGroups.contains(group);
        }

        @Override
        public void createGroupManually(String groupId, ArrayList<Integer> studentNumbers) {
            Group group = new Group(groupId);

            ArrayList<Student> students = new ArrayList<>();
            for (Integer i : studentNumbers) {
                students.add(filteredStudents.get(i - 1));
            }

            for (Student s : students) {
                group.addStudent(s);
            }

            listOfGroups.addGroup(group);
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return studentRecord.getStudentList();
        }

    }
}
