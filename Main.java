import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Main extends JFrame implements MouseListener, ActionListener {
    private ArrayList<GraphicalObjects> objects = new ArrayList<>();
    private String selectedShape = "Circle";
    private Draw currentDraw = null;

    public Main() {
        this.setTitle("GraphicalObjects");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(this);

        JMenuBar menuBar = new JMenuBar();

        // Shape Menu
        JMenu shapeMenu = new JMenu("Shape");
        String[] shapes = {"Circle", "Rectangle", "Triangle", "Star", "Smile", "Draw", "TextBox"};
        for (String shape : shapes) {
            JMenuItem menuItem = new JMenuItem(shape);
            menuItem.addActionListener(this);
            shapeMenu.add(menuItem);
        }

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveObjects());

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> loadObjects());

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);

        // Options Menu
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(e -> clearObjects());

        optionsMenu.add(clearItem);

        // Properties Menu
        JMenu propertiesMenu = new JMenu("Properties");
        JMenuItem propertiesItem = new JMenuItem("Change Properties");
        propertiesItem.addActionListener(e -> openPropertiesDialog());

        propertiesMenu.add(propertiesItem);

        menuBar.add(shapeMenu);
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(propertiesMenu);
        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (GraphicalObjects object : objects) {
            object.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Color fillColor = new Color((int) (Math.random() * 0x1000000));
        Color strokeColor = Color.BLACK;

        if (!"Draw".equals(selectedShape)) {
            GraphicalObjects obj = null;
            switch (selectedShape) {
                case "Circle":
                    obj = new Circle(x, y, 50, fillColor, strokeColor);
                    break;
                case "Rectangle":
                    obj = new Rectangle(x, y, 100, 50, fillColor, strokeColor);
                    break;
                case "Triangle":
                    obj = new Triangle(x, y, 100, 50, fillColor, strokeColor);
                    break;
                case "Star":
                    obj = new Star(x, y, 50, 25, 5, fillColor, strokeColor);
                    break;
                case "Smile":
                    obj = new Smile(x, y, 50, fillColor, strokeColor);
                    break;
                case "TextBox":
                    String text = JOptionPane.showInputDialog(this, "Enter text:");
                    if (text != null && !text.isEmpty()) {
                        obj = new TextBox(x, y, text, fillColor, strokeColor);
                    }
                    break;
            }
            if (obj != null) {
                objects.add(obj);
                repaint();
            }
        } else {
            if (currentDraw == null) {
                currentDraw = new Draw(Color.BLACK, Color.BLACK);
                this.addMouseMotionListener(currentDraw);
                objects.add(currentDraw);
            } else {
                this.removeMouseMotionListener(currentDraw);
                currentDraw = null;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedShape = e.getActionCommand();
        if ("Draw".equals(selectedShape) && currentDraw != null) {
            this.removeMouseMotionListener(currentDraw);
            currentDraw = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void clearObjects() {
        objects.clear();
        if (currentDraw != null) {
            this.removeMouseMotionListener(currentDraw);
            currentDraw = null;
        }
        repaint();
    }

    private void saveObjects() {
        try (FileOutputStream fos = new FileOutputStream("shapes.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(objects);
            JOptionPane.showMessageDialog(this, "Objects saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save objects.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadObjects() {
        try (FileInputStream fis = new FileInputStream("shapes.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            objects = (ArrayList<GraphicalObjects>) ois.readObject();
            JOptionPane.showMessageDialog(this, "Objects loaded successfully!");
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load objects.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openPropertiesDialog() {
        if (objects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No objects to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GraphicalObjects selectedObject = objects.get(objects.size() - 1);
        JDialog dialog = new JDialog(this, "Change Properties", true);
        dialog.setLayout(new GridLayout(6, 2));

        JLabel xLabel = new JLabel("X:");
        JTextField xField = new JTextField(String.valueOf(selectedObject.x));

        JLabel yLabel = new JLabel("Y:");
        JTextField yField = new JTextField(String.valueOf(selectedObject.y));

        JLabel widthLabel = new JLabel("Width:");
        JTextField widthField = new JTextField();

        JLabel heightLabel = new JLabel("Height:");
        JTextField heightField = new JTextField();

        JLabel sizeLabel = new JLabel("Size:");
        JTextField sizeField = new JTextField();

        if (selectedObject instanceof TextBox) {
            widthField.setText(String.valueOf(((TextBox) selectedObject).getText().length() * 10));
            heightField.setText(String.valueOf(((TextBox) selectedObject).getText().length() * 2));
        } else {
            sizeField.setText(String.valueOf(getObjectSize(selectedObject)));
        }

        JLabel fillColorLabel = new JLabel("Fill Color:");
        JButton fillColorButton = new JButton();
        fillColorButton.setBackground(selectedObject.getFillColor());
        fillColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose Fill Color", fillColorButton.getBackground());
            if (newColor != null) {
                fillColorButton.setBackground(newColor);
            }
        });

        JLabel strokeColorLabel = new JLabel("Stroke Color:");
        JButton strokeColorButton = new JButton();
        strokeColorButton.setBackground(selectedObject.getStrokeColor());
        strokeColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose Stroke Color", strokeColorButton.getBackground());
            if (newColor != null) {
                strokeColorButton.setBackground(newColor);
            }
        });

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> {
            int newX = Integer.parseInt(xField.getText());
            int newY = Integer.parseInt(yField.getText());
            int newWidth = Integer.parseInt(widthField.getText());
            int newHeight = Integer.parseInt(heightField.getText());
            int newSize = Integer.parseInt(sizeField.getText());
            Color newFillColor = fillColorButton.getBackground();
            Color newStrokeColor = strokeColorButton.getBackground();

            if (selectedObject instanceof TextBox) {
                ((TextBox) selectedObject).setProperties(((TextBox) selectedObject).getText(), newX, newY, newWidth, newHeight, newFillColor, newStrokeColor);
            } else {
                setObjectProperties(selectedObject, newSize, newFillColor, newStrokeColor);
            }

            dialog.dispose();
            repaint();
        });

        dialog.add(xLabel);
        dialog.add(xField);
        dialog.add(yLabel);
        dialog.add(yField);
        dialog.add(widthLabel);
        dialog.add(widthField);
        dialog.add(heightLabel);
        dialog.add(heightField);
        dialog.add(sizeLabel);
        dialog.add(sizeField);
        dialog.add(fillColorLabel);
        dialog.add(fillColorButton);
        dialog.add(strokeColorLabel);
        dialog.add(strokeColorButton);
        dialog.add(new JLabel()); // empty label for spacing
        dialog.add(applyButton);

        dialog.pack();
        dialog.setVisible(true);
    }

    private int getObjectSize(GraphicalObjects object) {
        if (object instanceof Circle) {
            return ((Circle) object).getRadius();
        } else if (object instanceof Rectangle) {
            return ((Rectangle) object).getWidth();
        } else if (object instanceof Triangle) {
            return ((Triangle) object).getBase();
        } else if (object instanceof Star) {
            return ((Star) object).getOuterRadius();
        } else if (object instanceof Smile) {
            return ((Smile) object).getRadius();
        } else if (object instanceof Draw) {
            return 0; // Draw object doesn't have a size property
        } else if (object instanceof TextBox) {
            return ((TextBox) object).getText().length() * 10; // Approximate size based on text length
        }
        return 0;
    }

    private void setObjectProperties(GraphicalObjects object, int size, Color fillColor, Color strokeColor) {
        if (object instanceof Circle) {
            ((Circle) object).setProperties(size, fillColor, strokeColor);
        } else if (object instanceof Rectangle) {
            ((Rectangle) object).setProperties(size, fillColor, strokeColor);
        } else if (object instanceof Triangle) {
            ((Triangle) object).setProperties(size, fillColor, strokeColor);
        } else if (object instanceof Star) {
            ((Star) object).setProperties(size, fillColor, strokeColor);
        } else if (object instanceof Smile) {
            ((Smile) object).setProperties(size, fillColor, strokeColor);
        } else if (object instanceof Draw) {
            ((Draw) object).setProperties(fillColor, strokeColor); // Set colors only
        } else if (object instanceof TextBox) {
            ((TextBox) object).setProperties(((TextBox) object).getText(), object.x, object.y, size, size / 2, fillColor, strokeColor); // Set colors and size
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
