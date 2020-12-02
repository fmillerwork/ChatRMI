package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import intf.ClientIntfCallback;
import intf.ServeurIntfCallback;

public class ClientRMICallback extends UnicastRemoteObject implements ClientIntfCallback, Serializable{
	private static final long serialVersionUID = 1L;
	
	ServeurIntfCallback Serveur;
	String pseudo;
	Scanner sc ;
	boolean isConnected = false;
	
	public ClientRMICallback() throws MalformedURLException, RemoteException, NotBoundException {
		sc = new Scanner(System.in);
		tryConnection();
	}
	
	public static void main(String args[]) throws Exception {
        ClientRMICallback chatClient = new ClientRMICallback(); 
        
        if(chatClient.isConnected) {
	        System.out.println("Connecté au chat.");
	        System.out.println("Entrer un pseudo : ");
	        chatClient.pseudo = chatClient.sc.nextLine();
	        System.out.println("Bonjour " + chatClient.pseudo + " bienvenu sur le serveur.");
	        System.out.println("Commandes du chat :\n\t/leave => Quitter le chat");
	        
	        System.out.println("Connecté au serveur...");
	        chatClient.Serveur.connect((ClientIntfCallback) chatClient);
	        
	        String message = chatClient.sc.nextLine();
	        while(!message.equals("/leave")){
	        	try {
	        		chatClient.Serveur.sendMessage((ClientIntfCallback) chatClient, message, chatClient.pseudo);
	        	}catch (RemoteException e) {
					System.out.println("Connexion au serveur interrompue");
				}
	        	message = chatClient.sc.nextLine();
	        }
	        chatClient.Serveur.disconnect((ClientIntfCallback) chatClient, chatClient.pseudo);
	        chatClient.sc.close();
	        System.out.println("A quitté le chat...");
        }
        
    }
	/**
	 * Tente une connexion au serveur et propose une reconnexion en cas d'échec.
	 */
	private void tryConnection() {
		try{
			Serveur = (ServeurIntfCallback)Naming.lookup("//localhost/RmiServer");
			isConnected = true;
		}catch(RemoteException e) {
			System.out.println("Serveur offline.");
			System.out.println("Tenter une nouvelle connexion ? o/n");
			
			String choice = sc.nextLine();
	        while(!choice.equals("o") && !choice.equals("n")){
	        	System.out.println("Pardon ?");
	        	choice = sc.nextLine();
	        }
	        
			if(choice.equals("o")) {
				tryConnection();
			}else {
				System.out.println("Fermeture du chat.");
			}
	        
		} catch (MalformedURLException e) {
			System.out.println("Mauvaise URL...");
			tryConnection();
		} catch (NotBoundException e) {
			System.out.println("Not assiocated binding...");
			tryConnection();
		}
	}
 
	/**
	 * Utilisé par le serveur pour envoyer la liste des messages au client.
	 */
	public void getLastMessage(String message) throws RemoteException {
		System.out.println(message);
	}
}