package basicFunction;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class DrawingCanvas extends Canvas{

    private List<Shape> shapes = new ArrayList<>();

    private List<Point> points = new ArrayList<>(); // List to store points for freehand drawing

    private Shape selectedShape;

    private Point lastMousePosition;

    private DrawingMode drawingMode = DrawingMode.FREEHAND;

    private Color drawingColor = DrawingColor.BLACK.getColor();
    private int x0, y0, x1, y1;

    private Shape clipboard;

    private List<Shape> undoStack = new ArrayList<>();
    private List<Shape> redoStack = new ArrayList<>();
    public DrawingCanvas() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                x0 = e.getX();
                y0 = e.getY();
                lastMousePosition = e.getPoint();

                if (drawingMode == DrawingMode.MOVE) {
                    for (int i = shapes.size() - 1; i >= 0; i--) {
                        if (shapes.get(i).contains(e.getX(), e.getY())) {
                            selectedShape = shapes.get(i);
                            lastMousePosition = e.getPoint();
                            break;
                        }
                    }
                } else {
                    shapes.add(new Shape(drawingMode, drawingColor, x0, y0, x0, y0, new ArrayList<>()));
                }

                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();

                if (drawingMode == DrawingMode.FREEHAND && !points.isEmpty()) {
                    shapes.add(createFreehandShape());
                    points.clear();
                } else {
                    shapes.get(shapes.size() - 1).setEndPoint(x1, y1);
                }

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - lastMousePosition.x;
                int deltaY = e.getY() - lastMousePosition.y;
                lastMousePosition = e.getPoint();

                if (drawingMode == DrawingMode.MOVE) {
                    moveSelectedShape(deltaX, deltaY);
                } else if (drawingMode == DrawingMode.FREEHAND) {
                    points.add(new Point(e.getX(), e.getY()));
                    repaint();
                } else {
                    x1 = e.getX();
                    y1 = e.getY();
                    repaint();
                }
            }
        });
    }

    public void moveSelectedShape(int deltaX, int deltaY) {
        if (selectedShape != null) {
            selectedShape.move(deltaX, deltaY);
            repaint();
        }
    }

    private Shape createFreehandShape() {
        return new Shape(DrawingMode.FREEHAND, drawingColor, 0, 0, 0, 0, new ArrayList<>(points));
    }

    public void setDrawingMode(DrawingMode mode) {
        drawingMode = mode;
    }

    public void setDrawingColor(DrawingColor color) {
        drawingColor = color.getColor();
    }

    public void paint(Graphics g) {
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }


    public void cutSelectedShape() {
        if (selectedShape != null) {
            clipboard = selectedShape;
            undoStack.add(selectedShape);
            shapes.remove(selectedShape);
            selectedShape = null;
            repaint();
        }
    }

    public void pasteClipboard() {
        if (clipboard != null) {
            int width = clipboard.getX1() - clipboard.getX0();
            int height = clipboard.getY1() - clipboard.getY0();
            shapes.add(new Shape(clipboard.getMode(), clipboard.getColor(),
                    lastMousePosition.x, lastMousePosition.y,
                    lastMousePosition.x + width,
                    lastMousePosition.y + height,
                    clipboard.getPoints()));
            repaint();
        }
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            undoStack.add(shapes.remove(shapes.size() - 1));
            repaint();
        }
    }

    public void redo() {
        if (!undoStack.isEmpty()) {
            shapes.add(undoStack.remove(undoStack.size() - 1));
            repaint();
        }
    }


}