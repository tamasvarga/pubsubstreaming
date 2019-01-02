package Tools;

import com.google.cloud.datastore.*;
import org.json.JSONObject;

public class DataStoreConnector {
    private final Datastore datastore;

    public DataStoreConnector() {
        datastore = DatastoreOptions.getDefaultInstance().getService(); // Authorized Datastore service
    }

    public String SaveEventDecision(String eventid, String action) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Decision");
        IncompleteKey key = keyFactory.newKey(eventid);
        FullEntity<IncompleteKey> incBookEntity = Entity.newBuilder(key)  // Create the Entity
                .set("eventid", eventid)           // Add Property ("author", book.getAuthor())
                .set("action", action)
                .build();
        Entity bookEntity = datastore.add(incBookEntity); // Save the Entity
        return bookEntity.getKey().getName();
    }

    public String ReadUserData(Integer userId, String datakey) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("UserAggregatedCache");
        Entity cacheEntry = datastore.get(keyFactory.newKey(userId.toString()));
        String jsonString = cacheEntry.getString("JSON");

        return new JSONObject(jsonString).getString(datakey);
    }

    public String Dump(String msg) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Dump");
        IncompleteKey key = keyFactory.newKey();
        FullEntity<IncompleteKey> incBookEntity = Entity.newBuilder(key)  // Create the Entity
                .set("msg", msg)           // Add Property ("author", book.getAuthor())
                .build();
        Entity bookEntity = datastore.add(incBookEntity); // Save the Entity
        return bookEntity.getKey().getName();
    }
}
