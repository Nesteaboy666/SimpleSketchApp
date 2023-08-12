package basicFunction;

import java.awt.*;

public enum DrawingColor {
    RED("Red", Color.RED),
    GREEN("Green", Color.GREEN),
    BLUE("Blue", Color.BLUE),
    YELLOW("Yellow", Color.YELLOW),
    ORANGE("Orange", Color.ORANGE),
    PURPLE("Purple", new Color(128, 0, 128)),
    BLACK("Black", Color.BLACK),
    WHITE("White", Color.WHITE);

    private final String name;
    private final Color color;

    DrawingColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}