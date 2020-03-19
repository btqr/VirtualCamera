# Virtual camera

## Desription

Virtual camera is a program, which simulates a real camera. In the program you can move, rotate, zoom in and zoom out picture in the camera. You can also make simple textures visible in the 3D models on the scene. 

## Demo

![Virtual camera program demo](https://s5.gifyu.com/images/virtual_camera.gif)

## Manual

You can move in the program using only keyboard:

| Key           | Action                       |
| ------------  |:----------------------------:|
| W             | Rotate forward about  X axis |
| A             | Rotate backward about Y axis |
| S             | Rotate backward about X axis |
| D             | Rotate forward about Y axis  |
| Z             | Rotate backward about Z axis |
| X             | Rotate forward about Z axis  |
| O             | Move closer                  |
| P             | Move further                 |
| [             | Zoom in                      |
| ]             | Zoom out                     |
| h             | Make textures visible        |

## Configuration

You can change configuration in Configuration.class by setting values of static properties.

```java
public class Configuration {
    public static final int SCREEN_HEIGHT = 1080;
    public static final int SCREEN_WIDTH = 1920;
    public static final double ROTATION_DEGREE = 0.1;
    public static final double MOVING_STEP = 10;
    public static final double SCALING_STEP = 0.01;
    public static final double DISTANCE_FROM_CAMERA = 450;
}
```