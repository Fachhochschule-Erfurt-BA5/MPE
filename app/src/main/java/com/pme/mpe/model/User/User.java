package com.pme.mpe.model.User;

import java.time.LocalDate;
import java.util.List;

public class User {


    /* /////////////////////Attributes///////////////////////// */


    protected LocalDate created;
    protected LocalDate updated;

    protected String firstName;
    protected String lastName;
    protected String email;

    protected int version;

    private char[] password;

    public List<User> shareWithUser;
    //public List<Category> userCategories;

    /* /////////////////////Constructors/////////////////////////// */

    public User(String firstName, String lastName, String email, char[] password){
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
    }

    //Default User
    public User(){
        this.firstName = "Max";
        this.lastName  = "Mustermann";
        this.email     = "max.mustermann@email.com";
        this.password  = new char[] {'P', 'a', 's', 's', 'w', 'o', 'r', 'd'};
    }



    /* /////////////////////Methods///////////////////////// */

    public void sendShareRequest(User this){

    }

    public void acceptShareRequest(User user){

    }





    /* /////////////////////Getter/Setter///////////////////////// */

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
