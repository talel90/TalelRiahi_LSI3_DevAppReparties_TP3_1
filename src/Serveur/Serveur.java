package Serveur;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Serveur {
    // compteur global partagé
    private static final AtomicInteger compteurGlobal = new AtomicInteger(0);

    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré sur le port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress());
                // Démarre un thread pour ce client
                ProcessServer process = new ProcessServer(clientSocket);
                process.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // méthode publique et statique, accessible depuis ProcessServer
    public static synchronized void incrementerCompteur() {
        int valeur = compteurGlobal.incrementAndGet();
        System.out.println("Nombre total d’opérations traitées : " + valeur);
    }
}
