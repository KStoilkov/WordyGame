package com.kstoilkov.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.kstoilkov.game.Config;

public class FallingLetter extends Letter{
	private final float LETTER_WIDTH = Config.SCREEN_WIDTH / 8f;
	private final float LETTER_HEIGHT = Config.SCREEN_HEIGHT / 14f;
	private final float MIN_SPEED = Config.SCREEN_HEIGHT / 10f;
	private final float MAX_SPEED = Config.SCREEN_HEIGHT / 5f;
	
	private float speed;
	
	public FallingLetter(int letterCode, float xPos, Texture lettersTexture){
		super(letterCode, lettersTexture, LetterType.FALLING_LETTER);
		this.setSize(LETTER_WIDTH, LETTER_HEIGHT);
		this.setPosition(xPos, Config.SCREEN_HEIGHT);
		this.speed = this.setSpeed();
	}
	
	private float setSpeed(){
		return MIN_SPEED + (int)(Math.random() * (MAX_SPEED - MIN_SPEED));
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		this.moveBy(0, -this.speed * delta);
	}

	public boolean isUnderGround(){
		if(this.getY() < (Config.SCREEN_HEIGHT / 4f - this.getHeight())) {
			return true;
		}
		return false;
	}
}
