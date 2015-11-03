package com.uidesign.braden.contactmanager;

import java.io.Serializable;

/**
 * Created by braden on 11/3/15.
 */
public class Contact implements Comparable<Contact>, Serializable {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String firstName, String lastName, String phoneNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public Contact() {
        this.firstName = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.emailAddress = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public int compareTo(Contact otherContact) {
        return this.lastName.compareTo(otherContact.lastName);
    }

    @Override
    public String toString() {
        return this.lastName + ", " + this.firstName;
    }
}
