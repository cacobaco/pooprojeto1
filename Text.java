import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Text extends Actor {
    
    private final String text;
    private final Color color;
    private final Font font;
    
    public Text(int width, int height, String text, String font, int size) {
        setImage(new GreenfootImage(width, height));
        this.text = text;
        this.color = Color.BLACK;
        this.font = new Font(font, false, false, size);
        getImage().setColor(this.color); 
        getImage().setFont(this.font);
        getImage().drawString(this.text, size/2, size);
    }

    public Text(int width, int height, String text, Color color, String font, int size) {
        setImage(new GreenfootImage(width, height));
        this.text = text;
        this.color = color;
        this.font = new Font(font, false, false, size);
        getImage().setColor(this.color); 
        getImage().setFont(this.font);
        getImage().drawString(this.text, size/2, size);
    }

    public Text(int width, int height, String text, Color color, String font, boolean bold, boolean italic, int size) {
        setImage(new GreenfootImage(width, height));
        this.text = text;
        this.color = color;
        this.font = new Font(font, bold, italic, size);
        getImage().setColor(this.color);
        getImage().setFont(this.font);
        getImage().drawString(this.text, size/2, size);
    }

    // getters and setters
    public String getText() {
        return this.text;
    }

    public Color getColor() {
        return this.color;
    }

    public Font getFont() {
        return this.font;
    }
    
}
