package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Scene {
    private List<Drawable> drawableList;

    public Scene() { this.drawableList = new ArrayList<Drawable>();}

    public void add(Drawable drawable) {
        drawableList.add(drawable);
    }
}
