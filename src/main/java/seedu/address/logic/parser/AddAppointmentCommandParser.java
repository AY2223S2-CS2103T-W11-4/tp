package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.SearchAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand>{

    public static final String ENDTIME_BEFORE_STATRTIME = "endTime can not be before startTime";
    public static final String DIFFERENT_START_AND_END_DATE = "startTime and endTime should be at the same date";

    // command: makeApp {index} /from {time} /to {time}
    public AddAppointmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        // a string with: index,startTime|endTime
        String parsedTime = ParserUtil.parseAddAppointmentCommand(trimmedArgs);
        int idxComma = parsedTime.indexOf(',');
        int idxSlash = parsedTime.indexOf('|');
        String indexStr = parsedTime.substring(0, idxComma);
        String startTimeStr = parsedTime.substring(idxComma + 1, idxSlash).trim();
        String endTimeStr = parsedTime.substring(idxSlash + 1).trim();
        Index index = ParserUtil.parseIndex(indexStr);
        LocalDateTime startTime = ParserUtil.parseTime(startTimeStr);
        LocalDateTime endTime = ParserUtil.parseTime(endTimeStr);
        if (endTime.isBefore(startTime)) {
            throw new ParseException(ENDTIME_BEFORE_STATRTIME);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (!startTime.format(formatter).equals(endTime.format(formatter))) {
            throw new ParseException(DIFFERENT_START_AND_END_DATE);
        }
        return new AddAppointmentCommand(index, new Appointment(startTime, endTime));
    }
}
