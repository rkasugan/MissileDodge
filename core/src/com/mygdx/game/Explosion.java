package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Explosion extends Actor{



    float elapsedTime;
    Texture texture = new Texture(Gdx.files.internal("Explosion/explosion1.png"));
    Sprite sprite = new Sprite(new Texture(Gdx.files.internal("Explosion/explosion1.png")));
    TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheet/explosion.atlas"));
    Animation<TextureRegion> animation = new Animation(1/6f, textureAtlas.getRegions());
    static float width, height, x, y;


    public Explosion(float f, float xLoc, float yLoc){
        elapsedTime = f;
        setX(xLoc);
        setY(yLoc);
        x = getX();
        y = getY();
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        width = texture.getWidth();
        height = texture.getHeight();
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        sprite.setRegion(animation.getKeyFrame(elapsedTime));
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        super.act(delta);
        sprite.setPosition(getX(), getY());
        x = getX();
        y = getY();

        if (animation.isAnimationFinished(elapsedTime)) {
            this.addAction(Actions.removeActor());
        }
    }
}
