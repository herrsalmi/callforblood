package open.callforblood.utils;

public class Lock {

	public static boolean serverLock = false;
	
	public Lock() {
	}

	public static void lockServer(){
		serverLock = true;
	}
	
	public static void unlockServer(){
		serverLock = false;
	}
	
	public static boolean isServerLocked(){
		return serverLock;
	}
	
	
	
}
