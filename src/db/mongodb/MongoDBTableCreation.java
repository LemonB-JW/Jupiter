package db.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

public class MongoDBTableCreation {
	public static void main(String[] args) {
		MongoClient mongoclient = new MongoClient();
		MongoDatabase db = mongoclient.getDatabase(MongoDBUtil.DB_NAME);
		
		db.getCollection("users").drop();
		db.getCollection("items").drop();
		
		IndexOptions indexOptions = new IndexOptions().unique(true);
		db.getCollection("users").createIndex(new Document("user_id", 1), indexOptions); // 1 means ascending order, -1 means descending order
		db.getCollection("items").createIndex(new Document("item_id", 1), indexOptions); // 1 means ascending order, -1 means descending order
		
		db.getCollection("users").insertOne(new Document().append("first_name", "Xuan").append("last_name", "Wang").append("user_id", "1111").append("password", 123456));
		mongoclient.close();
		
		System.out.println("Import is done succesfully.");
		
	}
}
