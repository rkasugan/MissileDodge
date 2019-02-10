package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;



public class Ship extends Actor {
    float elapsedTime;
    static float width, height, x, y;
    boolean doneMoving;

    String direction;

    Texture texture1 = new Texture(Gdx.files.internal("ship/ship2.png"));
    Sprite sprite1 = new Sprite(new Texture(Gdx.files.internal("ship/ship2.png")));

    public Ship(float f){
        elapsedTime = f;
        setBounds(getX(),getY(),texture1.getWidth(),texture1.getHeight());
        width = sprite1.getWidth() / 3.5f;
        height = sprite1.getHeight() / 2;

        this.setX(300f);
        this.setY(0f);

        direction = "center";
    }

    @Override
    public void draw(Batch batch, float alpha){
        elapsedTime+=Gdx.graphics.getDeltaTime();
        batch.draw(texture1, sprite1.getX(), sprite1.getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite1.setPosition(getX(),getY());
        x = getX();
        y = getY();
    }

    public static Rectangle getRectangle(){
        return new Rectangle(x + 0,y,width+70,height);
    }

    public void setDirection(String dir) {
        direction = dir;
        if (direction.equals("left")) {
            texture1 = new Texture(Gdx.files.internal("ship/ship1.png"));
            sprite1 = new Sprite(new Texture(Gdx.files.internal("ship/ship1.png")));
        }
        else if (direction.equals("right")) {
            texture1 = new Texture(Gdx.files.internal("ship/ship3.png"));
            sprite1 = new Sprite(new Texture(Gdx.files.internal("ship/ship3.png")));
        }
        else {
            texture1 = new Texture(Gdx.files.internal("ship/ship2.png"));
            sprite1 = new Sprite(new Texture(Gdx.files.internal("ship/ship2.png")));
        }
    }
}
