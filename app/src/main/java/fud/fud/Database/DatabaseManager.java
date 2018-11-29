package fud.fud.Database

import android.util.Log

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

import java.util.Date

import fud.fud.Models.Event

import android.content.ContentValues.TAG

/**
 * Implement CRUD operations for firestore database.
 * NOTE: All database tasks should be handled by a
 * DatabaseManager object.
 *
 * TODO: Make this a singleton since no parallel operation
 */
class DatabaseManager
/**
 * Initialize the database instance.
 *
 */
(private val db: FirebaseFirestore) : IEventQuery {

    /**
     * Get all events from the event collection.
     * @return A QuerySnapShot of all events within the event collection.
     */
    override val allEvents: Task<QuerySnapshot>
        get() = getAll(KeyStore.EVENT_TABLE_NAME)

    /**
     * Get all events that are occurring today.
     * @return A QuerySnapShot of all events that are occurring on the day of the query.
     */
    override val todayEvents: Task<QuerySnapshot>
        get() = eventCollection
                .whereEqualTo(KeyStore.EVENT_DATE!!, java.time.LocalDate.now())
                .get()

    /*
        TODO: Implement this later when decided on uniqueness
        TODO: qualifier for events
     */
//    override val eventObect: Task<QuerySnapshot>
//        get() = null

    init {
        eventCollection = db.collection(KeyStore.EVENT_TABLE_NAME)
    }

    /**
     * Add an event by calling the generic add method
     * with the correct table name.
     *
     * @param e - Event to add to the database
     */
    fun add(e: Event) {
        this.add(e, KeyStore.EVENT_TABLE_NAME, e.eventName)
    }

    /**
     * Add an object to a specified collection within the FireStore.
     * @param obj - Object to add to the collection
     * @param collection - Name of collection object is being added to.
     * @param <T> - Generic type handling
    </T> */
    private fun <T> add(obj: T, collection: String, document: String?) {
        db.collection(collection)
                .document(document!!)
                .set(obj)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    /**
     * Get all objects in a collection.
     * @param collectionName - Name of the collection in which the objects are stored.
     * @return a list of all of the objects in the collection
     */
    private fun getAll(collectionName: String): Task<QuerySnapshot> {
        return db.collection(collectionName).get()
    }

    /**
     * Get all events within a specified distance.
     * @param distanceToUser - Distance of the event to the user's current position.
     * @param withInMaximumDistance - Maximum distance of event from the user.
     * @return A QuerySnapShot of all of the events within this distance.
     */
    override fun getLocalEvents(distanceToUser: Double, withInMaximumDistance: Double): Task<QuerySnapshot> {
        return eventCollection
                .whereGreaterThan(KeyStore.EVENT_DISTANCE, withInMaximumDistance)
                .get()
    }

    /**
     * Get all events of a specified cuisine type.
     * @param cuisineType - Type of cuisine user specified.
     * @return - A QuerySnapShot of all events with the specified
     * cuisine type.
     */
    override fun getCuisineFilteredFoodEvents(cuisineType: String): Task<QuerySnapshot> {

        return eventCollection
                .whereEqualTo(KeyStore.EVENT_CUISINE_TYPE, cuisineType)
                .get()
    }

    /**
     * Get all events up to a certain price.
     * @param maximumPrice - Maximum price desired by user.
     * @return - A QuerySnapShot of all events under the specified price.
     */
    override fun getPriceFilteredEvents(maximumPrice: Double): Task<QuerySnapshot> {
        return eventCollection
                .whereLessThanOrEqualTo(KeyStore.EVENT_PRICE, maximumPrice)
                .get()
    }

    /**
     * Get all events up to a certain price.
     * @param minimumPrice - Minimum price desired by user.
     * @param maximumPrice - Maximum price desired by user.
     * @return - A QuerySnapShot of all events within the specified price range.
     */
    override fun getPriceFilteredEvents(minimumPrice: Double, maximumPrice: Double): Task<QuerySnapshot> {
        return eventCollection
                .whereLessThanOrEqualTo(KeyStore.EVENT_PRICE, maximumPrice)
                .whereGreaterThanOrEqualTo(KeyStore.EVENT_PRICE, minimumPrice)
                .get()
    }

    /**
     * Get all events on a specified date
     * @param date - Date specified by user
     * @return A QuerySnapShot of all events on the date specified.
     */
    override fun getDateSpecificEvents(date: Date): Task<QuerySnapshot> {
        return eventCollection
                .whereEqualTo(KeyStore.EVENT_DATE!!, date)
                .get()
    }

    companion object {
        private var eventCollection: CollectionReference
    }
}
