package Serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Interface.ServeurIntf;

public class ServeurRMI extends UnicastRemoteObject implements ServeurIntf{
	private static final long serialVersionUID = 1L;

	private ArrayList<String> messageList = new ArrayList<String>();
	
	public ServeurRMI() throws RemoteException{
		super();
	}
	
	 public static void main(String args[]) throws Exception {
		 try { 
			 LocateRegistry.createRegistry(1099); 
	     } catch (RemoteException e) {
	     }
		 ServeurRMI chatServeur = new ServeurRMI();
	     Naming.rebind("//localhost/RmiServer", chatServeur);
	     System.out.println("Serveur prêt!");
	 }

	@Override
	public ArrayList<String> getMessageList() throws RemoteException {
		return messageList;
	}

	@Override
	public void sendMessage(String message, String pseudo) throws RemoteException {
		messageList.add(pseudo + " : " + message);
	}

	
	
	
}