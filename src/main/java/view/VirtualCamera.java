package view;

import controller.CameraController;
import controller.CameraListener;
import data.CenterCube;
import data.Door;
import data.Rooftop;
import data.Tree;
import lombok.Data;
import model.*;

import javax.swing.*;
import java.awt.*;

@Data
public class VirtualCamera extends JPanel {

    private CameraController controller;

    public void paint(Graphics g) {
        super.paint(g);
        controller.draw(g);
    }

    public static void main(String args[]) {
        final VirtualCamera camera = new VirtualCamera();
        Scene scene = new Scene();
        CameraController controller = new CameraController(scene, camera);
        CameraListener cameraListener = new CameraListener(controller);
        Window window = new Window(camera, cameraListener);

        scene.add(new CenterCube());
        scene.add(new Rooftop());
        scene.add(new Tree());
        scene.add(new Door());
        while(true) camera.repaint();
    }
}
