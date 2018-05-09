package business;
import java.lang.Exception;
/// use this for fast implementation at singleton
public class Singleton {
	protected static Object singleton;
	@SuppressWarnings("static-access")
	public Singleton() throws Exception {
		/// saved my instance
		if(this.singleton != null)
			throw new Exception("Cannot initialize a Singleton because its has started, use 'ClassName.getSingleton()'"
					+ "	for get instance of your object");
		this.singleton = this;
	}
}
