package com.vivant.annecharlotte.avectoi.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.List;

/**
 * methods to implement firebase database events collection
 */
public class SosEventHelper {

    private static final String COLLECTION_NAME = "events";
    private static final String TAG = "SOSHELPER";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getEventsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<DocumentReference> createEvent(int themeIndex, String description, String town, int numberHero, User userAsk, Date dateCreated, Date dateNeed,boolean car) {
        SosEvent eventToCreate = new SosEvent(themeIndex, description, town, numberHero, userAsk, dateCreated, dateNeed, car);
        return SosEventHelper.getEventsCollection().add(eventToCreate);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getEvent(String eventId){
        return SosEventHelper.getEventsCollection().document(eventId).get();
    }

    // -- GET ALL EVENTS --
    public static Query getAllEvents(){
        Date today = new Date();
        return SosEventHelper.getEventsCollection().orderBy("dateNeed", Query.Direction.ASCENDING);
    }

    public static Query getAllEventsFromToday(){
        Date today = new Date();
        return SosEventHelper.getEventsCollection().orderBy("dateNeed", Query.Direction.ASCENDING).startAt(today);
    }

    public static Query getAllEventsWithHeroTofind() {
        return SosEventHelper.getEventsCollection().orderBy("numberHeroNotFound", Query.Direction.ASCENDING).startAt(1);
    }

    // --- UPDATE EVENT ---
    public static Task<Void> updateEventTheme(int themeIndex, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("themeIndex", themeIndex);
    }

    public static Task<Void> updateEventDateNeed(Date dateNeed, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("dateNeed", dateNeed);
    }

    public static Task<Void> updateEventDateCreated(Date date, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("dateCreated", date);
    }

    public static Task<Void> updateEventTown(String town, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("town", town);
    }

    public static Task<Void> updateEventDescription(String description, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("description", description);
    }

    public static Task<Void> updateEventHeroId(String heroId, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("userHeroId", heroId);
    }

    public static Task<Void> updateEventNumberHeroWanted(int numberHero, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("numberHeroWanted", numberHero);
    }

    public static Task<Void> updateEventNumberHeroStillToFind(int numberHero, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("numberHeroNotFound", numberHero);
    }

    public static Task<Void> updateEventUserAskId(String userAskId, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("userAskId", userAskId);
    }

    // --- UPDATE SUPER HEROS LIST---

    public static Task<Void> updateUserHerosList(List<User> userHeroList, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("userHeroList", userHeroList);
    }

    public static Task<Void> updateUserHerosIdList(List<String> userHeroIdList, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("userHeroIdList", userHeroIdList);
    }

    public static Task<Void> updateDeletedHeroToken(String deletedHeroToken, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("deletedHeroToken", deletedHeroToken);
    }

    public static Task<Void> updateUserHerosNotFound(int nbHeros, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("numberHeroNotFound", nbHeros);
    }

    public static Task<Void> updateDateHeroOk(Date today, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("dateHeroOk", today);
    }

    // --- UPDATE MISSION STATUS
    public static Task<Void> updateMissionOk(boolean missionOK, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("missionOK", missionOK);
    }
}
