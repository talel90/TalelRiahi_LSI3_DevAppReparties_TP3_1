package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000; // même port que le serveur

        try (Socket socket = new Socket(host, port)) {
            System.out.println("✅ Connecté au serveur sur le port " + port);

            // Flux de communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            // Affiche les messages d'accueil du serveur
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            String message;
            while (true) {
                System.out.print("Vous : ");
                message = scanner.nextLine();

                out.println(message); // Envoie au serveur

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("👋 Déconnexion...");
                    break;
                }

                // Réponse du serveur
                String response = in.readLine();
                if (response == null) break;
                System.out.println(response);
            }

            socket.close();
            System.out.println("Fin de la communication.");

        } catch (IOException e) {
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
        }
    }
}
