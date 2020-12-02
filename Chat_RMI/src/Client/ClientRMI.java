package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import Interface.ServeurIntf;

public class ClientRMI {
	ServeurIntf Serveur;
	Scanner sc;
	boolean isConnected = false;
	
	public ClientRMI() throws MalformedURLException, RemoteException, NotBoundException {
		sc = new Scanner(System.in);
		tryConnection();
	}
	
	public static void main(String args[]) throws Exception {
        ClientRMI chatClient = new ClientRMI(); 
        
        if(chatClient.isConnected) {
        	System.out.println("Connecté au chat.");
            System.out.println("Entrer un pseudo : ");
            String pseudo = chatClient.sc.nextLine();
            
            PollingThread pt = new PollingThread(chatClient);
            pt.start();
            
            System.out.println("Bonjour " + pseudo + " bienvenu sur le serveur.");
            System.out.println("Commandes du chat :\n\t/leave => Quitter le chat");
            
            String message = chatClient.sc.nextLine();
            while(!message.equals("/leave")){
            	try{
            		pt.newMessage();
            		chatClient.Serveur.sendMessage(message, pseudo);
            	}catch (RemoteException e) {
    				System.out.println("Connexion perdue...");
    				break;
    			}
            	message = chatClient.sc.nextLine();
            }
            
            chatClient.sc.close();
            System.out.println("A quitté le chat.");
        }
    }
	
	private void tryConnection() {
		try{
			Serveur = (ServeurIntf)Naming.lookup("//localhost/RmiServer");
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
	
	
}