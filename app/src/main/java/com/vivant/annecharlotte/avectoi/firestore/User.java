package com.vivant.annecharlotte.avectoi.firestore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Class describing User Collection
 */
public class User {

    private String uid;
    private String userName;
    private String userEmail;

    @Nullable
    private int userPhone;
    @Nullable
    private String urlPicture;

    private Boolean householdSP;  // ménage
    private Boolean ironingSP; // repassage
    private Boolean shoppingSP; // courses
    private Boolean cookingSP; // cuisine
    private Boolean driveSP; // conduite
    private Boolean gardeningSP; // gardening
    private Boolean diySP; // bricolage
    private Boolean worksSP; // travaux
    private Boolean relocationSP; // déménagement
    private Boolean readingSP; //lecture
    private Boolean companySP; // tenir compagnie
    private Boolean babysittingSP; // baby sitting
    private Boolean tutoringSP; // soutien scolaire
    private Boolean sewingSP; // couture

    private Boolean disponibility;

    public User() { }

    public User(String uid, String userName, String userEmail, String urlPicture) {
        this.uid = uid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.urlPicture = urlPicture;
        this.userPhone = 0;
        this.householdSP = false;
        this.ironingSP = false;
        this.shoppingSP = false;
        this.cookingSP = false;
        this.driveSP = false;
        this.gardeningSP = false;
        this.diySP = false;
        this.worksSP = false;
        this.relocationSP = false;
        this.readingSP = false;
        this.companySP = false;
        this.babysittingSP = false;
        this.tutoringSP = false;
        this.sewingSP = false;
        this.disponibility = false;
    }

    public User(String uid, String userName, int userPhone,  String userEmail, String urlPicture,
                Boolean householdSP, Boolean ironingSP, Boolean shoppingSP, Boolean cookingSP, Boolean driveSP, Boolean gardeningSP, Boolean diySP, Boolean worksSP,
                Boolean relocationSP, Boolean readingSP, Boolean companySP, Boolean babysittingSP, Boolean tutoringSP, Boolean sewingSP,
                Boolean disponibility) {
        this.uid = uid;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.urlPicture = urlPicture;
        this.householdSP = householdSP;
        this.ironingSP = ironingSP;
        this.shoppingSP = shoppingSP;
        this.cookingSP = cookingSP;
        this.driveSP = driveSP;
        this.gardeningSP = gardeningSP;
        this.diySP = diySP;
        this.worksSP = worksSP;
        this.relocationSP = relocationSP;
        this.readingSP = readingSP;
        this.companySP = companySP;
        this.babysittingSP = babysittingSP;
        this.tutoringSP = tutoringSP;
        this.sewingSP = sewingSP;
        this.disponibility = disponibility;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }

    @Nullable
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@Nullable String userEmail) {
        this.userEmail = userEmail;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public Boolean getHouseholdSP() {
        return householdSP;
    }

    public void setHouseholdSP(Boolean householdSP) {
        this.householdSP = householdSP;
    }

    public Boolean getIroningSP() {
        return ironingSP;
    }

    public void setIroningSP(Boolean ironingSP) {
        this.ironingSP = ironingSP;
    }

    public Boolean getShoppingSP() {
        return shoppingSP;
    }

    public void setShoppingSP(Boolean shoppingSP) {
        this.shoppingSP = shoppingSP;
    }

    public Boolean getCookingSP() {
        return cookingSP;
    }

    public void setCookingSP(Boolean cookingSP) {
        this.cookingSP = cookingSP;
    }

    public Boolean getDriveSP() {
        return driveSP;
    }

    public void setDriveSP(Boolean driveSP) {
        this.driveSP = driveSP;
    }

    public Boolean getGardeningSP() {
        return gardeningSP;
    }

    public void setGardeningSP(Boolean gardeningSP) {
        this.gardeningSP = gardeningSP;
    }

    public Boolean getDiySP() {
        return diySP;
    }

    public void setDiySP(Boolean diySP) {
        this.diySP = diySP;
    }

    public Boolean getWorksSP() {
        return worksSP;
    }

    public void setWorksSP(Boolean worksSP) {
        this.worksSP = worksSP;
    }

    public Boolean getRelocationSP() {
        return relocationSP;
    }

    public void setRelocationSP(Boolean relocationSP) {
        this.relocationSP = relocationSP;
    }

    public Boolean getReadingSP() {
        return readingSP;
    }

    public void setReadingSP(Boolean readingSP) {
        this.readingSP = readingSP;
    }

    public Boolean getCompanySP() {
        return companySP;
    }

    public void setCompanySP(Boolean companySP) {
        this.companySP = companySP;
    }

    public Boolean getBabysittingSP() {
        return babysittingSP;
    }

    public void setBabysittingSP(Boolean babysittingSP) {
        this.babysittingSP = babysittingSP;
    }

    public Boolean getTutoringSP() {
        return tutoringSP;
    }

    public void setTutoringSP(Boolean tutoringSP) {
        this.tutoringSP = tutoringSP;
    }

    public Boolean getSewingSP() {
        return sewingSP;
    }

    public void setSewingSP(Boolean sewingSP) {
        this.sewingSP = sewingSP;
    }

    public Boolean getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(Boolean disponibility) {
        this.disponibility = disponibility;
    }
}
