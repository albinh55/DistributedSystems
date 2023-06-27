public class Clothing {

    private String category;
    private String colour;
    private String size;


    public Clothing(String category, String colour, String size){
        this.category = category;
        this.colour = colour;
        this.size = size;

    }
    public String getCategory() {
        return category;
    }
    public String getColour() {
        return colour;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
}
