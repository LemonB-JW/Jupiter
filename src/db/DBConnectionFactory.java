package db;
import db.mongodb.MongoDBConnection;
import db.mysql.MySQLConnection;

public class DBConnectionFactory {
	private static final String DEFAULT_DB = "mysql"; // if we need to use MongoDB, then change this constant
//	private static final String DEFAULT_DB = "mongodb";
	public static DBConnection getDBConnection(String db) {
		switch (db) {
		case "mysql":
			return new MySQLConnection();		
		case "mongodb":
			return new MongoDBConnection(); // return new MongoDBConnection();
		default:
			throw new IllegalArgumentException("Invalid db" + db);
		}
	}

	public static DBConnection getDBConnection() {
		return getDBConnection(DEFAULT_DB);   // 如果不指定参数则返回MySQL类型的数据库
	}

}
