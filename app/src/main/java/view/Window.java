package view;

import configuration.Configuration;
import controller.CameraListener;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private int screenHeight = Configuration.SCREEN_HEIGHT;
    private int ScreenWidth = Configuration.SCREEN_WIDTH;

    public Window(VirtualCamera camera, CameraListener cameraListener) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(camera);
        this.setSize(ScreenWidth, screenHeight);
        int x = (int) ((dimension.getWidth() - ScreenWidth) / 2);
        int y = (int) ((dimension.getHeight() - screenHeight) / 2);
        this.addKeyListener(cameraListener);
        this.setLocation(x, y);
        this.setVisible(true);
    }
}
