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

	/**
	 * Utilisé par le client pour récupéré le dernier message non lu sur le serveur.
	 */
	public String getLastMessage(int lastMessageIndex) throws RemoteException {
		if(messageList.size() > lastMessageIndex + 1) {
			return messageList.get(lastMessageIndex + 1);
		}
		return null;
	}

	/**
	 * Utilisé par le client pour envoyer un message.
	 */
	public void sendMessage(String message, String pseudo) throws RemoteException {
		messageList.add(pseudo + " : " + message);
	}

	
	
	
}