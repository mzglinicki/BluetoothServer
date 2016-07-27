import javax.swing.*;
import java.awt.*;

/**
 * This class supports the following orientations:
 * <ul>
 * <li>DOWN - rotated 90 degrees
 * <li>UP (default) - rotated -90 degrees
 * </ul>
 */
public class RotatedIcon implements Icon {
    public enum Rotate {
        DOWN,
        UP,
    }

    private Icon icon;
    private Rotate rotate;

    /**
     * Create a RotatedIcon
     *
     * @param icon   the Icon to rotate
     * @param rotate the direction of rotation
     */
    public RotatedIcon(final Icon icon, final Rotate rotate) {
        this.icon = icon;
        this.rotate = rotate;
    }

    /**
     * Gets the width of this icon.
     *
     * @return the width of the icon in pixels.
     */
    @Override
    public int getIconWidth() {
        return icon.getIconHeight();
    }

    /**
     * Gets the height of this icon.
     *
     * @return the height of the icon in pixels.
     */
    @Override
    public int getIconHeight() {
        return icon.getIconWidth();
    }

    /**
     * Paint the icons of this compound icon at the specified location
     *
     * @param c The component on which the icon is painted
     * @param g the graphics context
     * @param x the X coordinate of the icon's top-left corner
     * @param y the Y coordinate of the icon's top-left corner
     */
    @Override
    public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
        Graphics2D g2 = (Graphics2D) g.create();

        int cWidth = icon.getIconWidth() / 2;
        int cHeight = icon.getIconHeight() / 2;
        int xAdjustment = (icon.getIconWidth() % 2) == 0 ? 0 : -1;
        int yAdjustment = (icon.getIconHeight() % 2) == 0 ? 0 : -1;

        if (rotate == Rotate.DOWN) {
            g2.translate(x + cHeight, y + cWidth);
            g2.rotate(Math.toRadians(90));
            icon.paintIcon(c, g2, -cWidth, yAdjustment - cHeight);
        } else if (rotate == Rotate.UP) {
            g2.translate(x + cHeight, y + cWidth);
            g2.rotate(Math.toRadians(-90));
            icon.paintIcon(c, g2, xAdjustment - cWidth, -cHeight);
        }
        g2.dispose();
    }
}