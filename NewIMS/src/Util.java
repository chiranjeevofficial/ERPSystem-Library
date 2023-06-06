import java.awt.*;
import static java.awt.Toolkit.getDefaultToolkit;

public class Util {
    public static Dimension getScreenDimension() {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        Insets insets = getDefaultToolkit().getScreenInsets(screens[0].getDefaultConfiguration());
        return new Dimension(getDefaultToolkit().getScreenSize().width - insets.left - insets.right, getDefaultToolkit().getScreenSize().height - insets.top - insets.bottom);
    }
}
