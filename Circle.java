import java.awt.*;

public class Circle extends GraphicalObjects {
    private int radius;

    public Circle(int x, int y, int radius, Color fillColor, Color strokeColor) {
        super(x, y, fillColor, strokeColor);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setProperties(int radius, Color fillColor, Color strokeColor) {
        this.radius = radius;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(fillColor);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(strokeColor);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
