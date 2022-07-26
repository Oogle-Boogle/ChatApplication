package Server;

import Client.Client;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static Client.Client.messageArea;

public class Engine {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    int port = 7777;

    /**
     * Constructor..
     */
    public Engine() {
        try {
            System.out.println("Gets here");
            server = new ServerSocket(port);
            System.out.println("Server socket: " + server);
            while (true) {
                socket = server.accept();
                System.out.println("Doesn't reach accept");
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());

                startReading();
                startWriting();

                System.out.println("Server has successfully loaded and is now ready to be connected to.");
            }
        } catch (Exception e) {
            System.out.println("Error loading the chat Engine.");
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
                        System.exit(0);
                    }
//                    System.out.println("Server: " + msg + "\n");
                    messageArea.setAlignmentX(messageArea.RIGHT_ALIGNMENT);
                    messageArea.append("Server: " + msg + "\n");
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
                    //Now lets close the socket if we type exit.
                    if (content.equalsIgnoreCase("exit")) {
                        socket.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(write).start();
    }

    public static void main(String[] args) {
        System.out.println("The server is starting...");
         new Engine();
    }
}
