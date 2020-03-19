package controller;

import java.awt.event.KeyEvent;

public class CameraListener implements java.awt.event.KeyListener{

    private CameraController cameraController;

    public CameraListener(CameraController controller) {
        this.cameraController = controller;
    }

    public void keyTyped(KeyEvent event) {

    }

    public void keyPressed(KeyEvent event) {
        if(event.getKeyChar() == 'a') {
            cameraController.rotateY(1);
        }
        if(event.getKeyChar() == 'd') {
            cameraController.rotateY(-1);
        }
        if(event.getKeyChar() == 'w') {
            cameraController.rotateX(1);
        }
        if(event.getKeyChar() == 's') {
            cameraController.rotateX(-1);
        }
        if(event.getKeyChar() == 'z') {
            cameraController.rotateZ(-1);
        }
        if(event.getKeyChar() == 'x') {
            cameraController.rotateZ(1);
        }
        if(event.getKeyCode() == KeyEvent.VK_UP) {
            cameraController.moveUp();
        }
        if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            cameraController.moveDown();
        }
        if(event.getKeyCode() == KeyEvent.VK_LEFT) {
            cameraController.moveLeft();
        }
        if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
            cameraController.moveRight();
        }
        if(event.getKeyChar() == 'o') {
            cameraController.moveForward();
        }
        if(event.getKeyChar() == 'p') {
            cameraController.moveBack();
        }
        if(event.getKeyChar() == '[') {
            cameraController.scaleUp();
        }
        if(event.getKeyChar() == ']') {
            cameraController.scaleDown();
        }
        if(event.getKeyChar() == 'h') {
            cameraController.setWallHackActive(!cameraController.isWallHackActive());
        }
    }

    public void keyReleased(KeyEvent event) {

    }
}
