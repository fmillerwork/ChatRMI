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
		sendToAll(sender, newMessage);
	}
	
	/**
	 * Utilisé par un client lors de sa connexion.
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	public void connect(ClientIntfCallback client, String pseudo) throws RemoteException{
		listeClients.add(client);
		sendToAll(client, pseudo + " s'est co !");
	}

	/**
	 * Utilisé par le le client lors de sa deconnexion.
	 */
	public void disconnect(ClientIntfCallback client, String pseudo) throws RemoteException {
		
		listeClients.remove(client);
		sendToAll(client, pseudo + " s'est déco !");
	}
	
	/**
	 * Envoi un messages aux clients sauf à celui en paramètre
	 * @param message
	 */
	private void sendToAll(ClientIntfCallback client, String message) {
		
		for(int i = 0; i < listeClients.size(); i++) {
			try {
				if(!client.equals(listeClients.get(i))) {
					listeClients.get(i).getLastMessage(message);
				}
			} catch (RemoteException e) {
				System.out.println("Client deconnecté !");
				listeClients.remove(i);
			}
		}
	}
	
}