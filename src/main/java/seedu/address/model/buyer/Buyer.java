package seedu.address.model.buyer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Buyer in the buyer list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Buyer {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final BuyerType buyerType;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Buyer(Name name, Phone phone, Email email, Address address, BuyerType buyerType, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags, buyerType);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.buyerType = buyerType;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public BuyerType getBuyerType() {
        return buyerType;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both buyers have the same name.
     * This defines a weaker notion of equality between two buyers.
     */
    public boolean isSameBuyer(Buyer otherBuyer) {
        if (otherBuyer == this) {
            return true;
        }

        return otherBuyer != null
                && otherBuyer.getName().equals(getName());
    }

    /**
     * Returns true if both buyers have the same identity and data fields.
     * This defines a stronger notion of equality between two buyers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Buyer)) {
            return false;
        }

        Buyer otherBuyer = (Buyer) other;
        return name.equals(otherBuyer.name)
                && phone.equals(otherBuyer.phone)
                && email.equals(otherBuyer.email)
                && address.equals(otherBuyer.address)
                && tags.equals(otherBuyer.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("type", buyerType)
                .add("tags", tags)
                .toString();
    }

}
