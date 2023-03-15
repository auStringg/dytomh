import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomScreenshot {

    private JFrame frame;
    private ImagePanel panel;
    private final Robot robot;

    private Rectangle captureRect;
    private boolean capturing;

    public CustomScreenshot() throws AWTException {
        robot = new Robot();
    }

    public static void main(String[] args) throws AWTException {
        CustomScreenshot screenshot = new CustomScreenshot();
        screenshot.captureScreen();
    }

    public void captureScreen() {
        capturing = true;
        captureRect = new Rectangle(0, 0, 0, 0);

        panel = new ImagePanel();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                captureRect.setLocation(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (capturing) {
                    int option = JOptionPane.showConfirmDialog(frame, "Capture this area?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        captureRect.setSize(Math.abs(captureRect.width), Math.abs(captureRect.height));
                        BufferedImage image = robot.createScreenCapture(captureRect);
                        try {
                            saveImage(image);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        capturing = false;
                    } else {
                        captureRect.setSize(0, 0);
                    }
                    panel.setCaptureRect(captureRect);
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (capturing) {
                    int x = captureRect.x;
                    int y = captureRect.y;
                    int width = e.getX() - captureRect.x;
                    int height = e.getY() - captureRect.y;
                    captureRect.setBounds(x, y, width, height);
                    panel.setCaptureRect(captureRect);
                }
            }
        });

        frame = new JFrame("Custom Screenshot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(800, 600));
    }

    private void saveImage(BufferedImage image) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".png")) {
                file = new File(file.getParentFile(), file.getName() + ".png");
            }
            ImageIO.write(image, "png", file);
        }
    }
}

class ImagePanel extends JPanel {

    private Image image;
    private Rectangle rect;

    public void setImage(Image image) {
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
    }


    public void setCaptureRect(Rectangle rect) {
        this.rect = rect;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
        if (rect != null && !rect.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.fill(getBounds());
            g2d.setColor(Color.WHITE);
            g2d.draw(rect);
        }
    }
}
