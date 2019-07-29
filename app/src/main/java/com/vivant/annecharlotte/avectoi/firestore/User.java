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
    private String userPhone;
    @Nullable
    private String urlPicture;
    private String userTown;

    private List<Integer> userSPList;

    public User() { }

    public User(String uid, String userName, String userEmail, String urlPicture) {
        this.uid = uid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.urlPicture = urlPicture;
        this.userPhone = "";
        this.userSPList = new ArrayList<>();
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getUserTown() { return userTown;}

    public void setUserTown(String town) {this.userTown = town;}

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

    public List<Integer> getUserSPList() { return userSPList; }

    public void setUserSPList(List<Integer> userSPList) { this.userSPList = userSPList; }


  /*  public Boolean getHouseholdSP() {
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

    public Boolean getDrivingSP() {
        return drivingSP;
    }

    public void setDrivingSP(Boolean drivingSP) {
        this.drivingSP = drivingSP;
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

    public Boolean getAdminSP() {
        return adminSP;
    }

    public void setAdminSP(Boolean adminSP) {
        this.adminSP = adminSP;
    }

    public Boolean getFloweringSP() {
        return floweringSP;
    }

    public void setFloweringSP(Boolean flowering) {
        this.floweringSP = flowering;
    }
*/

}
