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
    @Nullable
    private String userTown;
    @Nullable
    private String userDescription;
    @Nullable
    private String userToken;
    @Nullable
    //private List<SosEvent> eventHeroRefList;
    private List<User> eventHeroRefList;

    private List<Integer> userSPList;

    private Boolean activeAccount;



    public User() { }

    public User(String uid, String userName, String userEmail, String urlPicture) {
        this.uid = uid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.urlPicture = urlPicture;
        this.userPhone = null;
        this.userDescription = null;
        this.userTown = null;
        this.userSPList = new ArrayList<>();
        this.userToken = null;
        this.eventHeroRefList = new ArrayList<>();
        this.activeAccount = true;
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
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(@Nullable String userToken) {
        this.userToken = userToken;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    @Nullable
    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(@Nullable String userDescription) {
        this.userDescription = userDescription;
    }

/*    @Nullable
    public List<SosEvent> getEventHeroRefList() { return eventHeroRefList;}

    public void setEventHeroRefList(@Nullable List<SosEvent> eventHeroRefList) {
        this.eventHeroRefList = eventHeroRefList;
    }*/

    @Nullable
    public List<User> getEventHeroRefList() { return eventHeroRefList;}

    public void setEventHeroRefList(@Nullable List<User> eventHeroRefList) {
        this.eventHeroRefList = eventHeroRefList;
    }

    public List<Integer> getUserSPList() { return userSPList; }

    public void setUserSPList(List<Integer> userSPList) { this.userSPList = userSPList; }

    public Boolean getActiveAccount() {return activeAccount;}
    public void setActiveAccount(Boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

}
