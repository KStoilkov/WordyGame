package com.kstoilkov.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.kstoilkov.game.Config;


public class PlayScreenBackground extends Actor implements Disposable{
	private final int BACKGROUND_ANIMATION_SPEED = (int)Config.SCREEN_HEIGHT / 30;
	private final String BACKGROUND_PATH = "Background.png"; 
	
	private TextureRegion region;
	private Rectangle regionBounds1;
	private Rectangle regionBounds2;
	public PlayScreenBackground() {
		this.region = new TextureRegion(new Texture(Gdx.files.internal(BACKGROUND_PATH)));
		this.regionBounds1 = new Rectangle(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		this.regionBounds2 = new Rectangle(0, Config.SCREEN_HEIGHT / 1.1f, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(this.bottomReached(delta)){
			this.resetBounds();
		} else {
			this.updateBounds(delta);
		}
	}

	private void updateBounds(float delta) {
		this.regionBounds1.y -= delta * BACKGROUND_ANIMATION_SPEED;
        this.regionBounds2.y -= delta * BACKGROUND_ANIMATION_SPEED;
	}

	private void resetBounds() {
		this.regionBounds1 = this.regionBounds2;
		this.regionBounds2 = new Rectangle(0, Config.SCREEN_HEIGHT / 1.1f, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    }

	private boolean bottomReached(float delta) {
		return (this.regionBounds2.y - (delta * BACKGROUND_ANIMATION_SPEED)) <= 0;
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		batch.draw(this.region, this.regionBounds1.x, this.regionBounds1.y, (float)Config.SCREEN_WIDTH, (float)Config.SCREEN_HEIGHT);
		batch.draw(this.region, this.regionBounds2.x, this.regionBounds2.y, (float)Config.SCREEN_WIDTH, (float)Config.SCREEN_HEIGHT);
	}

	@Override
	public void dispose() {
		this.region.getTexture().dispose();
	}
}
