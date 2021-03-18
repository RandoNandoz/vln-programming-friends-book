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

    // Call getUserInfo, and add that to the list.
    Friend friend = getUserInfo();
    // Otherwise, do not create the object, as there is an invalid object.
    if (friend != null) {
      friendList.getItems().add(friend);
    }
  }

  // When the "Delete Friend" button is clicked.
  public void deleteFriendAction(MouseEvent mouseEvent) {
    // We get the user's selected friend.
    Friend selectedFriend = friendList.getSelectionModel().getSelectedItem();
    // If there is a valid selection, we alert the users to their requested deletion,
    if (selectedFriend != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirm Deletion");
      alert.setHeaderText("You are about to remove a friend.");
      alert.setContentText("Are you sure you would like to remove " + selectedFriend.getName() + "?");
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
    // We get the user's selected field.
    Friend selectedFriend = friendList.getSelectionModel().getSelectedItem();
    // If there actually is a selection, then we query all the values, and display it on all the fields.
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

  // When the best friend bit on the closeness dropdown is clicked.
  public void bestFriendItemClicked(ActionEvent actionEvent) {
    closenessDropdown.setText(bestFriendItem.getText());
  }

  // Commit to the edits made when "view friend details" made.
  public void editFriendAction(MouseEvent mouseEvent) {
    // Get the selected friend's index.
    int selectedFriendIndex = friendList.getSelectionModel().getSelectedIndex();
    // Now, we query the fields again for any changes.
    Friend friend = getUserInfo();
    // If it's valid (which it should be), set that index to the new friend we just queried. We basically swap.
    if (friend != null) {
      friendList.getItems().set(selectedFriendIndex, friend);
    }
    resetFields();
  }

  // Function to pop an alert.
  private void popInvalidOptionAlert(String msg) {
    // Instantiate the alert object, then set the contents of the alert, and then show it.
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Invalid Option");
    alert.setContentText(msg);
    alert.show();
  }

  // Reset and enable all the fields.
  private void resetFields() {
    // Clear and enable all the fields.
    nameField.setText("");
    nameField.setDisable(false);
    ageField.setText("");
    ageField.setDisable(false);
    genderField.setText("");
    genderField.setDisable(false);
    birthdateCalendarPicker.setValue(null);
    birthdateCalendarPicker.setDisable(false);
    closenessDropdown.setText("Closeness");
    closenessDropdown.setDisable(false);
    notesField.setText("");
    notesField.setDisable(false);
  }

  // Get the gender from the params and the hashsets provided.
  private String getGender(TextField textField, HashSet<String> wordsForFemale, HashSet<String> wordsForMale) {

    /*
     * One may ask: Why HashSet?
     * Now, isn't that a great question. See, with an ArrayList, the ArrayList.contains(Object) method is in O(n), while HashSet.contains(Object) is O(1).
     * The lookup speed is constant, making it better. However, it doesn't really matter for like 4 items. Oh well.
     * */

    // This function gets the gender from the TextField which is provided to it.
    String returnVal;
    // If the TextField has text which is contained in the wordsForFemale hashset, then assign female.
    if (wordsForFemale.contains(textField.getText().toLowerCase())) {
      returnVal = Friend.GENDER_FEMALE;
      // If the TextField has text which is in the wordsForMale hashset, then assign male.
    } else if (wordsForMale.contains(textField.getText().toLowerCase())) {
      returnVal = Friend.GENDER_MALE;
      // If it's empty, we throw an exception which we handle.
    } else if (textField.getText().equals("")) {
      throw new RuntimeException();
      // Alas, if it's neither female, male, nor empty, we assume that they do not identify with either male or female,
      // and just capitalize the first letter.
    } else {
      returnVal = textField.getText().substring(0, 1).toUpperCase() + textField.getText().substring(1).toLowerCase();
    }
    return returnVal;
  }

  // Get the closeness from the dropdown menu.
  private int getCloseness(MenuButton menuButton) {
    // Switch statement that we return, maps the text selected from the menu button to the constant values in the friend class.
    // If the user doesn't choose, throw an exception which we can handle later.
    return switch (menuButton.getText()) {
      case "Acquaintance" -> Friend.CLOSENESS_ACQUAINTANCE;
      case "Friend" -> Friend.CLOSENESS_FRIEND;
      case "Best Friend" -> Friend.CLOSENESS_BEST_FRIEND;
      case "Closeness" -> throw new RuntimeException();
      default -> Friend.CLOSENESS_INVALID;
    };
  }

  // Big boi method which kinda just gets all the data.
  private Friend getUserInfo() {
    // Set the name variable with the text in the name field.
    String name = nameField.getText();

    // Then we set the age, if the age is invalid, tell the user.
    int age = -1;
    try {
      // Call parseInt() on the ageField, to get the age. If it's invalid, we catch the error, and tell the user.
      age = Integer.parseInt(ageField.getText());
    } catch (NumberFormatException e) {
      popInvalidOptionAlert("Invalid entry for field age, please try again.");
    }

    // Query for gender.
    String gender = null;
    try {
      gender = getGender(genderField, wordsForFemale, wordsForMale);
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
      closeness = getCloseness(closenessDropdown);
    } catch (RuntimeException e) {
      popInvalidOptionAlert("You did not select a proper value for closeness, please try again.");
    }

    // Set notes to the text in the notes field.
    String notes = notesField.getText();

    // Check if there are any invalid values, if so, return null, otherwise,
    Friend friend = null;
    if (name != null && age != -1 && gender != null && birthDate != null && closeness != Friend.CLOSENESS_INVALID) {
      friend = new Friend(name, age, gender, birthDate, closeness, notes);
      resetFields();
    }
    return friend;
  }
}
