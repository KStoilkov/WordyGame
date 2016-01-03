package com.kstoilkov.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.kstoilkov.game.Config;

public class HUD extends Actor implements Disposable{
	private final String CONTROLS_BACKGROUND_PATH = "controlsBackground.png";
	private Texture controlsBackground;
	
	private Table table;
	private Skin skin;
	private BitmapFont font;
	private Label.LabelStyle labelStyle;
	
	private int score;
	
	private Label scoreLabel;
	
	public HUD() {
		this.score = 0;
		
		this.setPosition(0, 0);
		this.setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		defineHUD();
	}
	
	private void defineHUD() {
		this.table = new Table();
		this.table.sizeBy(this.getWidth(), this.getHeight() / 4f);
		this.table.setZIndex(3);
		
		this.controlsBackground = new Texture(CONTROLS_BACKGROUND_PATH);
		this.font = new BitmapFont();
		this.labelStyle = new Label.LabelStyle(this.font, Color.BLACK);
		
		this.skin = new Skin();
		this.skin.add("controlsBackground", this.controlsBackground);
		this.table.setSkin(skin);
		this.table.setBackground(this.skin.getDrawable("controlsBackground"));
		this.scoreLabel = new Label(String.format("%5d", this.score), this.labelStyle);
		this.table.add(this.scoreLabel).expand((int)Config.SCREEN_WIDTH, (int)(this.table.getHeight() / 4f));
		
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		this.table.act(delta);
	}
	
	public void update(int score){
		this.score = score;
		
		this.scoreLabel.setText(String.format("%5d", this.score));
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		this.table.draw(batch, alpha);
	}

	@Override
	public void dispose() {
		this.controlsBackground.dispose();
		this.skin.dispose();
		this.font.dispose();
	}
}
