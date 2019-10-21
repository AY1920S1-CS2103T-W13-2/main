package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * Slideshow window will open for the user.
     */
    private final boolean showSlideshow;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The application should update the scheduler view
     */
    private final boolean scheduleChange;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showSlideshow,
        boolean exit, boolean scheduleChange) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showSlideshow = showSlideshow;
        this.exit = exit;
        this.scheduleChange = scheduleChange;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, and other
     * fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowSlideshow() {
        return showSlideshow;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isScheduleChange() {
        return scheduleChange;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
            && showHelp == otherCommandResult.showHelp
            && showSlideshow == otherCommandResult.showSlideshow
            && exit == otherCommandResult.exit
            && scheduleChange == otherCommandResult.scheduleChange;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showSlideshow, exit, scheduleChange);
    }

}
