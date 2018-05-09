package business;

public class IOManager{
	private static IOManager instance;
	public IOManager(){
		instance =this;
	}
	public static IOManager getSingleton() {
		return instance;
	}
	public void print(Object obj) {
		System.out.println(obj);
	}
}
