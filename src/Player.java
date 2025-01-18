import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private Location currentLocation;

    private List<Item> inventory;

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<Item> getInventory() {
        return inventory;
    }
    public void addItem(Item item) {
        inventory.add(item);
    }
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }
}




