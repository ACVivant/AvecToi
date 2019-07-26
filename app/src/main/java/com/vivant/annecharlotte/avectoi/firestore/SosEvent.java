package com.vivant.annecharlotte.avectoi.firestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Anne-Charlotte Vivant on 24/07/2019.
 */
public class SosEvent {
    private String eventId;
    private int themeIndex;
    private Date dateNeed;
    private Date dateCreated;
    private String town;
    private String description;
    private String userAskId;
    private List<String> userHeroIdList;
    private int numberHero;

    public SosEvent() { }

    public SosEvent(int themeIndex, String description, String town, int numberHero, String userAskId, Date dateCreated, Date dateNeed) {
        this.themeIndex = themeIndex;
        this.description = description;
        this.town = town;
        this.numberHero = numberHero;
        this.userAskId = userAskId;
        this.dateCreated = dateCreated;
        this.dateNeed = dateNeed;
        this.userHeroIdList = new ArrayList<>();
    }

/*    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }*/

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

    public String getUserAskId() {
        return userAskId;
    }

    public void setUserAskId(String userAskId) {
        this.userAskId = userAskId;
    }

    public List<String> getUserHeroIdList() {
        return userHeroIdList;
    }

    public void setUserHeroIdList(List<String> userHeroIdList) {
        this.userHeroIdList = userHeroIdList;
    }

    public int getNumberHero() {
        return numberHero;
    }

    public void setNumberHero(int numberHero) {
        this.numberHero = numberHero;
    }
}
