package Client;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static Client.Client.messageArea;

public class Loader extends JFrame {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    String username = "Default";

    String ip = "0.0.0.0";
    int port = 7777;

    public Loader() {
        try {
            System.out.println("Sending connection request...");
            socket = new Socket(ip, port);
            System.out.println("Connected established to: " + ip + port);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

            //Load up the JFrame from the JFrame class that we've created.
            new Client().createGUI();
            System.out.println("Loaded up the JFrame client.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        Runnable read = () -> {
            System.out.println("Reader has started.");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equalsIgnoreCase("exit")) {
                        JOptionPane.showMessageDialog(this, "Server terminated the chat");
                        Client.messageInput.setEnabled(false);
                        socket.close();
                        System.exit(0);
                    }
                    messageArea.setAlignmentX(messageArea.RIGHT_ALIGNMENT);
                    Client.messageArea.append("Server: " + msg + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(read).start();
    }

    public void startWriting() {
        System.out.println("Writer has started.");
        Runnable write = () -> {
            try {
                while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(write).start();
    }

    public static void main(String[] args) {
        System.out.println("Client is loading...");
        new Loader();
    }
}
