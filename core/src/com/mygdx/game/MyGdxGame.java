package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MyGdxGame implements ApplicationListener, InputProcessor{

	Ship ship;
	Missile missile;
	int score = 0;
	private SpriteBatch batch;
	Action centerSprite;
	Action leftSprite;
	Action rightSprite;
	Texture textureCloud;
	int numMissiles = 0;

	private Stage stage;

	@Override
	public void create() {
		stage = new Stage();
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();

		textureCloud = new Texture(Gdx.files.internal("cloudstwo.jpg"));

		ship = new Ship(Gdx.graphics.getDeltaTime());
		missile = new Missile(Gdx.graphics.getDeltaTime());
		stage.addActor(ship);
		stage.addActor(missile);
		numMissiles++;

		centerSprite = new Action(){
			public boolean act( float delta ) {
				ship.setDirection("center");
				return true;
			}
		};
		leftSprite = new Action(){
			public boolean act( float delta ) {
				ship.setDirection("left");
				return true;
			}
		};
		rightSprite = new Action(){
			public boolean act( float delta ) {
				ship.setDirection("right");
				return true;
			}
		};
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());

		//Texture textureCloud = new Texture(Gdx.files.internal("cloudstwo.jpg"));
		batch.begin();
		batch.draw(textureCloud, 0, 0);
		batch.end();

		stage.draw();

		/*Texture textureCloud = new Texture(Gdx.files.internal("cloudstwo.jpg"));
		batch.begin();
		batch.draw(textureCloud, 0, 0);
		batch.end();*/


		if(numMissiles < 1 || stage.getActors().size < 2){
		//if (numMissiles < 1) {
			Missile newMissile = new Missile(Gdx.graphics.getDeltaTime());
			stage.addActor(newMissile);
			numMissiles++;
			score++;
			newMissile.setScore(score);
		}

		Missile tempMissile = new Missile(1.0f);
		Rectangle missileRec = new Rectangle();
		Rectangle shipRec = new Rectangle();
		for (int i = 0; i < stage.getActors().size; i ++) {
			if (stage.getActors().get(i) instanceof Missile) {
				tempMissile = ((Missile)(stage.getActors().get(i)));
				missileRec = tempMissile.getRectangle();
			}
			else if (stage.getActors().get(i) instanceof Ship) {
				shipRec = ((Ship)stage.getActors().get(i)).getRectangle();
			}
		}
		if (missileRec.overlaps(shipRec)) {
			tempMissile.setCollision();
			score--;
			stage.addActor(new Explosion(Gdx.graphics.getDeltaTime(), tempMissile.getX(), tempMissile.getY()));
		}


		/*if (ship.getActions().size > 0) {
			ship.setDirection("center");
		}*/
	}

	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		float rate = 1000f;
		MoveToAction moveToAction = new MoveToAction();
		int moveToY = Gdx.graphics.getHeight()-screenY - 165;
		int moveToX = screenX - 120;
		moveToAction.setPosition((float)moveToX, (float)moveToY);

		int currentY = (int)ship.getY();
		int currentX = (int)ship.getX();

		float distance = (float)Math.sqrt(Math.pow(moveToY-currentY, 2)+Math.pow(moveToX-currentX, 2));
		float duration = distance / rate;
		moveToAction.setDuration(duration);

		ship.clearActions();

		if (moveToX > currentX) {
			Action actions = sequence(rightSprite, moveToAction, centerSprite);
			ship.addAction(actions);
		}
		else if (moveToX < currentX) {
			Action actions = sequence(leftSprite, moveToAction, centerSprite);
			ship.addAction(actions);
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	@Override
	public void dispose() {
	}
}