import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingApp {
    private JFrame frame;
    private JPanel drawingPanel;
    private Color currentColor = Color.BLACK;
    private int lineWidth = 3;
    private ArrayList<Line> lines = new ArrayList<>();
    private Line currentLine;

    /**
     * @wbp.parser.entryPoint
     */
    public DrawingApp() {
        frame = new JFrame("Рисование пером");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setLayout(new BorderLayout());

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                for (Line line : lines) {
                    g2d.setColor(line.getColor());
                    g2d.setStroke(new BasicStroke(line.getWidth()));
                    g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
                }
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentLine = new Line(e.getX(), e.getY(), e.getX(), e.getY(), currentColor, lineWidth);
                lines.add(currentLine);
            }
        });
        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentLine.setX2(e.getX());
                currentLine.setY2(e.getY());
                drawingPanel.repaint();
            }
        });

        frame.getContentPane().add(drawingPanel, BorderLayout.CENTER);
        drawingPanel.setLayout(new BorderLayout(0, 0));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Настройки");

        JMenu colorMenu = new JMenu("Цвет");
        JMenuItem redColor = new JMenuItem("Красный");
        JMenuItem greenColor = new JMenuItem("Зеленый");
        JMenuItem blueColor = new JMenuItem("Синий");
        JMenuItem blackColor = new JMenuItem("Чёрный");

        redColor.addActionListener(e -> currentColor = Color.RED);
        greenColor.addActionListener(e -> currentColor = Color.GREEN);
        blueColor.addActionListener(e -> currentColor = Color.BLUE);
        blackColor.addActionListener(e -> currentColor = Color.BLACK);

        colorMenu.add(blackColor);
        colorMenu.add(redColor);
        colorMenu.add(greenColor);
        colorMenu.add(blueColor);

        JMenu thicknessMenu = new JMenu("Толщина");
        JMenuItem thinLine = new JMenuItem("Тонкая");
        JMenuItem mediumLine = new JMenuItem("Средняя");
        JMenuItem thickLine = new JMenuItem("Толстая");

        thinLine.addActionListener(e -> lineWidth = 1);
        mediumLine.addActionListener(e -> lineWidth = 3);
        thickLine.addActionListener(e -> lineWidth = 5);

        thicknessMenu.add(thinLine);
        thicknessMenu.add(mediumLine);
        thicknessMenu.add(thickLine);

        menu.add(colorMenu);

        menu.add(thicknessMenu);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
        // Добавление кнопок в меню
        JMenuItem clearCanvas = new JMenuItem("Очистить");
        JMenuItem eraser = new JMenuItem("Стерка");

        clearCanvas.addActionListener(e -> {
            lines.clear(); // Очистка списка линий
            drawingPanel.repaint(); // Обновление панели
        });

        eraser.addActionListener(e -> {
            currentColor = drawingPanel.getBackground(); // Устанавливаем цвет фона
            lineWidth = 10; // Ставим широкую "линии" для стирания
        });

        // Добавляем эти кнопки в меню
        menu.add(clearCanvas);
        menu.add(eraser);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DrawingApp::new);
    }
}

class Line {
    private int x1, y1, x2, y2;
    private Color color;
    private int width;

    public Line(int x1, int y1, int x2, int y2, Color color, int width) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.width = width;
    }

    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    public Color getColor() { return color; }
    public int getWidth() { return width; }

    public void setX2(int x2) { this.x2 = x2; }
    public void setY2(int y2) { this.y2 = y2; }
}
