package basicFunction;

public enum DrawingMode {
    FREEHAND("Freehand"),
    STRAIGHT_LINE("Straight Line"),
    RECTANGLE("Rectangle"),
    ELLIPSE("Ellipse"),
    SPECIAL_SHAPE("Special Shape"),
    POLYGON("Polygon"),

    MOVE("Move");


    private final String label;

    DrawingMode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
