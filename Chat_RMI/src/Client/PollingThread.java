package Client;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PollingThread extends Thread{

	private ClientRMI chatClient;
	private int lastMessageIndex = -1; // indice du dernier message connu du client
	
	
	public PollingThread(ClientRMI chatClient){
		this.chatClient = chatClient;
	}
		
	public void run(){
		while(true) {
			try {
				String serverMessage;
				serverMessage = chatClient.Serveur.getLastMessage(lastMessageIndex); // Récupération de la liste des messages serveur non lus.
				if(serverMessage != null) {
					lastMessageIndex ++;
					System.out.println(serverMessage);
				}
			} catch (RemoteException e) {
				System.out.println("Erreur de connexion au serveur...");
				break;	// On décide de terminer le processus si une erreur serveur survient
			}
		}
	}

	/**
	 * Incrémente lastMessageIndex. Utilisé juste après l'écriture d'un message par le client.  
	 */
	public void newMessage() {
		lastMessageIndex ++;
	}
	
	
}

