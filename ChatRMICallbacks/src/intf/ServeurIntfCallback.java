package intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurIntfCallback extends Remote{

	//public ArrayList<String> getMessageList() throws RemoteException;
	
	public void sendMessage(String message) throws RemoteException;
	
	public void connect(ClientIntfCallback client) throws RemoteException;
	
	
}