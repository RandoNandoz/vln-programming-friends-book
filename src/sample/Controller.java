package sample;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Controller {
  public TextField nameField;
  public TextField ageField;
  public TextField genderField;
  public DatePicker birthdateCalendarPicker;
  public MenuButton closenessDropdown;
  public TextArea notesField;
  public ListView<Friend> friendList;
  public MenuItem acquaintanceItem;
  public MenuItem friendItem;
  public MenuItem bestFriendItem;

  // Hashset for the words for female and male.
  private final HashSet<String> wordsForFemale = new HashSet<>(Arrays.asList("f", "female", "woman", "girl"));
  private final HashSet<String> wordsForMale = new HashSet<>(Arrays.asList("m", "male", "man", "boy"));

  // When the "Create Friend" button is clicked.
  public void createFriendAction(MouseEvent mouseEvent) {
    String name = nameField.getText();
    int age = Integer.parseInt(ageField.getText());

    /*
     * My way of getting the gender from the TextField. Using the hashsets containing the words for female and male, I check whether the text of the TextField
     * is in the hashset using HashSet.contains(<object>), then assigning it to the gender variable. This is superior to the ArrayList.contains(<object>), as
     * the time complexity of HashSet.contains() is average O(1), while for the ArrayList, it's O(n).
     *  */
    String gender;
    if (wordsForFemale.contains(genderField.getText().toLowerCase())) {
      gender = Friend.GENDER_FEMALE;
    } else if (wordsForMale.contains(genderField.getText().toLowerCase())) {
      gender = Friend.GENDER_MALE;
    } else {
      gender = genderField.getText().substring(0, 1).toUpperCase() + genderField.getText().substring(1).toLowerCase();
    }

    LocalDate birthDate = birthdateCalendarPicker.getValue();

    // Closeness switch. Looking at the acquaintanceItemClicked, friendItemClicked,
    int closeness;
    try {
      closeness = switch (closenessDropdown.getText()) {
        case "Acquaintance" -> Friend.CLOSENESS_ACQUAINTANCE;
        case "Friend" -> Friend.CLOSENESS_FRIEND;
        case "Best Friend" -> Friend.CLOSENESS_BEST_FRIEND;
        case "Closeness" -> throw new RuntimeException("Please select a closeness value.");
        default -> Friend.CLOSENESS_INVALID;
      };
    } finally {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText("Invalid Option");
      alert.setContentText("You did not select an option for the closeness dropdown, please try again.");
      alert.show();
    }
    String notes = notesField.getText();

    Friend friend = new Friend(name, age, gender, birthDate, closeness, notes);
    friendList.getItems().add(friend);
  }

//  public int getCloseness() {
//    int returnValue;
//    try {
//      returnValue = switch (closenessDropdown.getText()) {
//        case "Acquaintance" -> Friend.CLOSENESS_ACQUAINTANCE;
//        case "Friend" -> Friend.CLOSENESS_FRIEND;
//        case "Best Friend" -> Friend.CLOSENESS_BEST_FRIEND;
//        case "Closeness" -> throw new RuntimeException("Please select a closeness value.");
//        default -> Friend.CLOSENESS_INVALID;
//      };
//    } finally {
//      Alert alert = new Alert(Alert.AlertType.ERROR);
//      alert.show();
//    }
//    return returnValue;
//  }

  // When the "Delete Friend" button is clicked.
  public void deleteFriendAction(MouseEvent mouseEvent) {

  }

  // When the "View Friend Details" button is clicked.
  public void viewFriendDetailsAction(MouseEvent mouseEvent) {

  }

  // When the acquaintance bit on the closeness dropdown is clicked.
  public void acquaintanceItemClicked(ActionEvent actionEvent) {
    closenessDropdown.setText(acquaintanceItem.getText());
  }

  // When the friend bit on the closeness dropdown is clicked.
  public void friendItemClicked(ActionEvent actionEvent) {
    closenessDropdown.setText(friendItem.getText());
  }

  public void bestFriendItemClicked(ActionEvent actionEvent) {
    closenessDropdown.setText(bestFriendItem.getText());
  }
}
