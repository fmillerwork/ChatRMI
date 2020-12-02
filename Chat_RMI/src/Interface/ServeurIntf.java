package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServeurIntf extends Remote{

	public String getLastMessage(int lastMessageIndex) throws RemoteException;
	
	public void sendMessage(String message, String pseudo) throws RemoteException;
	
	
}