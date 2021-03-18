package sample;

import java.time.LocalDate;

public class Friend {
  // Attributes for the friend.
  private String name;
  private int age;
  private String gender;
  private final LocalDate birthDate;
  private int closeness;
  private String notes;

  // Potential options for gender.
  public static final String GENDER_FEMALE = "Female";
  public static final String GENDER_MALE = "Male";

  // Potential options for closeness.
  public static final int CLOSENESS_ACQUAINTANCE = 0;
  public static final int CLOSENESS_FRIEND = 1;
  public static final int CLOSENESS_BEST_FRIEND = 2;
  // Invalid case for an invalid value.
  public static final int CLOSENESS_INVALID = -1;

  // Constructor for the Friend class.
  public Friend(String name, int age, String gender, LocalDate birthDate, int closeness, String notes) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.birthDate = birthDate;
    // If the closeness is not any of the valid cases for closeness, assign it the invalid case.
    if (closeness != CLOSENESS_ACQUAINTANCE && closeness != CLOSENESS_FRIEND && closeness != CLOSENESS_BEST_FRIEND) {
      this.closeness = CLOSENESS_INVALID;
    } else {
      this.closeness = closeness;
    }
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "Name: " + getName() + ", Age: " + getAge() + ", Gender: " + getGender() + ", Birthdate: " + getBirthDate() + ", Closeness: " + getCloseness();
  }

  //<editor-fold desc="Getters and setters">
  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public String getCloseness() {
    return switch (closeness) {
      case CLOSENESS_ACQUAINTANCE -> "Acquaintance";
      case CLOSENESS_FRIEND -> "Friends";
      case CLOSENESS_BEST_FRIEND -> "Best Friend";
      case CLOSENESS_INVALID -> "INVALID!!! SOMETHING HAS GONE WRONG!!!";
      default -> throw new IllegalStateException("Unexpected value: " + closeness);
    };
  }

  public int getRawClosenessValue() {
    return closeness;
  }

  public String getNotes() {
    return notes;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setCloseness(int closeness) {
    this.closeness = closeness;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
  //</editor-fold>
}
