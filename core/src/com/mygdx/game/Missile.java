package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Missile extends Actor{
    float elapsedTime;
    static boolean collision = false;
    static int score;
    Texture texture = new Texture(Gdx.files.internal("missile/missile1.png"));
    Sprite sprite = new Sprite(new Texture(Gdx.files.internal("missile/missile1.png")));
    TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheet/missile.atlas"));
    Animation<TextureRegion> animation = new Animation(1/4f, textureAtlas.getRegions());
    static float width, height, x, y;


    public Missile(float f){
        elapsedTime = f;
        setX((float)(Math.random()*Gdx.graphics.getWidth()));
        setY(Gdx.graphics.getHeight());
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        width = texture.getWidth();
        height = texture.getHeight();
        animation.setPlayMode(Animation.PlayMode.LOOP);

        SequenceAction sequence = new SequenceAction();

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(getX(), 0);
        moveToAction.setDuration(2f);

        sequence.addAction(moveToAction);
        sequence.addAction(Actions.removeActor());

        this.addAction(sequence);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        BitmapFont font = new BitmapFont();
        sprite.draw(batch);
        sprite.setRegion(animation.getKeyFrame(elapsedTime));
        font.getData().setScale(3f, 3f);
        font.setColor(Color.BLACK);
        font.draw(batch, "Score: " + score, 25,1750);
        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void act(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        super.act(delta);
        sprite.setPosition(getX(), getY());
        x = getX();
        y = getY();
        if(collision){
            this.clearActions();
            this.addAction(Actions.removeActor());
            collision = false;
        }
    }

    public static Rectangle getRectangle(){
        return new Rectangle(x-25,y+0,width,height);
    }

    public void setCollision(){
        collision = true;
    }

    public static void setScore(int x){
        score = x;
    }
}
