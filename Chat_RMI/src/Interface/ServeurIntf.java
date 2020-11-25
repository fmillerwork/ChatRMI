package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServeurIntf extends Remote{

	public ArrayList<String> getMessageList() throws RemoteException;
	
	public void sendMessage(String message) throws RemoteException;
	
	
}