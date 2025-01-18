import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    private JTextArea displayArea;     // Shows game output
    private JTextField inputField;     // Where user types commands
    private Game game;                 // Our game logic

    public GameWindow() {
        super("My Text Adventure");

        // 1) Ask for the player’s name via a simple dialog
        String playerName = JOptionPane.showInputDialog(
            this,
            "Enter your character name:",
            "Welcome",
            JOptionPane.QUESTION_MESSAGE
        );

        if (playerName == null || playerName.trim().isEmpty()) {
            // If user cancels or leaves empty, default to "Hero"
            playerName = "Hero";
        }

        // 2) Create the Game with the given name
        game = new Game(playerName);

        // 3) Set up the window layout
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout());

        // 4) Create a text area for displaying game text
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // 5) Create a text field for input
        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);

        // 6) Add an action listener to process commands on Enter
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserInput();
            }
        });

        // 7) Show the initial welcome text from the game
        displayArea.setText(game.getWelcomeMessage());

        // 8) Make the window visible
        setVisible(true);
    }

    /**
     * Grabs the command from inputField, processes it via the game,
     * and appends the response to the displayArea.
     */
    private void handleUserInput() {
        if (game.isGameOver()) {
            // If the game is already over, ignore further input
            return;
        }

        String command = inputField.getText().trim();
        inputField.setText("");

        // Display the command the user just typed
        displayArea.append("\n> " + command);

        // Process the command in our game logic
        String response = game.processCommand(command);

        // Show the game’s response
        displayArea.append("\n" + response);

        // If the game ended after this command (e.g., final boss killed)
        if (game.isGameOver()) {
            displayArea.append("\n[Game has ended. Close the window to exit.]");
            // Optionally disable input
            inputField.setEnabled(false);
        }
    }
}
