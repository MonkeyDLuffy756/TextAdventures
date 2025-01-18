import java.util.Random;

public class Game {
    private Player player;

    // Locations
    private Location startLocation;
    private Location secondLocation;

    // Skeleton state - the final boss
    private boolean skeletonAlive = true;
    private int skeletonHealth = 20;

    private boolean gameOver = false; // track if the game has ended

    public Game(String playerName) {
        // Create the Player
        player = new Player(playerName, 100);

        // Create Locations
        startLocation = new Location("Start Room", "A small room with stone walls.");
        secondLocation = new Location("Hallway", "A narrow corridor dimly lit by torches.");

        // Link the rooms
        startLocation.addExit("east", secondLocation);
        secondLocation.addExit("west", startLocation);

        // Create some items
        Item key = new Item("Key", "A small rusty key.");
        Item torch = new Item("Torch", "A wooden torch that still has some fuel.");

        // Place items in the Start Room
        startLocation.addItem(key);
        startLocation.addItem(torch);

        // Set the player's starting location
        player.setCurrentLocation(startLocation);
    }

    /**
     * Called at the very beginning. We’ll display this in the GUI's text area.
     */
    public String getWelcomeMessage() {
        return "Welcome, " + player.getName() + "! Your journey begins now.\n"
             + "Type commands like 'go east', 'look', 'take key', 'inventory', or 'quit' to exit.";
    }

    /**
     * Called each time the user inputs a command in GameWindow.
     * We parse the command and return the resulting text.
     */
    public String processCommand(String input) {
        // If the game is already over, do nothing
        if (gameOver) {
            return "[The game has ended.]";
        }

        // Convert input to lowercase for easier checks
        input = input.trim().toLowerCase();

        // We'll build up a response in a StringBuilder
        StringBuilder response = new StringBuilder();
        Location current = player.getCurrentLocation();

        // 1) Quit
        if (input.equals("quit")) {
            response.append("Thanks for playing!");
            gameOver = true;
        }

        // 2) Movement (e.g., "go east")
        else if (input.startsWith("go ")) {
            String direction = input.substring(3).trim();
            Location nextLocation = current.getExit(direction);

            if (nextLocation != null) {
                player.setCurrentLocation(nextLocation);
                response.append("You move " + direction + " to the " + nextLocation.getName());

                // Check if we've just entered the Hallway & skeleton is still alive
                if (nextLocation == secondLocation && skeletonAlive) {
                    // We append the encounter text
                    response.append("\n\n").append(encounterSkeleton());
                }
            } else {
                response.append("You can't go that way!");
            }
        }

        // 3) Look
        else if (input.equals("look")) {
            if (current.getItems().isEmpty()) {
                response.append("You don't see any items here.");
            } else {
                response.append("You see the following items:");
                for (Item item : current.getItems()) {
                    response.append("\n- ").append(item.getName());
                }
            }
        }

        // 4) Take an item (e.g., "take key")
        else if (input.startsWith("take ")) {
            String itemName = input.substring(5).trim();

            Item foundItem = null;
            for (Item i : current.getItems()) {
                if (i.getName().toLowerCase().equals(itemName)) {
                    foundItem = i;
                    break;
                }
            }

            if (foundItem != null) {
                current.removeItem(foundItem);
                player.addItem(foundItem);
                response.append("You picked up the ").append(foundItem.getName()).append(".");
            } else {
                response.append("There's no ").append(itemName).append(" here.");
            }
        }

        // 5) Check inventory
        else if (input.equals("inventory")) {
            if (player.getInventory().isEmpty()) {
                response.append("You don't have any items.");
            } else {
                response.append("You are carrying:");
                for (Item i : player.getInventory()) {
                    response.append("\n- ").append(i.getName());
                }
            }
        }

        // If the player is currently in the hallway facing a live skeleton, 
        // they might type "attack skeleton" or "run" without moving. 
        // We can handle that too. (Optional extra)
        else if (skeletonAlive && current == secondLocation 
                 && (input.equals("attack skeleton") || input.equals("run"))) {
            // If we are physically in secondLocation and skeleton is alive,
            // we handle the combat action here. 
            response.append(handleSkeletonCombat(input));
        }

        // 6) Unknown command
        else {
            response.append("I don't understand that command.");
        }

        return response.toString();
    }

    /**
     * Called automatically when the player enters secondLocation and skeletonAlive is true.
     * Returns a short string describing the skeleton encounter (but does NOT block).
     */
    private String encounterSkeleton() {
        // We'll simply announce that the skeleton is here, 
        // and instruct the user to "attack skeleton" or "run".
        return "A Skeleton stands before you, rattling its bones menacingly!\n"
             + "It is the final boss of this dungeon!\n"
             + "Type 'attack skeleton' to fight, or 'run' to flee back to the Start Room.";
    }

    /**
     * Handles the "attack skeleton" or "run" actions once the player is already
     * in the hallway with the skeleton. 
     */
    private String handleSkeletonCombat(String input) {
        StringBuilder response = new StringBuilder();
        Random random = new Random();

        if (input.equals("attack skeleton")) {
            // Player attacks skeleton with random damage 1-5
            int damageToSkeleton = random.nextInt(5) + 1;
            skeletonHealth -= damageToSkeleton;
            response.append("You strike the Skeleton for ")
                    .append(damageToSkeleton).append(" damage!\n");

            // Check if skeleton is dead
            if (skeletonHealth <= 0) {
                skeletonAlive = false;
                response.append("The Skeleton collapses into a pile of bones!\n");
                // Drop Rusty Sword
                Item rustySword = new Item("Rusty Sword", "An old sword, slightly damaged but still usable.");
                secondLocation.addItem(rustySword);
                response.append("The Skeleton drops a Rusty Sword on the ground.\n");
                
                // Announce game cleared and end
                response.append("You have defeated the final boss and cleared the game. Congratulations!");
                gameOver = true;
                return response.toString();
            }

            // Skeleton fights back with random damage 1-3
            int damageToPlayer = random.nextInt(3) + 1;
            player.setHealth(player.getHealth() - damageToPlayer);
            response.append("The Skeleton hits you for ")
                    .append(damageToPlayer).append(" damage!\n");

            // Check if player is dead
            if (player.getHealth() <= 0) {
                response.append("You have been slain by the Skeleton...\n");
                response.append("Game Over.");
                gameOver = true;
            }
        }
        else if (input.equals("run")) {
            // Flee back to the start location
            player.setCurrentLocation(startLocation);
            response.append("You flee from the Skeleton and return to the Start Room!");
        }

        return response.toString();
    }

    /** 
     * Lets the GUI check if the game has ended, so it can stop taking input. 
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
