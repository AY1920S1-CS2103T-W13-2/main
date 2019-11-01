package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    // Address book
    public static final String MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX = "Please provide a question index.";
    public static final String MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX = "The question index provided is invalid.";
    public static final String MESSAGE_MISSING_QUESTION_OPTIONS = "Options A to D are necessary for mcq questions.";
    public static final String MESSAGE_MISSING_TEXT_SEARCH = "Please provide a text to search.";

    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid";

    public static final String MESSAGE_INVALID_NOTE_DISPLAYED_INDEX = "The note index provided is invalid";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note already exists in the notes record";

    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";

    public static final String MESSAGE_SAVE_STATS_FILE_ERROR = "Statistics Command Error. "
            + "Filename cannot be empty or have any special characters \\ / : * ? \" < > |";
    public static final String EXCEL_FILE_NOT_FOUND = "Excel file was not found. Please ensure file path is valid.";
    public static final String EXCEL_FILE_NOT_PARSED = "Error occurred retrieving file. Please try with another file";
    public static final String EXCEL_FILE_TYPE_ISSUE = "File path must be of type /'.xlsx/'. Please try again.";
    public static final String EXCEL_FILE_ILLEGAL_INPUT = "File has illegal input. Please refer to user guide.";
    public static final String EXCEL_FILE_ILLEGAL_FORMAT = "File has illegal format. PLease refer to user guide.";
    public static final String EXCEL_ILLEGAL_HEADER = "Cell A1 should be 'Students'";

}
