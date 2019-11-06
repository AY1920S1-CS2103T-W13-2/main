package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SCHEDULE_VIEW_MODE;
import static seedu.address.commons.util.EventUtil.BAD_DATE_FORMAT;
import static seedu.address.commons.util.EventUtil.DAILY_RECUR_RULE_STRING;
import static seedu.address.commons.util.EventUtil.INVALID_RECURRENCE_TYPE;
import static seedu.address.commons.util.EventUtil.NONE_RECUR_RULE_STRING;
import static seedu.address.commons.util.EventUtil.WEEKLY_RECUR_RULE_STRING;
import static seedu.address.commons.util.EventUtil.dateToLocalDateTimeFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventScheduleViewMode;
import seedu.address.model.event.RecurrenceType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return a student name representation
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseStudentName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        boolean valid = Name.isValidName(trimmedName);
        if (!valid) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String colorNumberString} into a {@code ArrayList<Categories>}.
     */
    public static ArrayList<Categories> parseColorNumber(String colorNumberString) throws ParseException {
        if (!validateColorNumberString(colorNumberString)) {
            throw new ParseException("invalid color string passed.");
        }
        String colorCategoryString = convertNumberToColorNumber(colorNumberString);
        Categories colorCategory = new Categories(colorCategoryString);
        ArrayList<Categories> colorCategoryList = new ArrayList<>();
        colorCategoryList.add(colorCategory);
        return colorCategoryList;
    }

    /**
     * Converts a string number to format that ICalendarAgenda accepts.
     * @param number String representation of number
     * @return String representation of colorNumber as required by ICalendarAgenda
     */
    private static String convertNumberToColorNumber(String number) throws NumberFormatException {
        return "group" + (Integer.parseInt(number) < 10 ? "0" : "") + number;
    }

    /**
     * Validates if a color number string is valid, must be a integer from 0 -23.
     * @param colorNumberString numberString to be checked
     * @return true if colorNumberString is valid
     * @throws NumberFormatException when colorNumberString cannot be cast to Integer,
     * representing invalid string format
     */
    private static boolean validateColorNumberString(String colorNumberString) {
        //validate number is in range
        try {
            Integer colorNumberInteger = Integer.parseInt(colorNumberString);
            boolean result = colorNumberInteger <= 23 && colorNumberInteger >= 0;
            return result;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Parses {@code String localDateTimeString} into a {@code LocalDateTime}.
     */
    public static LocalDateTime parseLocalDateTime(String localDateTimeString) throws ParseException {
        LocalDateTime result;
        try {
            result = LocalDateTime.parse(localDateTimeString);
        } catch (DateTimeParseException dtpEx) {
            throw new ParseException(BAD_DATE_FORMAT, dtpEx);
        }
        return result;
    }

    /**
     * Parses {@code String recurrenceTypeString} into a {@code RecurrenceRule}.
     */
    public static RecurrenceRule parseRecurrenceType(String recurrenceTypeString) throws ParseException {
        if (!validateRecurTypeString(recurrenceTypeString)) {
            throw new ParseException(INVALID_RECURRENCE_TYPE);
        }
        RecurrenceRule result;
        try {
            result = stringToRecurrenceRule(recurrenceTypeString);
        } catch (IllegalValueException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return result;
    }

    /**
     * Validates if recurTypeString is valid based on RecurrenceType enumeration
     * @param recurTypeString String to be evaluated
     * @return true if recurTypeString is valid
     */
    private static boolean validateRecurTypeString(String recurTypeString) {
        if (recurTypeString.equalsIgnoreCase(RecurrenceType.WEEKLY.name())) {
            return true;
        } else if (recurTypeString.equalsIgnoreCase(RecurrenceType.DAILY.name())) {
            return true;
        } else if (recurTypeString.equalsIgnoreCase(RecurrenceType.NONE.name())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Converts a recurrenceString to a RecurrenceRule object.
     * @param recurrenceString
     * @return returns a RecurrenceRule object which is used to configure VEVents
     * @throws IllegalValueException for invalid recurrenceString.
     */
    private static RecurrenceRule stringToRecurrenceRule(String recurrenceString) throws IllegalValueException {
        if (recurrenceString.equalsIgnoreCase("weekly")) {
            return RecurrenceRule.parse(WEEKLY_RECUR_RULE_STRING);
        } else if (recurrenceString.equalsIgnoreCase("daily")) {
            return RecurrenceRule.parse(DAILY_RECUR_RULE_STRING);
        } else if (recurrenceString.equalsIgnoreCase("none")) {
            return RecurrenceRule.parse(NONE_RECUR_RULE_STRING);
        } else {
            throw new IllegalValueException("recurrence string type is not valid. value passed: " + recurrenceString);
        }
    }

    /**
     * Convert viewMode to its Enum Type: EventScheduleViewMode
     * @param viewMode String representation of the viewMode desired
     * @return corresponding EventScheduleViewMode enumeration
     * @throws IllegalValueException if viewMode is not equilavent to any of the viewModes
     */
    public static EventScheduleViewMode parseEventScheduleViewMode(String viewMode) throws ParseException {
        if (viewMode.equalsIgnoreCase(EventScheduleViewMode.WEEKLY.name())) {
            return EventScheduleViewMode.WEEKLY;
        } else if (viewMode.equalsIgnoreCase(EventScheduleViewMode.DAILY.name())) {
            return EventScheduleViewMode.DAILY;
        } else {
            throw new ParseException(MESSAGE_INVALID_SCHEDULE_VIEW_MODE);
        }
    }

    /**
     * Convert localDateString into localDateTime with default hour, minute and second values.
     * @param localDateString String representation of the local date
     * @return LocalDateTime form of the local date time string parsed
     * @throws ParseException for invalid local date format.
     */
    public static LocalDateTime parseLocalDate(String localDateString) throws ParseException {
        try {
            LocalDateTime targetDateTime = dateToLocalDateTimeFormatter(localDateString);
            return targetDateTime;
        } catch (DateTimeParseException ex) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }
}
