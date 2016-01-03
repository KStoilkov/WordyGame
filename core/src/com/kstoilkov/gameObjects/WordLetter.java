package com.kstoilkov.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.kstoilkov.game.Config;

public class WordLetter extends Letter {
	
	public WordLetter(int letterCode, float xPos, float width, float height,Texture lettersTexture){
		super(letterCode, lettersTexture, LetterType.EMPTY_LETTER);
		this.setSize(width, height);
		this.setPosition(xPos, Config.SCREEN_HEIGHT / 5.5f);
	}
}
