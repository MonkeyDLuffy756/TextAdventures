import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private String description;
    private Map<String, Location> exits; 
    private List<Item> items;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addExit(String direction, Location location) {
        exits.put(direction.toLowerCase(), location);
    }

    public Location getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }
}



