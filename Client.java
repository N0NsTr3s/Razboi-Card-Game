import java.util.Objects;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




/**
 * The Client class represents a client that connects to a server and interacts with it.
 * It handles the communication with the server and updates the GUI accordingly.
 */
public class Client {
    private String host;
    private int port;
    private GUI ui;
    private boolean Flip;
    private static final String GAME_OVER = "objects closed";
    private static final String PRESS_ENTER = "cards in discard pile";
    private static final String WAR = "Tie! Time for war";
    private static final String CARD = "flipped";
    public String FlippedCard;
    public String response;
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.ui = new GUI();

    }

   
    

    public boolean flipCardPressed = false;


    public static String getHost(Scanner inputScanner, String defaultHost) {
        
        String answer = "0";

        return Objects.equals(answer, "0") ? defaultHost : answer;
    }

    


    
    /**
     * Starts the client and establishes a connection with the server.
     * Receives messages from the server and performs corresponding actions based on the received response.
     * If the response contains "GAME_OVER", the game is considered over and the loop breaks.
     * If the response contains "PRESS_ENTER", the client sends a "send card" message to the server.
     * If the response contains "WAR", the client sends a "send card" message to the server.
     * If the response contains "CARD", the client sends a "send card" message to the server and stores the flipped card.
     * If the response contains "Diamonds", "Hearts", "Clubs", or "Spades", the client updates the player's cards in the GUI.
     * 
     * @throws Exception if an error occurs during the execution of the method
     */
    public void start() {
        try (SocketConnection serverConnection = new SocketConnection(host, port)) {
    
            String input;
            
            while ((response = serverConnection.receiveMsgFromServer()) != null) {
                ui.log("\nServer: " + response);
    
                if (response.contains(GAME_OVER)) {
                    ui.log("Game over msg received from Server");
                    break;
                }
                if (response.contains(PRESS_ENTER)) {
                    input = "send card\n";
                    if (!Flip) {
                        // Wait for user to press the Flip Card button
                        while (!ui.isFlipCardPressed()) {
                            Thread.sleep(100);
                        }
                    }
                    serverConnection.sendMsgToServer(input);
                    resetFlipCardPressed();
                    ui.resetFlipCardPressed();
                    
                }
                if (response.contains(WAR)) {
                    input = "send card\n";
                    if (!Flip) {
                        // Wait for user to press the Flip Card button
                        while (!ui.isFlipCardPressed()) {
                            Thread.sleep(100);
                        }
                    }
                    serverConnection.sendMsgToServer(input);
                    resetFlipCardPressed();
                }
                resetFlipCardPressed();
                if (response.contains(CARD)) {
                    input = "send card\n";
                    if (!Flip) {
                        FlippedCard = response;
                    }
                    serverConnection.sendMsgToServer(input);
                    resetFlipCardPressed();
                }
                 if (response.contains("Diamonds") || response.contains("Hearts") || response.contains("Clubs") || response.contains("Spades")) {
                   
                
                    String[] cards = response.split(",");
                    GUI.setPlayer1Card(cards[0]);
                    GUI.setPlayer2Card(cards[1]);
                }
                    
            }
      
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());}
        }
        
    


    /**
     * Resets the flipCardPressed flag to false.
     */
    public void resetFlipCardPressed() {
    flipCardPressed = false;
    }


/**
 * The GUI class represents the graphical user interface for the card game.
 * It extends the JFrame class and provides various components such as labels,
 * buttons, and text fields for user interaction.
 */
class GUI extends JFrame {
    
    public static JLabel player1Card;
    public static JLabel player2Card;
    public JButton flipButton;
    public JTextField idField;
    public JTextField ServerField;
    public JPasswordField passwordField;
    public JButton loginButton;
    public  boolean loginSuccessful = false;
    public boolean flipCardPressed = false;

    
    public boolean isFlipCardPressed() {
    return flipCardPressed;
    }
    
    public void resetFlipCardPressed() {
    flipCardPressed = false;
    }

    public void reset() {
        // Clear fields
        player1Card.setText("Player 1 Card: ");
        player2Card.setText("Player 2 Card: ");
        // Reset state
        resetFlipCardPressed();
    }
    public void clearCredentialsFields() {
        idField.setText("");
        passwordField.setText("");
    }
    


    public GUI() {

        


        
        setTitle("Card Game");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 20.0;
        gbc.gridx = 5;
        
        
        player1Card = new JLabel("Player 1 Card: " );
        player2Card = new JLabel("Player 2 Card: " );
        flipButton = new JButton("Flip Card " );
        
        
        idField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        ServerField = new JTextField();
        log("Server: " + response);


        flipButton.setEnabled(false);
       

        loginButton.addActionListener(new ActionListener() {
            /**
             * Performs the action when the login button is pressed.
             * It checks if the entered ID and password are correct and enables the flip button if successful.
             * Otherwise, it displays an error message.
             *
             * @param e the ActionEvent object representing the button press event
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Login button pressed"); // Debugging line
                if (idField.getText().equals("javaproject123") && new String(passwordField.getPassword()).equals("javaproject123")) {
                    loginSuccessful = true;
                    System.out.println("Correct login details"); // Debugging line
                    clearCredentialsFields();
                    flipButton.setEnabled(true);
                    loginButton.setEnabled(false);
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid ID or password");
                }
            }
        });

        flipButton.addActionListener(new ActionListener() {
            /**
             * Invoked when the flip button is pressed.
             * Sets the flipCardPressed flag to true and processes the response.
             * If the response contains card information, it splits the response and sets the cards for player 1 and player 2.
             * 
             * @param e the ActionEvent object representing the button press event
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Flip button pressed"); // Debugging line
                flipCardPressed = true;
                
                if (response.contains("Diamonds") || response.contains("Hearts") || response.contains("Clubs") || response.contains("Spades")) {
                   
                
                    String[] cards = response.split(",");
                    setPlayer1Card(cards[0]);
                    setPlayer2Card(cards[1]);}
                    
            }
        });
        gbc.gridy = 0;
        add(player1Card, gbc);
        
        gbc.gridy = 1;
        add(player2Card, gbc);
        
        gbc.gridy = 2;
        add(flipButton, gbc);
        
        gbc.gridy = 3;
        add(loginButton, gbc);
        
        gbc.gridy = 4;
        add(idField, gbc);
        
        gbc.gridy = 5;
        add(passwordField, gbc);
        
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(ServerField, gbc);
        ServerField.setEditable(false);
        setVisible(true);
   
    }

    public static void setPlayer1Card(String card) {
        player1Card.setText("Player 1 Card: " + card);
    }

    public static void setPlayer2Card(String card) {
        player2Card.setText("Player 2 Card: " + card);
    }
    public void log(String message) {
        ServerField.setText(message);
    }

}
    public static void main(String[] args) {
        // Create only one Client instance
     
        Client client = new Client("localhost", 1337);
        client.start();

    }}
            


