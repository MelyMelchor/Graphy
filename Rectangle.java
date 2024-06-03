import java.awt.*;

public class Rectangle extends GraphicalObjects {
    private int width, height;

    public Rectangle(int x, int y, int width, int height, Color fillColor, Color strokeColor) {
        super(x, y, fillColor, strokeColor);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setProperties(int size, Color fillColor, Color strokeColor) {
        this.width = size;
        this.height = size / 2;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(fillColor);
        g.fillRect(x, y, width, height);
        g.setColor(strokeColor);
        g.drawRect(x, y, width, height);
    }
}

