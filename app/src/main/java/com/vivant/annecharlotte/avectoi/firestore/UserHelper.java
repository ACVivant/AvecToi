package com.vivant.annecharlotte.avectoi.firestore;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;

/**
 * Created by Anne-Charlotte Vivant on 15/07/2019.
 */
public class UserHelper {

        private static final String COLLECTION_NAME = "users";
        private static final String TAG = "USERHELPER";

        // --- COLLECTION REFERENCE ---
        public static CollectionReference getUsersCollection(){
            return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
        }

        // --- CREATE ---
    public static Task<Void> createUser(String uid, String userName,  String userEmail, String urlPicture) {
        User userToCreate = new User(uid, userName, userEmail, urlPicture);
        Log.d(TAG, "createUser: ");
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

        // --- GET ---
        public static Task<DocumentSnapshot> getUser(String uid){
            return UserHelper.getUsersCollection().document(uid).get();
        }

        // --- GET CURRENT USER ID ---
        public static String getCurrentUserId() {
            return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        }

        // --- GET CURRENT USER NAME ---
        public static String getCurrentUserName() {
            return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        }

        // --- GET CURRENT USER URL PICTURE ---
        public static String getCurrentUserUrlPicture() {
            return FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        }

        // --- UPDATE NAME---
        public static Task<Void> updateUsername(String username, String uid) {
            return UserHelper.getUsersCollection().document(uid).update("username", username);
        }

    // --- UPDATE TOKEN---
    public static Task<Void> updateUserToken(String usertoken, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userToken", usertoken);
    }

    // --- UPDATE SUPER POWER LIST---
    public static Task<Void> updateUserSPList(List<Integer> userSPList, String userId) {
        return UserHelper.getUsersCollection().document(userId).update("userSPList", userSPList);
    }

    // --- UPDATE REFERENCES LIST---
    public static Task<Void> updateEventHeroRefList(List<SosEvent> eventHeroRefList, String userId) {
        return UserHelper.getUsersCollection().document(userId).update("eventHeroRefList", eventHeroRefList);
    }

    // --- UPDATE PHONE, TOWN AND DESCRIPTION---

    public static Task<Void> updateTel(String tel, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userPhone", tel);
    }

    public static Task<Void> updateTown(String town, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userTown", town);
    }

    public static Task<Void> updateDescription(String description, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userDescription", description);
    }

        // --- DELETE ---
        public static Task<Void> deleteUser(String uid) {
            return UserHelper.getUsersCollection().document(uid).delete();
        }

        // -- GET ALL USERS --
        public static Query getAllUsers(){
            return UserHelper.getUsersCollection().orderBy("userName", Query.Direction.ASCENDING);
        }

        // -- GET USER TOKEN --
    public static String getUserToken(String uid) {
            return UserHelper.getUserToken(uid);
    }

}
