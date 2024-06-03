import java.awt.*;

public class Star extends GraphicalObjects {
    private int outerRadius;
    private final int innerRadius;
    private final int numberOfPoints;

    public Star(int x, int y, int outerRadius, int innerRadius, int numberOfPoints, Color fillColor, Color strokeColor) {
        super(x, y, fillColor, strokeColor);
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.numberOfPoints = numberOfPoints;
    }

    public int getOuterRadius() {
        return outerRadius;
    }

    public void setProperties(int size, Color fillColor, Color strokeColor) {
        this.outerRadius = size;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public void draw(Graphics g) {
        int[] xPoints = new int[numberOfPoints * 2];
        int[] yPoints = new int[numberOfPoints * 2];
        double angle = Math.PI / numberOfPoints;

        for (int i = 0; i < numberOfPoints * 2; i++) {
            int radius = (i % 2 == 0) ? outerRadius : innerRadius;
            xPoints[i] = (int) (x + radius * Math.cos(i * angle));
            yPoints[i] = (int) (y - radius * Math.sin(i * angle));
        }

        g.setColor(fillColor);
        g.fillPolygon(xPoints, yPoints, numberOfPoints * 2);
        g.setColor(strokeColor);
        g.drawPolygon(xPoints, yPoints, numberOfPoints * 2);
    }
}

