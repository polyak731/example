package com.develop.apachi.testtask.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.develop.apachi.testtask.database.UsersDatabaseContract;
import com.develop.apachi.testtask.model.adapters.UserDeserializer;
import com.google.gson.annotations.JsonAdapter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * User model with full user data.
 *
 * {@see https://randomuser.me/documentation#format}
 */
@JsonAdapter(value = UserDeserializer.class)
@Entity(tableName = UsersDatabaseContract.UsersTable.TABLE_NAME)
@AnyThread
public class User {

    /** Primary key is random int, because API retrieves nullable values, sometimes.*/
    @PrimaryKey
    @ColumnInfo(name = UsersDatabaseContract.UsersTable._ID)
    private int id;
    /** Id number. Based on user Id data.*/
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.USER_ID)
    @Nullable
    private final String idNumber;
    /** User's title. Depending on state.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.TITLE)
    private final String title;
    /** User's first name.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.FIRST_NAME)
    private final String firstName;
    /** User's last name.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.LAST_NAME)
    private final String lastName;
    /** User's nick name from user credentials JSON section.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.USER_NAME)
    private final String userName;
    /** User's birth date.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.BIRTH_DATE)
    private final DateTime birthDate;
    /** User's age.*/
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.AGE)
    private final int age;
    /** User's registration date.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.REGISTRATION_DATE)
    private final DateTime registrationDate;
    /** User's registration age.*/
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.REGISTRATION_AGE)
    private final int registrationAge;
    /** User's phone number.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.PHONE_NUMBER)
    private final String phoneNumber;
    /** User's cell number.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.CELL_NUMBER)
    private final String cellNumber;
    /** User's id type(Id number name). */
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.ID_TYPE)
    private final String idType;
    /** Url of thumbnail image.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.THUMBNAIL_PICTURE_URL)
    private final String thumbnailPictureUrl;
    /** Url of medium image.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.MEDIUM_PICTURE_URL)
    private final String mediumPictureUrl;
    /** Url of large image.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.LARGE_PICTURE_URL)
    private final String largePictureUrl;
    /** Local user's street name*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.STREET)
    private final String street;
    /** User's city name.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.CITY)
    private final String city;
    /** User's state name.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.STATE)
    private final String state;
    /** Storing post code always in Integer values, because post code is always number.*/
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.POST_CODE)
    private final String postCode;
    /** Using default Android location system instead of two variables.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.LOCATION_BLOB)
    private final Location location;
    /** Using DateTimeZone from JodaTime for more functionality.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.TIME_ZONE)
    private final DateTimeZone userTimeZone;
    /** User email is just data, using simple string.*/
    @Nullable
    @ColumnInfo(name = UsersDatabaseContract.UsersTable.EMAIL)
    private final String email;

    /**
     * Public constructor for Room.
     */
    public User(int id, @NonNull String idNumber,
                @Nullable String title,
                @Nullable String firstName,
                @Nullable String lastName,
                @Nullable String userName,
                @Nullable DateTime birthDate,
                int age,
                @Nullable DateTime registrationDate,
                int registrationAge,
                @Nullable String phoneNumber,
                @Nullable String cellNumber,
                @Nullable String idType,
                @Nullable String thumbnailPictureUrl,
                @Nullable String mediumPictureUrl,
                @Nullable String largePictureUrl,
                @Nullable String street,
                @Nullable String city,
                @Nullable String state,
                @Nullable String postCode,
                @Nullable Location location,
                @Nullable DateTimeZone userTimeZone,
                @Nullable String email) {
        this.id = id;
        this.idNumber = idNumber;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.birthDate = birthDate;
        this.age = age;
        this.registrationDate = registrationDate;
        this.registrationAge = registrationAge;
        this.phoneNumber = phoneNumber;
        this.cellNumber = cellNumber;
        this.idType = idType;
        this.thumbnailPictureUrl = thumbnailPictureUrl;
        this.mediumPictureUrl = mediumPictureUrl;
        this.largePictureUrl = largePictureUrl;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.location = location;
        this.userTimeZone = userTimeZone;
        this.email = email;
    }

    /**
     * Private constructor to avoid instantiation through simple allocation.
     *
     * @param aUserBuilder
     *      User builder.
     */
    private User(@NonNull Builder aUserBuilder) {

        id = (int) System.currentTimeMillis();
        idNumber = aUserBuilder.iIdNumber;
        age = aUserBuilder.iAge;
        birthDate = aUserBuilder.iBirthDate;
        cellNumber = aUserBuilder.iCellNumber;
        city = aUserBuilder.iCity;
        email = aUserBuilder.iEmail;
        firstName = aUserBuilder.iFirstName;
        idType = aUserBuilder.iIdType;
        largePictureUrl = aUserBuilder.iLargePicture;
        lastName = aUserBuilder.iLastName;
        location = aUserBuilder.iLocation;
        mediumPictureUrl = aUserBuilder.iMediumPicture;
        phoneNumber = aUserBuilder.iPhoneNumber;
        postCode = aUserBuilder.iPostCode;
        registrationAge = aUserBuilder.iRegistrationAge;
        registrationDate = aUserBuilder.iRegistrationDate;
        state = aUserBuilder.iState;
        title = aUserBuilder.iTitle;
        userName = aUserBuilder.iUserName;
        userTimeZone = aUserBuilder.iUserTimeZone;
        thumbnailPictureUrl = aUserBuilder.iThumbnailPicture;
        street = aUserBuilder.iStreet;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getIdNumber() {
        return idNumber;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getUserName() {
        return userName;
    }

    @Nullable
    public DateTime getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return age;
    }

    @Nullable
    public DateTime getRegistrationDate() {
        return registrationDate;
    }

    public int getRegistrationAge() {
        return registrationAge;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getCellNumber() {
        return cellNumber;
    }

    @Nullable
    public String getIdType() {
        return idType;
    }

    @Nullable
    public String getThumbnailPictureUrl() {
        return thumbnailPictureUrl;
    }

    @Nullable
    public String getMediumPictureUrl() {
        return mediumPictureUrl;
    }

    @Nullable
    public String getLargePictureUrl() {
        return largePictureUrl;
    }

    @Nullable
    public String getStreet() {
        return street;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    @Nullable
    public String getState() {
        return state;
    }

    public String getPostCode() {
        return postCode;
    }

    @Nullable
    public Location getLocation() {
        return location;
    }

    @Nullable
    public DateTimeZone getUserTimeZone() {
        return userTimeZone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    /**
     * Builder class to create users.
     */
    public static class Builder {

        private final String iIdNumber;

        @Nullable
        private String iTitle;

        @Nullable
        private String iFirstName;

        @Nullable
        private String iLastName;

        @Nullable
        private String iUserName;

        @Nullable
        private DateTime iBirthDate;

        private int iAge;

        @Nullable
        private DateTime iRegistrationDate;

        private int iRegistrationAge;

        @Nullable
        private String iPhoneNumber;

        @Nullable
        private String iCellNumber;

        @Nullable
        private String iIdType;

        @Nullable
        private String iThumbnailPicture;

        @Nullable
        private String iMediumPicture;

        @Nullable
        private String iLargePicture;

        @Nullable
        private String iStreet;

        @Nullable
        private String iCity;

        @Nullable
        private String iState;

        private String iPostCode;
        /** Using default Android location system instead of two variables.*/
        @Nullable
        private Location iLocation;
        /** Using DateTimeZone from JodaTime for more functionality.*/
        @Nullable
        private DateTimeZone iUserTimeZone;
        /** User email is just data, using simple string.*/
        @Nullable
        private String iEmail;

        /**
         * Builder constructor.
         *
         * @param aIdNumber
         *      Id number.
         */
        public Builder(String aIdNumber) {

            iIdNumber = aIdNumber;
        }

        /**
         * @param aTitle Users's title.
         */
        public Builder setTitle(@Nullable String aTitle) {
            iTitle = aTitle;
            return this;
        }

        /**
         * @param aFirstName User's first name.
         */
        public Builder setFirstName(@Nullable String aFirstName) {
            iFirstName = aFirstName;
            return this;
        }

        /**
         * @param aLastName Users last name.
         */
        public Builder setLastName(@Nullable String aLastName) {
            iLastName = aLastName;
            return this;
        }

        /**
         * @param aUserName User's nickname.
         */
        public Builder setUserName(@Nullable String aUserName) {
            iUserName = aUserName;
            return this;
        }

        /**
         * @param aBirthDate User's birth date.
         */
        public Builder setBirthDate(@Nullable DateTime aBirthDate) {
            iBirthDate = aBirthDate;
            return this;
        }

        /**
         * @param aAge Users's age.
         */
        public Builder setAge(int aAge) {
            iAge = aAge;
            return this;
        }

        /**
         * @param aRegistrationDate User's registration date and time.
         */
        public Builder setRegistrationDate(@Nullable DateTime aRegistrationDate) {
            iRegistrationDate = aRegistrationDate;
            return this;
        }

        /**
         * @param aRegistrationAge User's registration age.
         */
        public Builder setRegistrationAge(int aRegistrationAge) {
            iRegistrationAge = aRegistrationAge;
            return this;
        }

        /**
         * @param aPhoneNumber User's phone number.
         */
        public Builder setPhoneNumber(@Nullable String aPhoneNumber) {
            iPhoneNumber = aPhoneNumber;
            return this;
        }

        /**
         * @param aCellNumber User's cell number.
         */
        public Builder setCellNumber(@Nullable String aCellNumber) {
            iCellNumber = aCellNumber;
            return this;
        }

        /**
         * @param aIdType User's id type.
         */
        public Builder setIdType(@Nullable String aIdType) {
            iIdType = aIdType;
            return this;
        }

        /**
         * @param aThumbnailPicture User's thumbnail picture.
         */
        public Builder setThumbnailPicture(@Nullable String aThumbnailPicture) {
            iThumbnailPicture = aThumbnailPicture;
            return this;
        }

        /**
         * @param aMediumPicture User's medium size picture.
         */
        public Builder setMediumPicture(@Nullable String aMediumPicture) {
            iMediumPicture = aMediumPicture;
            return this;
        }

        /**
         * @param aLargePicture User's larger size picture.
         */
        public Builder setLargePicture(@Nullable String aLargePicture) {
            iLargePicture = aLargePicture;
            return this;
        }

        /**
         * @param aStreet User's street.
         */
        public Builder setStreet(@Nullable String aStreet) {
            iStreet = aStreet;
            return this;
        }

        /**
         * @param aCity User's city.
         */
        public Builder setCity(@Nullable String aCity) {
            iCity = aCity;
            return this;
        }

        /**
         * @param aState User's state.
         */
        public Builder setState(@Nullable String aState) {
            iState = aState;
            return this;
        }

        /**
         * @param aPostCode User's post code.
         */
        public Builder setPostCode(String aPostCode) {
            iPostCode = aPostCode;
            return this;
        }

        /**
         * @param aLocation User's location.
         */
        public Builder setLocation(@Nullable Location aLocation) {
            iLocation = aLocation;
            return this;
        }

        /**
         * @param aUserTimeZone User's time zone.
         */
        public Builder setUserTimeZone(@Nullable DateTimeZone aUserTimeZone) {
            iUserTimeZone = aUserTimeZone;
            return this;
        }

        /**
         * @param aEmail User's email.
         */
        public Builder setEmail(@Nullable String aEmail) {
            iEmail = aEmail;
            return this;
        }

        /**
         * @return New instance of user class.
         */
        @NonNull
        public User build() {

            return new User(this);
        }
    }
}
