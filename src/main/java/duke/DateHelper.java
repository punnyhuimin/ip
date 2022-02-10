package duke;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import duke.DateChecker.Dates;

/**
 * DateHelper is used to help convert Strings into the proper date time formats.
 * It also helps to check for the validity of the date inputted.
 * If the date input is invalid, (e.g. missing the day, short form months), it throws a DukeException.
 */
public class DateHelper {
    private Date formattedDatetime;
    private String dateFormat;
    private int daysAfter;
    private static final String INVALID_DATE = "Oops, please put a valid time format!\n"
            + "Let's try again ~(^.^)~\n"
            + "Type 'help' if you need to know how to use this command";

    /**
     * Constructs a DateHelper.
     *
     * @param time Time input is based on the datetime provided by the user.
     * @throws DukeException Thrown when there is an invalid datetime format.
     */
    public DateHelper(String time) throws DukeException {
        patternType(time);
        try {
            String pattern = "dd/MM/yyyy";
            DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(dateFormat);
            formattedDatetime = date;
        } catch (ParseException e) {
            try {
                String pattern = "dd MMMM yyyy";
                DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                simpleDateFormat.setLenient(false);
                Date date = simpleDateFormat.parse(dateFormat);
                formattedDatetime = date;
            } catch (ParseException err) {
                throw new DukeException(INVALID_DATE);
            }
        }
    }

    /**
     * Method meant to detect what type of date user inputs.
     * This method is meant to format inputs that are not valid date types, but are natural dates.
     *
     * @param flexInput This is the date input provided by the user.
     */
    private void patternType(String flexInput) {
        DateChecker dateCheck = new DateChecker(flexInput);
        Dates inputtedDay = dateCheck.getDate(); // This gives the enum date which is like "MONDAY"
        Calendar now = Calendar.getInstance();
        int weekday = now.get(Calendar.DAY_OF_WEEK);
        switch (inputtedDay) {
        case MONDAY:
            if (weekday != Calendar.MONDAY) {
                daysAfter = (Calendar.SATURDAY - weekday + 2) % 7;
                dateFormat = formatDate(now);
            }
            break;
        case TUESDAY:
            if (weekday != Calendar.TUESDAY) {
                daysAfter = (Calendar.SATURDAY - weekday + 3) % 7;
                dateFormat = formatDate(now);
            }
            break;
        case WEDNESDAY:
            if (weekday != Calendar.WEDNESDAY) {
                daysAfter = (Calendar.SATURDAY - weekday + 4) % 7;
                dateFormat = formatDate(now);
            }
            break;
        case THURSDAY:
            if (weekday != Calendar.THURSDAY) {
                daysAfter = (Calendar.SATURDAY - weekday + 5) % 7;
                dateFormat = formatDate(now);
            }
            break;
        case FRIDAY:
            if (weekday != Calendar.FRIDAY) {
                daysAfter = (Calendar.SATURDAY - weekday + 6) % 7;
                dateFormat = formatDate(now);
            }
            break;
        case SATURDAY:
            if (weekday != Calendar.SATURDAY) {
                daysAfter = (Calendar.SATURDAY - weekday) % 7;
                dateFormat = formatDate(now);
            }
            break;
        case SUNDAY:
            if (weekday != Calendar.SUNDAY) {
                daysAfter = (Calendar.SATURDAY - weekday + 1) % 7;
                dateFormat = formatDate(now);
            }
            break;
        case NODAYS:
            dateFormat = flexInput;
        }
    }

    /**
     * This formats the Date to String. Used in DateHelper only.
     *
     * @param now Calendar class object meant to format the date.
     * @return Date in String format.
     */
    private String formatDate(Calendar now) {
        now.add(Calendar.DAY_OF_YEAR, daysAfter);
        Date date = now.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        dateFormat.setLenient(false);
        return dateFormat.format(date);
    }

    /**
     * Returns the formatted date time based on the user's input.
     *
     * @return Returns a formatted date time (i.e. dd MMMM yyyy).
     */
    public String getDatetime() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        dateFormat.setLenient(false);
        assert formattedDatetime != null : "No time was given previously";
        String strDate = dateFormat.format(formattedDatetime);
        return strDate;
    }
}

