package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import Interface.ServeurIntf;

public class ClientRMI {
	ServeurIntf Serveur;
	
	public ClientRMI() throws MalformedURLException, RemoteException, NotBoundException {
		Serveur = (ServeurIntf)Naming.lookup("//localhost/RmiServer");
	}
	
	public static void main(String args[]) throws Exception {
        ClientRMI chatClient = new ClientRMI();     
        Scanner sc = new Scanner(System.in);
        PollingThread pt = new PollingThread(chatClient);
        pt.start();
        System.out.println("Connecté au serveur...");
        String message = sc.nextLine();
        while(!message.equals("$")){
        	chatClient.Serveur.sendMessage(message);
        	pt.addMessage(message);
        	message = sc.nextLine();
        }
        sc.close();
        System.out.println("A quitté le chat...");
        
        
        
    }
}