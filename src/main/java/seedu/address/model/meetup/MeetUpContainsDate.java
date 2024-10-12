package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests that a {@code Meetup}'s {@code Date} range contains the given date.
 */
public class MeetUpContainsDate implements Predicate<MeetUp> {
    private final Date date;

    public MeetUpContainsDate(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public boolean test(MeetUp meetUp) {
        return !meetUp.getFrom().before(this.date) && !meetUp.getTo().after(this.date);
    }
}