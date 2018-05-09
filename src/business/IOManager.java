package business;

public class IOManager extends Singleton {
	public IOManager() throws Exception {
		super();
	}
	public void print(Object obj) {
		System.out.println(obj);
	}
	public static IOManager getSingleton() {
		return (IOManager)singleton;
	}
}
