package fvs.taxe.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class TrainActor extends Image {
    public static int width = 36;
    public static int height = 36;
    public Train train;

    private Rectangle bounds;
    public boolean facingLeft;
    private float previousX;
    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private boolean paused;

    public TrainActor(Train train) {
        super(new Texture(Gdx.files.internal(train.getLeftImage())));
        leftDrawable = getDrawable();
        rightDrawable = new Image(new Texture(Gdx.files.internal(train.getRightImage()))).getDrawable();

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
}
