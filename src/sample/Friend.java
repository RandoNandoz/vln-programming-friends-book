package sample;

import java.util.Date;

public class Friend {
  // Attributes for the friend.
  private String name;
  private int age;
  private String gender;
  private final java.util.Date birthDate;
  private int closeness;

  // Potential options for gender.
  public final String GENDER_FEMALE = "Female";
  public final String GENDER_MALE = "Male";

  // Potential options for closeness.
  public final int CLOSENESS_ACQUAINTANCE = 0;
  public final int CLOSENESS_FRIEND = 1;
  public final int CLOSENESS_BESTIE = 2;
  // Invalid case for an invalid value.
  public final int CLOSENESS_INVALID = -1;

  // Constructor for the Friend class.
  public Friend(String name, int age, String gender, Date birthDate, int closeness) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.birthDate = birthDate;
    // If the closeness is not any of the valid cases for closeness, assign it the invalid case.
    if (closeness != CLOSENESS_ACQUAINTANCE && closeness != CLOSENESS_FRIEND && closeness != CLOSENESS_BESTIE) {
      this.closeness = CLOSENESS_INVALID;
    } else {
      this.closeness = closeness;
    }
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

  public Date getBirthDate() {
    return birthDate;
  }

  public int getCloseness() {
    return closeness;
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
  //</editor-fold>
}
