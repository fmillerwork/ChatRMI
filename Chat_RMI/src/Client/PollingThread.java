package Client;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PollingThread extends Thread{

	private ClientRMI chatClient;
	private ArrayList<String> displayedMessages;
	
	
	public PollingThread(ClientRMI chatClient){
		this.chatClient = chatClient;
		displayedMessages = new ArrayList<String>();
	}
		
	public void run(){
		while(true) {
			try {
				ArrayList<String> serverMessages = chatClient.Serveur.getMessageList(); // R�cup�ration de la liste des messages serveur.
				
				if(displayedMessages.size() == 0) {	//Aucun message stock� chez le client.
					for(int i = 0; i < serverMessages.size();i++) {
						displayedMessages.add(serverMessages.get(i));
						System.out.println(serverMessages.get(i));
			        }
				}else {
					for(int i = 0; i < serverMessages.size() - displayedMessages.size() ;i++) {
						displayedMessages.add(serverMessages.get(i + displayedMessages.size()-1));
						System.out.println(serverMessages.get(i + displayedMessages.size()-1));
			        }
				}
			} catch (RemoteException e) {
				System.out.println("Erreur de connexion au serveur...");
				break;	// On d�cide de terminer le processus si une erreur serveur survient
			}
		}
	}

	public void addMessage(String message) {
		displayedMessages.add(message);
	}
	
	
}

