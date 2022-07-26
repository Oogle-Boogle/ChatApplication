package Client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.System.out;

public class Client extends JFrame {

    //Declare components

    private JLabel heading = new JLabel("Chat Application");
    public static JTextArea messageArea = new JTextArea();
    public static JTextField messageInput = new JTextField();
    //Lets have a nice font for the users to read.
    private Font font = new Font("Roboto", Font.ITALIC, 18);

    String title = "Chat Application - V 0.1";

    public void createGUI() {
        out.println("Called the GUI.");
        //Here we customise and create the JFrame
        this.setTitle(title);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null); //Sets the location center when first loaded
        this.setDefaultCloseOperation(Client.EXIT_ON_CLOSE);

        //Components
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        heading.setIcon(new ImageIcon("assets/chatIcon.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Frame layout
        this.setLayout(new BorderLayout());

        //Adding components to the frame
        this.add(heading, BorderLayout.NORTH);
        this.add(messageArea, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);

        //Stops the chat area to be tampered with by the user.
        messageArea.setEditable(false);

        this.setVisible(true);
        //Call the events once the frame has loaded.
        handleEvents();
    }

    /**
     * We use the keyListener here so we can send the message to the user by clicking enter
     * To get the keyCode we simply use e.getKeyCode
     */
    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10 || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    out.println("User has clicked enter.");
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me: " +contentToSend + "\n");
                    out.println(contentToSend);
                    out.flush();
                    //Once the message has been sent we clear the inputChat
                   messageInput.setText("");
                   messageInput.requestFocus();
                }
            }
        });
    }

    public static void main(String[] args) {

    }
}
