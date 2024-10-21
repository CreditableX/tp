package seedu.address.logic.commands.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBuyerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUYER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUYER;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
import seedu.address.model.BuyerList;
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.buyer.Buyer;
import seedu.address.testutil.buyer.BuyerBuilder;
import seedu.address.testutil.buyer.EditBuyerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Buyer editedBuyer = new BuyerBuilder().build();
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder(editedBuyer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUYER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));
        expectedModel.setBuyer(model.getFilteredBuyerList().get(0), editedBuyer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBuyer = Index.fromOneBased(model.getFilteredBuyerList().size());
        Buyer lastBuyer = model.getFilteredBuyerList().get(indexLastBuyer.getZeroBased());

        BuyerBuilder buyerInList = new BuyerBuilder(lastBuyer);
        Buyer editedBuyer = buyerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastBuyer, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));
        expectedModel.setBuyer(lastBuyer, editedBuyer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUYER, new EditBuyerDescriptor());
        Buyer editedBuyer = model.getFilteredBuyerList().get(INDEX_FIRST_BUYER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBuyerAtIndex(model, INDEX_FIRST_BUYER);

        Buyer buyerInFilteredList = model.getFilteredBuyerList().get(INDEX_FIRST_BUYER.getZeroBased());
        Buyer editedBuyer = new BuyerBuilder(buyerInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUYER,
                new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));
        expectedModel.setBuyer(model.getFilteredBuyerList().get(0), editedBuyer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBuyerUnfilteredList_failure() {
        Buyer firstBuyer = model.getFilteredBuyerList().get(INDEX_FIRST_BUYER.getZeroBased());
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder(firstBuyer).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_BUYER, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BUYER);
    }

    @Test
    public void execute_duplicateBuyerFilteredList_failure() {
        showBuyerAtIndex(model, INDEX_FIRST_BUYER);

        // edit buyer in filtered list into a duplicate in buyer list
        Buyer buyerInList = model.getBuyerList().getBuyerList().get(INDEX_SECOND_BUYER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUYER,
                new EditBuyerDescriptorBuilder(buyerInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BUYER);
    }

    @Test
    public void execute_invalidBuyerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBuyerList().size() + 1);
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BUYER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of buyer list
     */
    @Test
    public void execute_invalidBuyerIndexFilteredList_failure() {
        showBuyerAtIndex(model, INDEX_FIRST_BUYER);
        Index outOfBoundIndex = INDEX_SECOND_BUYER;
        // ensures that outOfBoundIndex is still in bounds of buyer list list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBuyerList().getBuyerList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BUYER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_BUYER, DESC_AMY);

        // same values -> returns true
        EditBuyerDescriptor copyDescriptor = new EditBuyerDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_BUYER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_BUYER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_BUYER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditBuyerDescriptor editBuyerDescriptor = new EditBuyerDescriptor();
        EditCommand editCommand = new EditCommand(index, editBuyerDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editBuyerDescriptor="
                + editBuyerDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}