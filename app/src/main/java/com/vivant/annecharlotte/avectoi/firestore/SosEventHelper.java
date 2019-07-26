package com.vivant.annecharlotte.avectoi.firestore;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Anne-Charlotte Vivant on 24/07/2019.
 */
public class SosEventHelper {

    private static final String COLLECTION_NAME = "events";
    private static final String TAG = "SOSHELPER";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getEventsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---
/*    public static Task<Void> createEvent(String eventId, int themeIndex, String description, String town, int numberHero, String userAskId, Date dateCreated, Date dateNeed) {
        SosEvent eventToCreate = new SosEvent(eventId, themeIndex, description, town, numberHero, userAskId, dateCreated, dateNeed);
        Log.d(TAG, "createEvent");
        return SosEventHelper.getEventsCollection().document(eventId).set(eventToCreate);
    }*/

    public static Task<DocumentReference> createEvent(int themeIndex, String description, String town, int numberHero, String userAskId, Date dateCreated, Date dateNeed) {
        SosEvent eventToCreate = new SosEvent(themeIndex, description, town, numberHero, userAskId, dateCreated, dateNeed);
        return SosEventHelper.getEventsCollection().add(eventToCreate);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getEvent(String eventId){
        return SosEventHelper.getEventsCollection().document(eventId).get();
    }

    // --- UPDATE EVENT ---
    public static Task<Void> updateEventTheme(int themeIndex, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("themeIndex", themeIndex);
    }

    public static Task<Void> updateEventDateNeed(Date dateNeed, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("dateNeed", dateNeed);
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

    public static Task<Void> updateEventNumberHero(int numberHero, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("numberHero", numberHero);
    }

    public static Task<Void> updateEventUserAskId(String userAskId, String eventId) {
        return SosEventHelper.getEventsCollection().document(eventId).update("userAskId", userAskId);
    }
}
