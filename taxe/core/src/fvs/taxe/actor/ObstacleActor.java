package fvs.taxe.actor;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import gameLogic.map.IPositionable;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class ObstacleActor extends Image{

    private static int width = 20;
    private static int height = 20;

    public ObstacleActor(IPositionable location) {

    }

    @Override
    public int getWidth(ImageObserver observer) {
        return 0;
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return 0;
    }

    @Override
    public ImageProducer getSource() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return null;
    }
}
