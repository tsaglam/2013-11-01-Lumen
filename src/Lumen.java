import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This is Lumen, a visual something. It's a short piece of code that paints psychedelic color
 * effects on a frame.
 * @Author Timur Saglam
 */
public class Lumen {

    public static void main(String[] args) {
        Lumen lumen = new Lumen(0);
        lumen.start();
    }

    private JFrame frame;
    private JPanel panel;
    private Dimension dimension;
    private double brushSizeShift;
    private double currentColor;
    private int shiftCounter;
    private int speed;
    private int mode;

    /**
     * Simple constructor.
     * @param mode specifies the color mode. Use 0, 1 or 2.
     */
    public Lumen(int mode) {
        if (mode < 0 || mode > 2) {
            throw new IllegalArgumentException("Mode can only be 0, 1 or 2. It can't be " + mode + ".");
        }
        this.mode = mode;
        int[] speeds = { 18750, 45000, 75000 };
        speed = speeds[mode];
        currentColor = 1.0;
        brushSizeShift = 0.1;
        buildGUI();
    }

    public void start() {
        double brushSize = 0;
        while (true) {
            for (int n = 0; n <= 50; n++) {
                brushSize += brushSizeShift;
                paint(brushSize);
            }
        }
    }

    private void paint(double baseSize) {
        for (int i = 1; i <= speed; i++) {
            Graphics g = panel.getGraphics();
            int[] circleSize = { (int) (Math.random() * 10) + 5 + (int) baseSize, 4, 3 };
            int x = (int) (dimension.width * Math.random());
            int y = (int) (dimension.height * Math.random());
            g.setColor(getShiftingColor());
            g.fillOval(x, y, circleSize[mode], circleSize[mode]);                                                                              // circle
        }
    }

    private Color getShiftingColor() {
        float h = 0;
        shiftCounter++; // shifting color scheme
        if (shiftCounter >= speed) {
            shiftCounter = 0;
            currentColor = currentColor + 0.0125; // +0.05
            if (currentColor >= 1.0)
                currentColor = 0.0;
        }
        h = (float) currentColor;
        return Color.getHSBColor(h, (float) (Math.random() / 2 + 0.25), (float) (Math.random() / 2 + 0.25));
    }

    private void buildGUI() {
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        try {
            Thread.sleep(500); // Without this the dimensions are not up to date.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dimension = panel.getSize();
    }
}