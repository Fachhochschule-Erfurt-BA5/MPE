package com.pme.mpe.model.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.pme.mpe.model.tasks.*;
import com.pme.mpe.model.user.exceptions.CategoryException;
import com.pme.mpe.model.util.PasswordHashing;

import org.jetbrains.annotations.NotNull;

/**
 * The type User.
 */
@Entity
public class User {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = "User";
    /**
     * The constant SALT_LENGTH.
     */
    public static final int SALT_LENGTH = 30;

    /* /////////////////////Attributes///////////////////////// */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long userId;

    private long version;

    @NotNull
    @ColumnInfo(name = "created")
    private LocalDate created;

    @NotNull
    @ColumnInfo(name = "updated")
    private LocalDate updated;

    @NotNull
    @ColumnInfo(name = "firstName")
    private String firstName;

    @NotNull
    @ColumnInfo(name = "lastName")
    private String lastName;

    @NotNull
    @ColumnInfo(name = "email")
    private String email;

    @NotNull
    @ColumnInfo(name = "profileImageUrl")
    private String profileImageUrl;

    //For managing the password security
    @NotNull
    @ColumnInfo(name = "salt")
    private String salt;

    @NotNull
    @ColumnInfo(name = "securePassword")
    private String securePassword;

    //Which categories belong to a User
    private List<Category> userCategories;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new User. And creates the salt for the user
     * Creates as well a secure Password with given salt
     *
     * @param firstName         the first name
     * @param lastName          the last name
     * @param email             the email
     * @param notSecurePassword the not secure password
     * @param profileImageUrl   the profile image url
     */
    public User(String firstName, String lastName, String email, String notSecurePassword, String profileImageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.userCategories = new ArrayList<>();

        //Generate the Random salt and create the secure password
        this.salt = PasswordHashing.getSalt(SALT_LENGTH);
        this.securePassword = PasswordHashing.generateSecurePassword(notSecurePassword, salt);
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets id.
     *
     * @param userId the id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public LocalDate getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(LocalDate created) {
        this.created = created;
    }

    /**
     * Gets updated.
     *
     * @return the updated
     */
    public LocalDate getUpdated() {
        return updated;
    }

    /**
     * Sets updated.
     *
     * @param updated the updated
     */
    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets profile image url.
     *
     * @return the profile image url
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * Sets profile image url.
     *
     * @param profileImageUrl the profile image url
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * Gets salt.
     *
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Gets secure password.
     *
     * @return the secure password
     */
    public String getSecurePassword() {
        return securePassword;
    }

    /**
     * Create a new Salt for the new password
     * and Sets a new secure password.
     *
     * @param notSecurePassword the not secure password coming from the frontend
     */
    public void setSecurePassword(String notSecurePassword) {
        this.salt = PasswordHashing.getSalt(SALT_LENGTH);
        this.securePassword = PasswordHashing.generateSecurePassword(notSecurePassword, salt);
    }

    /**
     * Gets user categories.
     *
     * @return the user categories
     */
    public List<Category> getUserCategories() {
        return userCategories;
    }

    /**
     * Sets user categories.
     *
     * @param userCategories the user categories
     */
    public void setUserCategories(List<Category> userCategories) {
        this.userCategories = userCategories;
    }

    /* /////////////////////Methods///////////////////////// */

    /**
     * Add one category to user categories.
     *
     * @param category the category
     */
    public void addOneCategoryToUserCategories(Category category){
        this.userCategories.add(category);
    }

    /**
     * Remove one category from user categories.
     *
     * @param category the category
     * @throws CategoryException the category exception
     */
    public void removeOneCategoryFromUserCategories(Category category) throws CategoryException {
        if(this.userCategories.contains(category))
        {
            this.userCategories.remove(category);
        }
        else{
            throw new CategoryException("Cannot erase a Category that a user does not have");
        }
    }

    /**
     * Update one category from user categories.
     *
     * @param oldCategory the old category
     * @param newCategory the new category
     * @throws CategoryException the category exception
     */
    public void updateOneCategoryFromUserCategories(Category oldCategory, Category newCategory) throws CategoryException {
        if(this.userCategories.contains(oldCategory))
        {
            this.userCategories.remove(oldCategory);
            this.userCategories.add(newCategory);
        }
        else{
            throw new CategoryException("Cannot update a Category that a user does not have");
        }
    }

    /* /////////////////////Overrides/////////////////////////// */

    /**
     * To string string.
     *
     * @return the string
     */
    @NotNull
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
