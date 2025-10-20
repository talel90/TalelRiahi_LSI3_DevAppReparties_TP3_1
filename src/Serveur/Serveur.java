package Serveur;

import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) {
        int clientCount = 0; // Compteur de clients

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Serveur démarré sur le port 5000...");

            while (true) {
                Socket socket = serverSocket.accept();
                clientCount++;
                System.out.println("Client " + clientCount + " connecté : " + socket.getInetAddress());

                // On crée un thread pour gérer ce client
                int clientId = clientCount; // pour l’utiliser dans le thread
                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        out.println("Bienvenue Client " + clientId + " !");
                        out.println("Tapez 'exit' pour quitter.");

                        String message;
                        while ((message = in.readLine()) != null) {
                            System.out.println("Client " + clientId + " : " + message);

                            if (message.equalsIgnoreCase("exit")) {
                                out.println("Déconnexion du serveur...");
                                break;
                            }

                            out.println("Serveur → Client " + clientId + " : " + message.toUpperCase());
                        }

                        socket.close();
                        System.out.println("Client " + clientId + " déconnecté.");

                    } catch (IOException e) {
                        System.out.println("Erreur avec le client " + clientId + " : " + e.getMessage());
                    }
                }).start(); // Démarre le thread
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
