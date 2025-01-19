Text Adventure Game
Welcome to the Text Adventure Game! This is a Java-based interactive text adventure where the player explores rooms, interacts with items, and faces a final boss. The game demonstrates fundamental programming concepts and provides a fun, interactive experience.

Features
Exploration: Move between rooms using commands like go east and go west. Each room has unique descriptions and items.
Inventory System: Pick up items using take [item], and view your inventory with the inventory command.
Combat: Encounter and fight the Skeleton, the final boss. Use commands like attack skeleton or run during combat.
Dynamic Gameplay: Players name their character at the start. The game ends when the Skeleton is defeated or the player dies.
How to Play
Start the Game:

Run the Main.java file.
Enter your character's name when prompted.
Game Commands:

go [direction]: Move between rooms (e.g., go east).
look: View the items in the current room.
take [item name]: Pick up an item (e.g., take key).
inventory: View your inventory.
attack skeleton: Fight the Skeleton in the Hallway.
run: Escape to the Start Room during combat.
quit: Exit the game.
Winning the Game: Defeat the Skeleton (final boss) to clear the game. Upon victory, the Skeleton drops a Rusty Sword, and you'll receive a congratulations message.

Losing the Game: If your health drops to 0 during combat, the game ends.

Game Structure
Main: Entry point for the game.
Game: Contains the main game logic, including input handling and combat mechanics.
Player: Manages the player's name, health, location, and inventory.
Location: Represents rooms in the game with items and exits.
Item: Represents objects that can be picked up and used.
GameWindow: A Swing-based GUI for the game.
Technical Details
Language: Java
Key Features: Object-Oriented Design, Randomized Combat, Swing GUI
How to Run
Clone the repository:

git clone https://github.com/MonkeyDLuffy756/TextAdventures.git
cd TextAdventures

Compile and run the project:

javac Main.java
java Main

Play the game by following on-screen prompts!

Future Enhancements
Add more rooms and puzzles for a larger game world.
Introduce additional enemies with unique mechanics.
Implement save/load functionality.
Add sound effects and music.
Author
Created by Javier Morales. Feel free to reach out or open an issue for suggestions or feedback.
