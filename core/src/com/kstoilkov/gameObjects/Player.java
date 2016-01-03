package com.kstoilkov.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kstoilkov.game.Config;

public class Player extends Actor implements Disposable{
	private enum PlayerState {MOVING_LEFT, MOVING_RIGHT, STAYING, DEAD}
	private final int SPRITE_WIDTH = 32;
	private final int SPRITE_HEIGHT = 48;
	
	private Texture sprite;
	private Array<TextureRegion> frames;
	
	private float elapsedTime;
	
	private Animation staying;
	private Animation runningLeft;
	private Animation runningRight;
	
	private PlayerState currentState;
	private PlayerState previousState;
	
	private float speed;
	
	public Player() {
		this.setSize(Config.SCREEN_WIDTH / 10f, Config.SCREEN_HEIGHT / 15f);
		this.setPosition((Config.SCREEN_WIDTH / 2f) - (this.getWidth() / 2f), Config.SCREEN_HEIGHT / 4.2f);
		this.speed = Config.SCREEN_WIDTH / 2f;
		loadFrames();
	}
	
	public Rectangle getBounds(){
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	private void loadFrames(){
		this.elapsedTime = 0f;
		
		this.sprite = new Texture("player.png");
		this.frames = new Array<TextureRegion>();
		
		this.frames.add(new TextureRegion(sprite, 0, 3 * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT));
		
		this.staying = new Animation(1f / 8f, this.frames);
		this.frames.clear();
		
		for(int i = 0; i < 4; i++) {
			this.frames.add(new TextureRegion(sprite, i * SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT));	
		}
		this.runningLeft = new Animation(1f/8f, this.frames);
		this.frames.clear();
		
		for (int i = 0; i < 4; i++) {
			this.frames.add(new TextureRegion(sprite, i * SPRITE_WIDTH, 2 * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT));
		}
		this.runningRight = new Animation(1f / 8f, this.frames);
		this.frames.clear();
		
		this.currentState = PlayerState.STAYING;
		this.previousState = PlayerState.STAYING;
	}
	
	private TextureRegion getFrame() {
		TextureRegion frame;
		
		switch(this.currentState) {
			case MOVING_LEFT: frame = this.runningLeft.getKeyFrame(this.elapsedTime, true); break;
			case MOVING_RIGHT: frame = this.runningRight.getKeyFrame(this.elapsedTime, true); break;
			case STAYING: 
			default: frame = this.staying.getKeyFrame(this.elapsedTime); break;
		}
		
		return frame;
	}
	
	public void moveLeft(float delta){
		this.currentState = PlayerState.MOVING_LEFT;
		if(this.getX() - (this.speed * delta) > 0) {
			this.moveBy(-this.speed * delta, 0);
		}
	}
	
	public void moveRight(float delta) {
		this.currentState = PlayerState.MOVING_RIGHT;
		if(this.getX() + (this.speed * delta) + this.getWidth() < Config.SCREEN_WIDTH) {
			this.moveBy(this.speed * delta, 0);	
		}
	}
	
	public void stay() {
		if(this.currentState != PlayerState.STAYING) {
			this.currentState = PlayerState.STAYING;
		}
		this.elapsedTime = 0f;
	}
	
	public PlayerState getState() {
		return this.currentState;
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		this.elapsedTime = this.currentState == this.previousState ? this.elapsedTime + delta : 0;
		this.previousState = this.currentState;
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		super.draw(batch, alpha);
		batch.draw(this.getFrame(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	@Override
	public void dispose() {
		this.sprite.dispose();
	}
}
