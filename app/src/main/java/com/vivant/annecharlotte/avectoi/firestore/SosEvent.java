package com.vivant.annecharlotte.avectoi.firestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Anne-Charlotte Vivant on 24/07/2019.
 */
public class SosEvent {

    private int themeIndex;
    private Date dateNeed;
    private Date dateCreated;
    @Nullable
    private Date dateHeroOk;
    private String town;
    private String description;
    private User userAsk;
    private String userAskId;
    @Nullable
    private List<User> userHeroList;
    @Nullable
    private List<String> userHeroIdList;
    private int numberHeroWanted;
    private int numberHeroNotFound;
    private boolean missionOK;
    private boolean car;

    public SosEvent() { }

    public SosEvent(int themeIndex, String description, String town, int numberHeroWanted, User userAsk, Date dateCreated, Date dateNeed, boolean car) {
        this.themeIndex = themeIndex;
        this.description = description;
        this.town = town;
        this.numberHeroWanted = numberHeroWanted;
        this.numberHeroNotFound = numberHeroWanted;
        this.missionOK = false;
        this.userAsk = userAsk;
        this.userAskId = userAsk.getUid();
        this.dateCreated = dateCreated;
        this.dateNeed = dateNeed;
        this.dateHeroOk = null;
        this.car = car;
        this.userHeroList = new ArrayList<>();
        this.userHeroIdList = new ArrayList<>();
    }

    public int getThemeIndex() {
        return themeIndex;
    }

    public void setThemeIndex(int themeIndex) {
        this.themeIndex = themeIndex;
    }

    public Date getDateNeed() {
        return dateNeed;
    }

    public void setDateNeed(Date dateNeed) {
        this.dateNeed = dateNeed;
    }

    public Date getDateHeroOk() {
        return dateHeroOk;
    }

    public void setDateHeroOk(Date dateHeroOk) {
        this.dateHeroOk = dateHeroOk;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserAsk() {
        return userAsk;
    }

    public void setUserAsk(User userAsk) {
        this.userAsk = userAsk;
    }

    public String getUserAskId() {
        return userAskId;
    }

    public void setUserAskId(String userAskId) {
        this.userAskId = userAskId;
    }

    public List<User> getUserHeroList() {
    return userHeroList;
}

    public void setUserHeroList(List<User> userHeroList) {
        this.userHeroList = userHeroList;
    }

    public List<String> getUserHeroIdList() {
        return userHeroIdList;
    }

    public void setUserHeroIdList(List<String> userHeroIdList) {
        this.userHeroIdList = userHeroIdList;
    }

    public int getNumberHeroWanted() {
        return numberHeroWanted;
    }

    public void setNumberHeroWanted(int numberHeroWanted) {
        this.numberHeroWanted = numberHeroWanted;
    }

    public int getNumberHeroNotFound() {
        return numberHeroNotFound;
    }

    public void setNumberHeroNotFound(int numberHeroNotFound) {
        this.numberHeroNotFound = numberHeroNotFound;
    }

    public boolean isMissionOK() {
        return missionOK;
    }

    public void setMissionOK(boolean missionOK) {
        this.missionOK = missionOK;
    }

    public boolean getCar() {
        return car;
    }

    public void setCar(boolean car) {
        this.car = car;
    }
}
