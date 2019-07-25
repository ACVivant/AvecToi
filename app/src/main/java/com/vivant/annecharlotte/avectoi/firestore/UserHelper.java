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

        // --- UPDATE SUPER POWER ---
        public static Task<Void> updateHouseholdSP(boolean householdSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("householdSP", householdSP);
    }

    public static Task<Void> updateIroningSP(boolean ironingSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("ironingSP", ironingSP);
    }

    public static Task<Void> updateShoppingSP(boolean shoppingSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("shoppingSP", shoppingSP);
    }

    public static Task<Void> updateCookingSP(boolean cookingSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("cookingSP", cookingSP);
    }

    public static Task<Void> updateGardeningSP(boolean gardeningSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("gardeningSP", gardeningSP);
    }

    public static Task<Void> updateDrivingSP(boolean drivingSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("drivingSP", drivingSP);
    }

    public static Task<Void> updateDiySP(boolean diySP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("diySP", diySP);
    }

    public static Task<Void> updateWorksSP(boolean  worksSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update(" worksSP",  worksSP);
    }

    public static Task<Void> updateRelocationSP(boolean relocationSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("relocationSP", relocationSP);
    }

    public static Task<Void> updateReadingSP(boolean readingSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("readingSP", readingSP);
    }

    public static Task<Void> updateCompanySP(boolean companySP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("companySP", companySP);
    }
    public static Task<Void> updateBabysittingSP(boolean babysittingSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("babysittingSP", babysittingSP);
    }

        public static Task<Void> updateTutoringSP(boolean tutoringSP, String uid) {
            return UserHelper.getUsersCollection().document(uid).update("tutoringSP", tutoringSP);
        }
            public static Task<Void> updateSewingSP(boolean sewingSP, String uid) {
                return UserHelper.getUsersCollection().document(uid).update("sewingSP", sewingSP);
            }

    public static Task<Void> updateFloweringSP(boolean floweringSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("floweringSP", floweringSP);
    }

    public static Task<Void> updateAdminSP(boolean adminSP, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("adminSP", adminSP);
    }
    // --- UPDATE PHONE AND TOWN ---

    public static Task<Void> updateTel(String tel, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userPhone", tel);
    }

    public static Task<Void> updateTown(String town, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userTown", town);
    }

        // --- DELETE ---
        public static Task<Void> deleteUser(String uid) {
            return UserHelper.getUsersCollection().document(uid).delete();
        }

        // -- GET ALL USERS --
        public static Query getAllUsers(){
            return UserHelper.getUsersCollection().orderBy("userName", Query.Direction.ASCENDING);
        }
}
