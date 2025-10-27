package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Connecté au serveur " + host + ":" + port);
            System.out.println("Format : nombre opérateur nombre (ex: 5 + 2)");
            System.out.println("Tapez 'exit' pour quitter.");

            while (true) {
                System.out.print("> ");
                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("exit")) break;

                String[] parts = line.split(" ");
                if (parts.length != 3) {
                    System.out.println("Entrée invalide !");
                    continue;
                }

                try {
                    double a = Double.parseDouble(parts[0]);
                    String op = parts[1];
                    double b = Double.parseDouble(parts[2]);

                    Object[] data = {a, op, b};
                    out.writeObject(data);
                    out.flush();

                    Object response = in.readObject();
                    System.out.println(response);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : nombres invalides !");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
