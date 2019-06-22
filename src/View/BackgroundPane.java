package View;

import javax.swing.*;
import java.awt.*;

public class BackgroundPane extends JPanel {
    Image backgroundImage = null;

    BackgroundPane(String imageFile) {
        if(imageFile != null) {
            MediaTracker mediaTracker = new MediaTracker(this);
            this.backgroundImage = Toolkit.getDefaultToolkit().getImage(imageFile);
            mediaTracker.addImage(this.backgroundImage, 0);
            try {
                mediaTracker.waitForAll();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
