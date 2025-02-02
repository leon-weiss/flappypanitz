package name.panitz.game2d.flappypanitz.Screen;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontLoader {

    public Font loadFont(String file, String fontName, String fallback, int size) {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file + ".ttf"))));
            return new Font(fontName, Font.PLAIN, size);
        } catch (IOException | FontFormatException | NullPointerException e) {
            System.err.println("Error loading font " + file);
            e.printStackTrace();
            return new Font(fallback, Font.PLAIN, size);
        }
    }

}
