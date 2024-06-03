import java.awt.*;

public class Smile extends GraphicalObjects {
    private int radius;

    public Smile(int x, int y, int radius, Color fillColor, Color strokeColor) {
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
        // Draw face
        g.setColor(fillColor);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(strokeColor);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);

        // Draw eyes
        int eyeRadius = radius / 5;
        int eyeXOffset = radius / 3;
        int eyeYOffset = radius / 3;
        g.setColor(Color.BLACK);
        g.fillOval(x - eyeXOffset - eyeRadius, y - eyeYOffset - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);
        g.fillOval(x + eyeXOffset - eyeRadius, y - eyeYOffset - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);

        // Draw mouth
        int mouthWidth = radius / 2;
        int mouthHeight = radius / 3;
        int mouthYOffset = radius / 4;
        g.drawArc(x - mouthWidth, y + mouthYOffset, 2 * mouthWidth, mouthHeight, 0, -180);
    }
}
