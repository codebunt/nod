package settings;

import java.util.ArrayList;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import play.Application;
import play.Configuration;
import play.GlobalSettings;
import play.Play;

public class Global extends GlobalSettings {
	private static MorphiaObject morphiaObject;
	
	private static MongoClient mongoClient;

	private static String appName;

	public static MorphiaObject getMorphiaObject() {
		if (morphiaObject == null) {
			throw new IllegalStateException("Morphia not initialized.Call initMongo()");
		}
		return morphiaObject;
	}
	@Override
	public void onStart(Application arg0) {
		initMongo(Play.application().configuration());
		super.onStart(arg0);
	}
	
	protected void initMongo(Configuration config) {
		Morphia morphia = new Morphia();
		MongoClient mongo = ensureMongoCLient();
		Datastore ds = morphia.createDatastore(mongo , config.getString("mongo.db"));
		ds.ensureIndexes();
		morphiaObject = new MorphiaObject(mongo, morphia, ds);
	}
	
	protected MongoClient ensureMongoCLient() {
		if(mongoClient == null) {
			Configuration config = Play.application().configuration();
			String serversStr = config .getString("mongo.servers");
			String[] serversArr = serversStr.split(",");
			ArrayList<ServerAddress> serverObjs = new ArrayList<ServerAddress>();
			for (int i = 0; i < serversArr.length; i++) {
				serverObjs.add(new ServerAddress(config.getString("mongo." + serversArr[i] + ".host"),
						config.getInt("mongo." + serversArr[i] + ".port")));
			}
			mongoClient = new MongoClient(serverObjs);
		}
		return mongoClient;
	}
}
