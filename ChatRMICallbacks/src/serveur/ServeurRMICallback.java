package serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import intf.ClientIntfCallback;
import intf.ServeurIntfCallback;

public class ServeurRMICallback extends UnicastRemoteObject implements ServeurIntfCallback{
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<ClientIntfCallback> listeClients = new ArrayList<ClientIntfCallback>();
	private static ArrayList<String> messageList = new ArrayList<String>(); 
	
	public ServeurRMICallback() throws RemoteException{
		super();
		listeClients = new ArrayList<ClientIntfCallback>();
	}
	
	public static void main(String args[]) throws Exception {
		try { 
			LocateRegistry.createRegistry(1099);
	    } catch (RemoteException e) {
	    	System.out.println("Registre inaccessible...");
	    }
		ServeurRMICallback chatServeur = new ServeurRMICallback();
		Naming.rebind("//localhost/RmiServer", chatServeur);
		System.out.println("Serveur pr�t!");
		while(true){
			
		}  
	}

	
	/**
	 * Utilis� par le client pour envoyer un message.
	 */
	public void sendMessage(String message) throws RemoteException {
		messageList.add(message);
		
		//	Envoi des messages aux clients
		ArrayList<Integer> clientIDToRemove = new ArrayList<Integer>();
		for(int i = 0; i < listeClients.size(); i++) {
			try {
				listeClients.get(i).getMessagesList(messageList);
			} catch (RemoteException e) {
	 			System.out.println("Client deconnect� !");
	 			clientIDToRemove.add(i);
			}
		}
		for(int i = 0; i < clientIDToRemove.size(); i++) {
			listeClients.remove((int) clientIDToRemove.get(i));
		}
		
	}
	
	/**
	 * Utilis� par un client lors de sa connexion.
	 */
	public void connect(ClientIntfCallback client) throws RemoteException {
		listeClients.add(client);
		System.out.println("Nouveau chatteur connect� ! Nombre de chatteurs connect�s : " + listeClients.size());
	}

	
	
	
}