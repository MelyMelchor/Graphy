import java.awt.*;
import java.io.Serializable;

public abstract class GraphicalObjects implements Serializable {
    protected int x, y;
    protected Color fillColor, strokeColor;

    public GraphicalObjects(int x, int y, Color fillColor, Color strokeColor) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public String getText() {
        return ""; // Default implementation, to be overridden by subclasses with text
    }

    public abstract void draw(Graphics g);
}
