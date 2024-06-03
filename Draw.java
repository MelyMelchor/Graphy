import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Draw extends GraphicalObjects implements MouseMotionListener {
    private final ArrayList<Point> points = new ArrayList<>();

    public Draw(Color fillColor, Color strokeColor) {
        super(0, 0, fillColor, strokeColor);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(strokeColor);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        points.add(e.getPoint());
        e.getComponent().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setProperties(Color fillColor, Color strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }
}
