package com.kstoilkov.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.kstoilkov.screens.PlayScreen;

public class WordyMain extends Game implements Disposable{
	public SpriteBatch batch;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
		System.out.println(Gdx.app.getJavaHeap());
	}
	
	@Override
	public void dispose() {
		this.batch.dispose();
	}
	
}
