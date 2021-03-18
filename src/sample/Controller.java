package sample;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Controller {
  // Hashset for the words for female and male.
  private final HashSet<String> wordsForFemale = new HashSet<>(Arrays.asList("f", "female", "woman", "girl"));
  private final HashSet<String> wordsForMale = new HashSet<>(Arrays.asList("m", "male", "man", "boy"));
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

  // When the "Create Friend" button is clicked.
  public void createFriendAction(MouseEvent mouseEvent) {
    // Set the name variable with the text in the name field.
    String name = nameField.getText();

    // Then we set the age, if the age is invalid, tell the user.
    int age = -1;
    try {
      age = Integer.parseInt(ageField.getText());
    } catch (NumberFormatException e) {
      popInvalidOptionAlert("Invalid entry for field age, please try again.");
    }

    /*
     * My way of getting the gender from the TextField. Using the hashsets containing the words for female and male, I check whether the text of the TextField
     * is in the hashset using HashSet.contains(<object>), then assigning it to the gender variable. This is superior to the ArrayList.contains(<object>), as
     * the time complexity of HashSet.contains() is average O(1), while for the ArrayList, it's O(n).
     *
     * If the field is empty, alert the user.
     *  */
    String gender = null;
    try {
      if (wordsForFemale.contains(genderField.getText().toLowerCase())) {
        gender = Friend.GENDER_FEMALE;
      } else if (wordsForMale.contains(genderField.getText().toLowerCase())) {
        gender = Friend.GENDER_MALE;
      } else if (genderField.getText().equals("")) {
        throw new RuntimeException();
      } else {
        gender = genderField.getText().substring(0, 1).toUpperCase() + genderField.getText().substring(1).toLowerCase();
      }
    } catch (RuntimeException e) {
      popInvalidOptionAlert("Gender field is empty, please input valid value.");
    }

    // Set the birthdate.
    LocalDate birthDate;
    birthDate = birthdateCalendarPicker.getValue();
    if (birthDate == null) {
      popInvalidOptionAlert("Invalid option for date, please enter valid date.");
    }

    // Closeness switch. We turn the text from the dropdown into numerical representations. In this case, if the doesn't choose, then it'll alert the user to an error.
    int closeness = Friend.CLOSENESS_INVALID;
    try {
      closeness = switch (closenessDropdown.getText()) {
        case "Acquaintance" -> Friend.CLOSENESS_ACQUAINTANCE;
        case "Friend" -> Friend.CLOSENESS_FRIEND;
        case "Best Friend" -> Friend.CLOSENESS_BEST_FRIEND;
        case "Closeness" -> throw new RuntimeException();
        default -> Friend.CLOSENESS_INVALID;
      };
    } catch (RuntimeException e) {
      popInvalidOptionAlert("You did not select a proper value for closeness, please try again.");
    }

    // Set notes to the text in the notes field.
    String notes = notesField.getText();

    // Now, we start with friend at null, and we check for valid values for all the variables, extracted from the fields. If they are all valid, we create a new
    // friend object, add it to the list, and then we clear all the fields.
    Friend friend = null;
    if (name != null && age != -1 && gender != null && birthDate != null && closeness != Friend.CLOSENESS_INVALID) {
      friend = new Friend(name, age, gender, birthDate, closeness, notes);
      nameField.setText("");
      ageField.setText("");
      genderField.setText("");
      birthdateCalendarPicker.setValue(null);
      closenessDropdown.setText("Closeness");
      notesField.setText("");
    }
    // Otherwise, do not create the object, as there is an invalid object.
    if (friend != null) {
      friendList.getItems().add(friend);
    }
  }

  // When the "Delete Friend" button is clicked.

  public void deleteFriendAction(MouseEvent mouseEvent) {
    Friend selectedFriend = friendList.getSelectionModel().getSelectedItem();
    if (selectedFriend != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirm Deletion");
      alert.setHeaderText("You are about to remove a friend.");
      alert.setContentText("Are you sure you would like to remove " + selectedFriend + "?");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == ButtonType.OK) {
          friendList.getItems().remove(selectedFriend);
        }
      }
    } else {
      popInvalidOptionAlert("You did not select a friend to delete, please try again.");
    }
  }
  // When the "View Friend Details" button is clicked.

  public void viewFriendDetailsAction(MouseEvent mouseEvent) {
    Friend selectedFriend = friendList.getSelectionModel().getSelectedItem();
    if (selectedFriend != null) {
      nameField.setText(selectedFriend.getName());
      ageField.setText(String.valueOf(selectedFriend.getAge()));
      genderField.setText(selectedFriend.getGender());
      birthdateCalendarPicker.setValue(selectedFriend.getBirthDate());
      birthdateCalendarPicker.setDisable(true);
      closenessDropdown.setText(selectedFriend.getCloseness());
      notesField.setText(selectedFriend.getNotes());
    }
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

  // Function to pop an alert.
  public void popInvalidOptionAlert(String msg) {
    // Instantiate the alert object, then set the contents of the alert, and then show it.
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Invalid Option");
    alert.setContentText(msg);
    alert.show();
  }

  public void editFriendAction(MouseEvent mouseEvent) {
    Friend selectedFriend = friendList.getSelectionModel().getSelectedItem();
  }
}
