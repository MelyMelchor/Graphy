import java.awt.*;

public class Triangle extends GraphicalObjects {
    private int base, height;

    public Triangle(int x, int y, int base, int height, Color fillColor, Color strokeColor) {
        super(x, y, fillColor, strokeColor);
        this.base = base;
        this.height = height;
    }

    public int getBase() {
        return base;
    }

    public void setProperties(int size, Color fillColor, Color strokeColor) {
        this.base = size;
        this.height = size / 2;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public void draw(Graphics g) {
        int[] xPoints = {x, x - base / 2, x + base / 2};
        int[] yPoints = {y - height / 2, y + height / 2, y + height / 2};
        g.setColor(fillColor);
        g.fillPolygon(xPoints, yPoints, 3);
        g.setColor(strokeColor);
        g.drawPolygon(xPoints, yPoints, 3);
    }
}
