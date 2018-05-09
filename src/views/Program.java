package views;

import business.FactoryManager;
import business.GlobalManager;

public class Program {
	public static int main(String[] args) {
		if(!FactoryManager.initBusiness())
			return GlobalManager.errorCode;
		return 0;
	}

}
