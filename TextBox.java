import java.awt.*;

public class TextBox extends GraphicalObjects {
    private String text;
    private int width, height;

    public TextBox(int x, int y, String text, Color fillColor, Color strokeColor) {
        super(x, y, fillColor, strokeColor);
        this.text = text;
        this.width = text.length() * 10; // Approximate width based on text length
        this.height = 20; // Fixed height for simplicity
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.width = text.length() * 10;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(fillColor);
        g.fillRect(x, y - height, width, height);
        g.setColor(strokeColor);
        g.drawRect(x, y - height, width, height);
        g.setColor(Color.BLACK);
        g.drawString(text, x + 5, y - 5);
    }

    public void setProperties(String text, int x, int y, int width, int height, Color fillColor, Color strokeColor) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }
}
