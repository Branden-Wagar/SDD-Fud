package fud.fud.Database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import fud.fud.Models.Event;

import static android.content.ContentValues.TAG;

/**
 * Implement CRUD operations for firestore database.
 * NOTE: All database tasks should be handled by a
 * DatabaseManager object.
 */
public class DatabaseManager implements IEventQuery {

    private FirebaseFirestore db;
    private static CollectionReference eventCollection;

    /**
     * Initialize the database instance.
     *
     */
    public DatabaseManager(FirebaseFirestore db) {
        this.db = db;
        eventCollection = db.collection(KeyStore.EVENT_TABLE_NAME);
    }

    /**
     * Add an event by calling the generic add method
     * with the correct table name.
     *
     * @param e - Event to add to the database
     */
    public void add(Event e) {
        this.add(e, KeyStore.EVENT_TABLE_NAME, e.getEventName());
    }

    /**
     * Add an object to a specified collection within the FireStore.
     * @param obj - Object to add to the collection
     * @param collection - Name of collection object is being added to.
     * @param <T> - Generic type handling
     */
    private <T> void add(T obj, String collection, String document) {
        db.collection(collection)
                .document(document)
                .set(obj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    /**
     * Get all objects in a collection.
     * @param collectionName - Name of the collection in which the objects are stored.
     * @return a list of all of the objects in the collection
     */
    private Task<QuerySnapshot> getAll(String collectionName) {
        return db.collection(collectionName).get();
    }

    /**
     * Get all events within a specified distance.
     * @param distanceToUser - Distance of the event to the user's current position.
     * @param withInMaximumDistance - Maximum distance of event from the user.
     * @return A QuerySnapShot of all of the events within this distance.
     */
    @Override
    public Task<QuerySnapshot> getLocalEvents(double distanceToUser, double withInMaximumDistance) {
        return eventCollection
                .whereGreaterThan(KeyStore.EVENT_DISTANCE, withInMaximumDistance)
                .get();
    }

    /**
     * Get all events of a specified cuisine type.
     * @param cuisineType - Type of cuisine user specified.
     * @return - A QuerySnapShot of all events with the specified
     * cuisine type.
     */
    @Override
    public Task<QuerySnapshot> getCuisineFilteredFoodEvents(String cuisineType) throws InterruptedException {

        return eventCollection
            .whereEqualTo(KeyStore.EVENT_CUISINE_TYPE, cuisineType)
            .get();
    }

    /**
     * Get all events up to a certain price.
     * @param maximumPrice - Maximum price desired by user.
     * @return - A QuerySnapShot of all events under the specified price.
     */
    @Override
    public Task<QuerySnapshot> getPriceFilteredEvents(double maximumPrice) {
        return eventCollection
                .whereLessThanOrEqualTo(KeyStore.EVENT_PRICE, maximumPrice)
                .get();
    }
    /**
     * Get all events up to a certain price.
     * @param minimumPrice - Minimum price desired by user.
     * @param maximumPrice - Maximum price desired by user.
     * @return - A QuerySnapShot of all events within the specified price range.
     */
    @Override
    public Task<QuerySnapshot> getPriceFilteredEvents(double minimumPrice, double maximumPrice) {
        return eventCollection
                .whereLessThanOrEqualTo(KeyStore.EVENT_PRICE, maximumPrice)
                .whereGreaterThanOrEqualTo(KeyStore.EVENT_PRICE, minimumPrice)
                .get();
    }

    /**
     * Get all events from the event collection.
     * @return A QuerySnapShot of all events within the event collection.
     */
    @Override
    public Task<QuerySnapshot> getAllEvents() {
        return getAll(KeyStore.EVENT_TABLE_NAME);
    }

    /**
     * Get all events that are occurring today.
     * @return A QuerySnapShot of all events that are occurring on the day of the query.
     */
    @Override
    public Task<QuerySnapshot> getTodayEvents() {
        return eventCollection
                .whereEqualTo(KeyStore.EVENT_DATE, java.time.LocalDate.now())
                .get();
    }

    /**
     * Get all events on a specified date
     * @param date - Date specified by user
     * @return A QuerySnapShot of all events on the date specified.
     */
    @Override
    public Task<QuerySnapshot> getDateSpecificEvents(Date date) {
        return eventCollection
                .whereEqualTo(KeyStore.EVENT_DATE, date)
                .get();
    }

    /*
        TODO: Implement this later when decided on uniqueness
        TODO: qualifier for events
     */
    @Override
    public Task<QuerySnapshot> getEventObect() {
        return null;
    }
}
