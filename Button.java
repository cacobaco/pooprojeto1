import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Button extends Actor {
    
    private final String text;
    private final Color color;
    private final Font font;
    
    public Button(int width, int height, String text, String font, int size) {
        setImage(new GreenfootImage(width, height));
        this.text = text;
        this.color = Color.BLACK;
        this.font = new Font(font, false, false, size);
        getImage().setColor(this.color);
        getImage().setFont(this.font);
        getImage().drawString(this.text, size/2, size);
    }

    public Button(int width, int height, String text, Color color, String font, int size) {
        setImage(new GreenfootImage(width, height));
        this.text = text;
        this.color = color;
        this.font = new Font(font, false, false, size);
        getImage().setColor(this.color);
        getImage().setFont(this.font);
        getImage().drawString(this.text, size/2, size);
    }

    public Button(int width, int height, String text, Color color, String font, boolean bold, boolean italic, int size) {
        setImage(new GreenfootImage(width, height));
        this.text = text;
        this.color = color;
        this.font = new Font(font, bold, italic, size);
        getImage().setColor(this.color);
        getImage().setFont(this.font);
        getImage().drawString(this.text, size/2, size);
    }

    // draws a rectangle border around the button
    public void drawBorder(Color color, int tickness) {
        GreenfootImage image = getImage();
        image.setColor(color);

        for (int i = 0; i < tickness; i++) {
            image.drawRect(i, i, image.getWidth() - i * 2 - 1, image.getHeight() - i * 2 - 1);
        }
    }
    
    // returns true if button is pressed with m1
    public boolean isPressed() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        return mouse != null && mouse.getButton() == 1 && mouse.getActor() == this;
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
