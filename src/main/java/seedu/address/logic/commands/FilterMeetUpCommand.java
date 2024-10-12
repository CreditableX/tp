package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUpContainsDate;

/**
 * Finds and lists all meetings in address book whose date is the same as the given date.
 */
public class FilterMeetUpCommand extends Command {

    public static final String COMMAND_WORD = "filterMeetup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meetings whose date matches "
            + " the given date and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD yyyy-mm-dd\n"
            + "Example: " + COMMAND_WORD + "2022-12-12";

    private final MeetUpContainsDate predicate;

    public FilterMeetUpCommand(MeetUpContainsDate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        // TODO
        return null;
        // requireNonNull(model);
        // model.updateFilteredPersonList(predicate);
        // return new CommandResult(
        //         String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

}