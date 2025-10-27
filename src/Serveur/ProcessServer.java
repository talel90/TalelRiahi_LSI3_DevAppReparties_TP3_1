package Serveur;



import java.io.*;
import java.net.*;

public class ProcessServer extends Thread {
    private final Socket socket;

    public ProcessServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ) {
            while (true) {
                Object obj = in.readObject();
                if (obj == null) break;

                Object[] data = (Object[]) obj;
                double a = (double) data[0];
                String op = (String) data[1];
                double b = (double) data[2];
                double result = 0;

                switch (op) {
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "*": result = a * b; break;
                    case "/":
                        if (b == 0) {
                            out.writeObject("Erreur : division par zéro !");
                            continue;
                        }
                        result = a / b;
                        break;
                    default:
                        out.writeObject("Erreur : opérateur invalide !");
                        continue;
                }

                // Appel correct à la méthode du serveur
                Serveur.incrementerCompteur();

                out.writeObject("Résultat = " + result);
                out.flush();
            }
        } catch (EOFException e) {
            System.out.println("Client déconnecté : " + socket.getInetAddress());
        } catch (Exception e) {
            System.out.println("Erreur avec client " + socket.getInetAddress() + " : " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
