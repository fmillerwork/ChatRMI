package intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurIntfCallback extends Remote{

	//public ArrayList<String> getMessageList() throws RemoteException;
	
	public void sendMessage(ClientIntfCallback sender, String message, String pseudo) throws RemoteException;
	
	public void connect(ClientIntfCallback client, String pseudo) throws RemoteException;
	
	public void disconnect(ClientIntfCallback client, String pseudo) throws RemoteException;
	
	
}