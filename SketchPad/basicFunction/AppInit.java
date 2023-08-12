package basicFunction;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppInit extends Frame {

    private DrawingCanvas canvas;

    public AppInit() {
        setSize(800, 600);
        setupUI();
        canvas = new DrawingCanvas();
        add(canvas, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setFocusable(true);
    }

    private void setupUI() {
        Panel controlPanel = new Panel();
        controlPanel.setLayout(new GridLayout(3, 1)); // Divide into 3 rows

        // Row 1: Drawing Modes
        Panel modePanel = new Panel();
        modePanel.setLayout(new FlowLayout());

        Label modeLabel = new Label("Modes:");
        modeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14)); // Set font to bold
        modePanel.add(modeLabel);

        DrawingMode[] modes = DrawingMode.values();
        for (DrawingMode mode : modes) {
            Button button = new Button(mode.getLabel());
            button.addActionListener(e -> canvas.setDrawingMode(mode));
            modePanel.add(button);
        }
        controlPanel.add(modePanel);

        // Row 2: Drawing Colors
        Panel colorPanel = new Panel();
        colorPanel.setLayout(new FlowLayout());

        Label colorLabel = new Label("Colours:");
        colorLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14)); // Set font to bold
        colorPanel.add(colorLabel);

        DrawingColor[] colours = DrawingColor.values();
        for (DrawingColor color : colours) {
            Button button = new Button(color.getName());
            button.addActionListener(e -> canvas.setDrawingColor(color));
            colorPanel.add(button);
        }
        controlPanel.add(colorPanel);

        // Row 3: Buttons for Move, Cut, and Paste
        //Button moveButton = new Button("Move");
        //moveButton.addActionListener(e -> canvas.setDrawingMode(DrawingMode.MOVE));
        //controlPanel.add(moveButton);
        //add(controlPanel, BorderLayout.NORTH);
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());

        Button moveButton = new Button("Move");
        moveButton.addActionListener(e -> canvas.setDrawingMode(DrawingMode.MOVE));
        buttonPanel.add(moveButton);

        Button cutButton = new Button("Cut");
        cutButton.addActionListener(e -> canvas.cutSelectedShape());
        buttonPanel.add(cutButton);

        Button pasteButton = new Button("Paste");
        pasteButton.addActionListener(e -> canvas.pasteClipboard());
        buttonPanel.add(pasteButton);

        Button undoButton = new Button("Undo");
        undoButton.addActionListener(e -> canvas.undo());
        buttonPanel.add(undoButton);

        Button redoButton = new Button("Redo");
        redoButton.addActionListener(e -> canvas.redo());
        buttonPanel.add(redoButton);


        controlPanel.add(buttonPanel);

        add(controlPanel, BorderLayout.NORTH);

    }

    public static void main(String[] args) {
       AppInit app = new AppInit();
       app.setVisible(true);
   }
}
