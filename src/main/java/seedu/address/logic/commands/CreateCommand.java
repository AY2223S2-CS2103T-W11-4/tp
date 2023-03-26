package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.files.FilesManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;



/**
 * The type Create command.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generate Number x days of MC for the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) and number\n"
            + "Example: " + COMMAND_WORD + " 1 " + "1";

    public static final String MESSAGE_CREATE_MC_SUCCESS = "Generated Person: %1$s";

    private final Index targetIndex;
    private final int days;

    /**
     * @param targetIndex
     * @param days
     */
    public CreateCommand(Index targetIndex, int days) {
        this.targetIndex = targetIndex;
        this.days = days;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person persontoGenerate = lastShownList.get(targetIndex.getZeroBased());
        FilesManager filesManager = new FilesManager(persontoGenerate);
        filesManager.generateMc("Dr Van", "Very SIck", days);
        //default values for days and doctor parser will separate out information
        //changes still needed for FileManager to fully Integrate with UI
        return new CommandResult(String.format(MESSAGE_CREATE_MC_SUCCESS, persontoGenerate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommand // instanceof handles nulls
                && targetIndex.equals(((CreateCommand) other).targetIndex)); // state check
    }
}
