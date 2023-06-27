import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Wardrobes {
    private String name;
    private Set<Clothing>cloth;

    public Wardrobes(String name) {
        this.name = name;
        this.cloth = new HashSet<Clothing>();
    }
    public Clothing search(String category, String colour) {
        for(Clothing c : cloth) {
            if (c.getCategory().equals(category)){
                if (c.getColour().equals(colour)) {
                    return c;
                }
            }
        }
        return null;
    }
    public Set<Clothing> getClothing() {
        return  cloth;

    }

    public void add(String category, String colour, String size) {
        cloth.add(new Clothing(category, colour, size));
    }


    public String getName() {
        return name;
    }
    public String getAllCategories() {
        Set<String> categories = new LinkedHashSet<String>();

        for (Clothing c : cloth) {
            categories.add(c.getCategory());
        }

        return String.join(", ", categories);
    }
}
