/**
 * 
 */
package settings;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class MorphiaObject {
	private MongoClient mongo;
	private Morphia morphia;
	private Datastore datastore;

	/**
	 * @param mongo
	 * @param morphia
	 * @param datastore
	 */
	MorphiaObject(MongoClient mongo, Morphia morphia, Datastore datastore) {
		super();
		this.mongo = mongo;
		this.morphia = morphia;
		this.datastore = datastore;
	}

	public MongoClient getMongo() {
		return mongo;
	}

	public Morphia getMorphia() {
		return morphia;
	}

	public Datastore getDatastore() {
		return datastore;
	}
}