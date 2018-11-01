package fud.fud.Database;

import fud.fud.Models.Event;

/**
 * Class for keeping track of keys for database.
 * All keys should be stored here a final static
 * strings.
 */
class KeyStore {
    static String EVENT_DATE;
    static {
        try {
            EVENT_DISTANCE = Event.class.getField("date").getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    // Used for multi-purpose methods to allow for
    // specifying which type we're using
    static final int EVENT_ID = 0;

    static final String EVENT_TABLE_NAME = "events";

    // Event class fields
    static String EVENT_DISTANCE;

    static {
        try {
            EVENT_DISTANCE = Event.class.getField("distanceToUser").getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static String EVENT_CUISINE_TYPE;

    static {
        try {
            EVENT_CUISINE_TYPE = Event.class.getField("cuisineType").getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static String EVENT_PRICE;

    static {
        try {
            EVENT_PRICE = Event.class.getField("price").getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
