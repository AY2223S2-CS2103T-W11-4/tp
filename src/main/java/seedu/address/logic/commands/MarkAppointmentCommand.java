package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class MarkAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "markApp";
    public static final String MESSAGE_SUCCESS = "Appointment successfully marked";
    public static final String NO_APPOINTMENT = "This patient does not have appointment yet";

    private final Index index;

    public MarkAppointmentCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, null);

        if (!personToEdit.hasAppointment()) {
            throw new CommandException(NO_APPOINTMENT);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);

    }

    private static Person createEditedPerson(Person personToEdit, Appointment appointmentToAdd) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        Age updatedAge = personToEdit.getAge();
        LocalDateTime updatedTime = personToEdit.getTime();
        MedicalCondition updatedMedicalCondition = personToEdit.getMedicalCondition();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedAge,
                updatedTags, updatedTime, updatedMedicalCondition, appointmentToAdd);
    }
}
