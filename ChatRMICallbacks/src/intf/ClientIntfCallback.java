package intf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientIntfCallback extends Remote{
	public void getLastMessage(String message) throws RemoteException;
	
}
