package com.kstoilkov.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kstoilkov.game.Config;
import com.kstoilkov.gameObjects.Letter.LetterType;

public class Word extends Actor{
	private final int MAX_WORD_LETTERS = 10;
	private final float WORD_LETTER_WIDTH = Config.SCREEN_WIDTH / MAX_WORD_LETTERS;
	private final float WORD_LETTER_HEIGHT = Config.SCREEN_HEIGHT / 17f;
	//private final float MAX_WORD_SIZE = MAX_WORD_LETTERS * WORD_LETTER_SIZE;
	
	private Array<WordLetter> letters;
	
	private int currentWordSize;
	
	public Word() {
		this.letters = new Array<WordLetter>();
	}
	
	public void setNewWord(String word, Texture lettersTexture) {
		this.currentWordSize = this.calculateWordSize(word);
		this.covertWordToLetters(word, lettersTexture);
	}
	
	public boolean check(Letter letter){
		for (WordLetter wordLetter : letters) {
			if(wordLetter.getType() == LetterType.EMPTY_LETTER){
				if(wordLetter.compareTo(letter)){
					wordLetter.setType(LetterType.FULL_LETTER);
					wordLetter.resetRegion();
					return true;
				}	
			}
		}
		
		return false;
	}
	
	private void covertWordToLetters(String word, Texture lettersTexture){
		float wordStartPosition = (Config.SCREEN_WIDTH / 2f) - (this.currentWordSize / 2f);
		int wordLen = word.length();
		
		for (int i = 1; i <= wordLen; i++) {
			int num = (int)word.charAt(i - 1);
			this.letters.add(new WordLetter(num, i * wordStartPosition, this.WORD_LETTER_WIDTH, this.WORD_LETTER_HEIGHT, lettersTexture));
		}
	}
	
	private int calculateWordSize(String word){
		return word.length() * (int)WORD_LETTER_WIDTH;
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		
		for (WordLetter wordLetter : letters) {
			wordLetter.draw(batch, alpha);
		}
	}
}
