package fvs.taxe.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import fvs.taxe.controller.Context;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

import javax.swing.*;
import java.util.ArrayList;

public class TrainActor extends Image {
    public static int width = 36;
    public static int height = 36;
    public Train train;

    private Rectangle bounds;
    public boolean facingLeft;
    private float previousX;
    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private Context context;
    private boolean paused;

    public TrainActor(Train train, Context context) {
        super(new Texture(Gdx.files.internal(train.getLeftImage())));
        leftDrawable = getDrawable();
        rightDrawable = new Image(new Texture(Gdx.files.internal(train.getRightImage()))).getDrawable();
        this.context = context;

        IPositionable position = train.getPosition();

        train.setActor(this);
        this.train = train;
        setSize(width, height);
        bounds = new Rectangle();
        setPosition(position.getX() - width / 2, position.getY() - height / 2);
        previousX = getX();
        facingLeft = true;
        paused = false;
    }

    @Override
    public void act (float delta) {
        if ((Game.getInstance().getState() == GameState.ANIMATING) && (! this.paused)) {
            super.act(delta);
            updateBounds();
            updateFacingDirection();

            Train collision = collided();
            //JOptionPane.showMessageDialog(null, collided(),"message", JOptionPane.INFORMATION_MESSAGE);
            if (collision != null){
                context.getTopBarController().displayFlashMessage("Two trains collided.  They were both destroyed.", Color.RED, 2);
                Game.getInstance().getMap().blockConnection(train.getLastStation(), train.getNextStation(), 5);
                collision.getActor().remove();
                collision.getPlayer().removeResource(collision);
                train.getPlayer().removeResource(train);
                this.remove();
            }

        } else if (this.paused){

            //find station train most recently passed
            Station station = train.getHistory().get(train.getHistory().size()-1).getFirst();
//            Station station = Game.getInstance().getMap().getStationByName(stationName);

            // find index of this within route
            int index = train.getRoute().indexOf(station);

            // find next station
            Station nextStation = train.getRoute().get(index+1);

            // check if connection is blocked, if no unpause
            if (! Game.getInstance().getMap().isConnectionBlocked(station, nextStation))
                this.paused = false;
        }
    }

    private void updateBounds() {
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }

    public void updateFacingDirection() {
        float currentX = getX();

        if(facingLeft && previousX < currentX) {
            setDrawable(rightDrawable);
            facingLeft = false;
        } else if(!facingLeft && previousX > currentX) {
            setDrawable(leftDrawable);
            facingLeft = true;
        }

        previousX = getX();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }

    public Train collided(){
        if (train.getPosition().getX() == -1){ //if this train is moving;
            for (Player player : Game.getInstance().getPlayerManager().getAllPlayers()) {
                for (Train otherTrain : player.getTrains()) {

                    if (!otherTrain.equals(train)) { //don't check if collided with self

                        if (otherTrain.getPosition() != null) { //if other train has been placed on map
                            if (otherTrain.getPosition().getX() == -1) { //if other train moving

                                float difX = Math.abs(otherTrain.getActor().getX() - getX());

                                float difY = Math.abs(otherTrain.getActor().getY() - getY());
                                if (difX < 0.1 && difY < 0.1) {
                                    return otherTrain;
                                }
                            }
                        }




                    }
                }
            }
        }
        return null;
    }


}