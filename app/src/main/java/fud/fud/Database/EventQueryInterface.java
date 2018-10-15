package fud.fud.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

/**
 * A query interface for all supported event queries.
 */
public interface EventQueryInterface {
    Task<QuerySnapshot> getLocalEvents(double distanceToUser, double withInMileageRange);

    Task<QuerySnapshot> getCuisineFilteredFoodEvents(String cuisineType) throws InterruptedException;

    Task<QuerySnapshot> getPriceFilteredEvents(double maximumPrice);

    Task<QuerySnapshot> getPriceFilteredEvents(double minimumPrice, double maximumPrice);

    Task<QuerySnapshot> getAllEvents();

    Task<QuerySnapshot> getTodayEvents();

    Task<QuerySnapshot> getDateSpecificEvents(Date date);

    Task<QuerySnapshot> getEventObect();
}
