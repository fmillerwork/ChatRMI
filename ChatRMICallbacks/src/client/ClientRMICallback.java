package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import intf.ClientIntfCallback;
import intf.ServeurIntfCallback;

public class ClientRMICallback extends UnicastRemoteObject implements ClientIntfCallback, Serializable{
	private static final long serialVersionUID = 1L;
	
	ServeurIntfCallback Serveur;
	String pseudo;
	
	public ClientRMICallback() throws MalformedURLException, RemoteException, NotBoundException {
		Serveur = (ServeurIntfCallback)Naming.lookup("//localhost/RmiServer");
	}
	
	public static void main(String args[]) throws Exception {
        ClientRMICallback chatClient = new ClientRMICallback(); 
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Connecté au chat.");
        System.out.println("Entrer un pseudo : ");
        chatClient.pseudo = sc.nextLine();
        System.out.println("Bonjour " + chatClient.pseudo + " bienvenu sur le serveur.");
        System.out.println("Commandes du chat :\n\t/leave => Quitter le chat");
        
        System.out.println("Connecté au serveur...");
        chatClient.Serveur.connect((ClientIntfCallback) chatClient);
        
        String message = sc.nextLine();
        while(!message.equals("/leave")){
        	try {
        		chatClient.Serveur.sendMessage((ClientIntfCallback) chatClient, message, chatClient.pseudo);
        	}catch (RemoteException e) {
				System.out.println("Connexion au serveur interrompue");
			}
        	message = sc.nextLine();
        }
        sc.close();
        System.out.println("A quitté le chat...");
        
        
    }


	/**
	 * Utilisé par le serveur pour envoyer la liste des messages au client.
	 */
	public void getLastMessage(String message, String pseudo) throws RemoteException {
		System.out.println(pseudo + " : " + message);
	}
}