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
 *
 * TODO: Make this a singleton since no parallel operation
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
        eventCollection = db.collection("events");
    }

    /**
     * Add an event by calling the generic add method
     * with the correct table name.
     *
     * @param e - Event to add to the database
     */
    public void add(Event e) {
        this.add(e, "events", e.getEventName());
    }

    /**
     * Add an object to a specified collection within the FireStore.
     * @param obj - Object tso add to the collection
     * @param collection - Name of collection object is being adde          //d to.
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
     * @param withinMaximumDistance - Maximum distance of event from the user.
     * @return A QuerySnapShot of all of the events within this distance.
     */
    @Override
    public Task<QuerySnapshot> getLocalEvents(double distanceToUser, double withinMaximumDistance) {
        return eventCollection
                .whereGreaterThan("distance", withinMaximumDistance)
                .get();
    }

    /**
     * Get all events of a specified cuisine type.
     * @param cuisineType - Type of cuisine user specified.
     * @return - A QuerySnapShot of all events with the specified
     * cuisine type.
     */
    @Override
    public Task<QuerySnapshot> getCuisineFilteredFoodEvents(String cuisineType) {

        return eventCollection
            .whereEqualTo("cuisineType", cuisineType)
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
                .whereLessThanOrEqualTo("price", maximumPrice)
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
                .whereLessThanOrEqualTo("price", maximumPrice)
                .whereGreaterThanOrEqualTo("price", minimumPrice)
                .get();
    }

    /**
     * Get all events from the event collection.
     * @return A QuerySnapShot of all events within the event collection.
     */
    @Override
    public Task<QuerySnapshot> getAllEvents() {
        return getAll("events");
    }

    /**
     * Get all events that are occurring today.
     * @return A QuerySnapShot of all events that are occurring on the day of the query.
     */
    @Override
    public Task<QuerySnapshot> getTodayEvents() {
        return eventCollection
                .whereEqualTo("date", java.time.LocalDate.now())
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
                .whereEqualTo("date", date)
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



    /* Delete functions (mostly needed for testing, but will also be useful eventually. */
    /**
     * Delete an event by calling the generic delete method
     * with the correct table name.
     *
     * @param e - Event to delete from the database
     */
    public void delete(Event e) {
        this.delete("events", e.getEventName());
    }

    /**
     * Delete a document from a specified collection within the FireStore.
     * @param collection - Name of collection document is being deleted from.
     * @param document - Name of document to be deleted.
     */
    private void delete(String collection, String document) {
        db.collection(collection).document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
