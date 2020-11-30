package intf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientIntfCallback extends Remote{
	public void getMessagesList(ArrayList<String> liste) throws RemoteException;
	
}
