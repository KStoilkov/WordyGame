package com.kstoilkov.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Letter extends Actor {
	public enum LetterType {FALLING_LETTER, EMPTY_LETTER, FULL_LETTER}
	private final int REGION_SIZE = 32;
	private final int MIN_LETTER_CODE = 65;
	private final int MAX_LETTER_CODE = 90;
	
	private TextureRegion region;
	
	private LetterType letterType;
	
	private int letterCode;
	
	public Letter(int letterCode, Texture lettersTexture, LetterType letterType) {
		this.letterCode = letterCode;
		this.letterType = letterType;
		this.setRegion(lettersTexture);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	protected int getLetterCode(){
		return this.letterCode;
	}
	
	public LetterType getType(){
		return this.letterType;
	}
	
	protected void setType(LetterType type){
		this.letterType = type;
	}
	
	public boolean compareTo(Letter letter){
		if(this.letterCode == letter.getLetterCode()){
			return true;
		}
		
		return false;
	}
	
	private void setRegion(Texture lettersTexture){
		int regionRow = 0;
		
		if(this.letterType == LetterType.EMPTY_LETTER) {
			regionRow = 1;
		}
		
		for (int i = MIN_LETTER_CODE; i <= MAX_LETTER_CODE; i++){
			if(letterCode == i) {
				this.region = new TextureRegion(lettersTexture, (i - MIN_LETTER_CODE) * REGION_SIZE, regionRow * REGION_SIZE, REGION_SIZE, REGION_SIZE);
				break;
			}
		}
	}
	
	public void resetRegion(){
		this.setRegion(this.region.getTexture());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		batch.draw(this.region, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
