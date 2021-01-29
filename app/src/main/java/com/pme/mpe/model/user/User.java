package com.pme.mpe.model.user;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.pme.mpe.model.tasks.*;
import com.pme.mpe.model.user.exceptions.CategoryException;
import com.pme.mpe.model.util.PasswordHashing;

import org.jetbrains.annotations.NotNull;

@Entity
public class User {

    @Ignore
    public static final String LOG_TAG = "User";

    @Ignore
    public static final int SALT_LENGTH = 30;

    /* /////////////////////Attributes///////////////////////// */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private long userId;

    @NotNull
    @ColumnInfo(name = "version")
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
    @Ignore
    private List<Category> userCategories;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new User. And creates the salt for the user
     * Creates as well a secure Password with given salt
     *
     * @param firstName         the first name
     * @param lastName          the last name
     * @param email             the email
     * @param securePassword    the not secure password
     * @param profileImageUrl   the profile image url
     */
    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String securePassword, @NotNull String profileImageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.userCategories = new ArrayList<>();

        //Generate the Random salt and create the secure password
        this.salt = PasswordHashing.getSalt(SALT_LENGTH);
        this.securePassword = PasswordHashing.generateSecurePassword(securePassword, salt);
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @NotNull
    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(@NotNull LocalDate created) {
        this.created = created;
    }

    @NotNull
    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(@NotNull LocalDate updated) {
        this.updated = updated;
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    @NotNull
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(@NotNull String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @NotNull
    public String getSalt() {
        return salt;
    }

    public void setSalt(@NotNull String salt) {
        this.salt = salt;
    }

    public List<Category> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(List<Category> userCategories) {
        this.userCategories = userCategories;
    }

    @NotNull
    public String getSecurePassword() {
        return securePassword;
    }

    /**
     * Create a new Salt for the new password
     * and Sets a new secure password.
     *
     * @param notSecurePassword the not secure password coming from the frontend
     */
    public void setSecurePassword(@NotNull String notSecurePassword) {
        this.salt = PasswordHashing.getSalt(SALT_LENGTH);
        this.securePassword = PasswordHashing.generateSecurePassword(notSecurePassword, salt);
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

    /**
     * Gets all category blocks from this user for a given date.
     *
     * @param date the date
     * @return the all category blocks for a given date
     */
    public List<CategoryBlock> getAllCategoryBlocksFromUserForAGivenDate(LocalDate date, User user)
    {
        if(user == this)
        {
            List<CategoryBlock> categoryBlocks = new ArrayList<>();

            for (int i = 0; i < this.userCategories.size(); i++) {
                for (int j = 0; j < this.userCategories.get(i).getCategoryBlockList().size(); j++) {
                    if(this.userCategories.get(i).getCategoryBlockList().get(j).getDate() == date)
                    {
                        categoryBlocks.add(this.userCategories.get(i).getCategoryBlockList().get(j));
                    }
                }
            }

            return categoryBlocks;
        }
        else
        {
            return null;
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
