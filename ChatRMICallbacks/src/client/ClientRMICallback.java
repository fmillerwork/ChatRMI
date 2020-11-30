package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import intf.ClientIntfCallback;
import intf.ServeurIntfCallback;

public class ClientRMICallback implements ClientIntfCallback, Serializable{
	private static final long serialVersionUID = 1L;
	
	ServeurIntfCallback Serveur;
	ArrayList<String> displayedMessages = new ArrayList<String>();
	
	public ClientRMICallback() throws MalformedURLException, RemoteException, NotBoundException {
		Serveur = (ServeurIntfCallback)Naming.lookup("//localhost/RmiServer");
	}
	
	public static void main(String args[]) throws Exception {
        ClientRMICallback chatClient = new ClientRMICallback();  
        
        Scanner sc = new Scanner(System.in);
        //new PollThread(chatClient).start();
        System.out.println("Connecté au serveur...");
        chatClient.Serveur.connect((ClientIntfCallback) chatClient);
        
        String message = sc.nextLine();
        while(!message.equals("$")){
        	try {
        		chatClient.Serveur.sendMessage(message);
        		chatClient.displayedMessages.add(message);
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
	public void getMessagesList(ArrayList<String> newMessagesListe) throws RemoteException {
		
		if(displayedMessages.size() == 0) {	//Aucun message stocké chez le client.
			for(int i = 0; i < newMessagesListe.size();i++) {
				displayedMessages.add(newMessagesListe.get(i));
				System.out.println(newMessagesListe.get(i));
	        }
		}else {
			for(int i = 0; i < newMessagesListe.size() - displayedMessages.size() ;i++) {
				displayedMessages.add(newMessagesListe.get(i + displayedMessages.size()-1));
				System.out.println(newMessagesListe.get(i + displayedMessages.size()-1));
	        }
		}
	}
}