package serveur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
		System.out.println("Serveur prêt!"); 
	}

	
	/**
	 * Utilisé par le client pour envoyer un message.
	 */
	public void sendMessage(ClientIntfCallback sender, String message, String pseudo) throws RemoteException {
		String newMessage = pseudo + " : " + message;
		messageList.add(newMessage);
		
		// Envoi des messages aux clients
		for(int i = 0; i < listeClients.size(); i++) {
			try {
				if(!sender.equals(listeClients.get(i))) {
					listeClients.get(i).getLastMessage(newMessage);
				}
			} catch (RemoteException e) {
				System.out.println("Client deconnecté !");
				listeClients.remove(i);
			}
		}
		
	}
	
	/**
	 * Utilisé par un client lors de sa connexion.
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	public void connect(ClientIntfCallback client) throws RemoteException{
		listeClients.add(client);
	}

	/**
	 * Utilisé par le le client lors de sa deconnexion.
	 */
	public void disconnect(ClientIntfCallback client, String pseudo) throws RemoteException {
		// Envoi des messages aux clients
		listeClients.remove(client);
		for(int i = 0; i < listeClients.size(); i++) {
			try {
				if(!client.equals(listeClients.get(i))) {
					listeClients.get(i).getLastMessage(pseudo + " s'est déco !");
				}
			} catch (RemoteException e) {
				System.out.println("Client deconnecté !");
				listeClients.remove(i);
			}
		}
	}
		
	
}