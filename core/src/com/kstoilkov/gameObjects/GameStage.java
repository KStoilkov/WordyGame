package com.kstoilkov.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kstoilkov.game.Config;
import com.kstoilkov.gui.HUD;
import com.kstoilkov.gui.PlayScreenBackground;

public class GameStage extends Group implements Disposable{
	private PlayScreenBackground psBackground;
	private HUD hud;
	private Player player;
	private Word word;
	
	private FallingLetter lastFallingLetter;

	private Texture lettersTexture;
	
	private Array<FallingLetter> fallingLetters;
	
	public GameStage() {
		super();
		this.psBackground = new PlayScreenBackground();
		this.hud = new HUD();
		this.player = new Player();
		this.word = new Word();
		this.lettersTexture = new Texture(Gdx.files.internal("Letters.png"));
		this.fallingLetters = new Array<FallingLetter>();
		
		this.addActorAt(0, this.psBackground);
		this.addActorAt(1, generateRandomLetter());
		this.addActorAt(2, this.hud);
		this.addActorAt(3, this.player);
		this.addActorAt(4, this.word);
		
	}

	public HUD getHUD(){
		return this.hud;
	}
	
	private boolean readyForLetter() {
		
		if(this.lastFallingLetter.getY() < Config.SCREEN_HEIGHT / 1.25f) {
			return true;
		}
		
		return false;
	}
	
	private void generateLetter(){
		if(readyForLetter()) {
			this.addActorAt(1, generateRandomLetter());
		}
	}
	
	private FallingLetter generateRandomLetter() {
		int randomLetterCode = 65 + (int)(Math.random() * (90 - 65));
		
		int randomPos = 1 + (int)(Math.random() * (Config.SCREEN_WIDTH - (Config.SCREEN_HEIGHT / 10f)) - 1f);
		
		this.lastFallingLetter = new FallingLetter(randomLetterCode, randomPos, this.lettersTexture);
		this.fallingLetters.add(this.lastFallingLetter);
		
		return this.lastFallingLetter;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void updateWord(String word) {
		this.word.setNewWord(word, this.lettersTexture);
	}
	
	private void detectCollision(boolean isRemoved, FallingLetter currentFallingLetter, int i){
		if(currentFallingLetter.getBounds().overlaps(this.player.getBounds())){
			if(this.word.check(currentFallingLetter)) {
				currentFallingLetter.remove();
				this.fallingLetters.removeIndex(i);
				isRemoved = true;
			}
		}	
	}

	private void checkForLetterOutOfScreen(FallingLetter current, int i){
		if(current.isUnderGround()) {
			current.remove();
			this.fallingLetters.removeIndex(i);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		boolean isRemoved = false;
		for (int i = 0; i < this.fallingLetters.size; i++) {
			checkForLetterOutOfScreen(this.fallingLetters.get(i), i);
		
			if(!isRemoved){
				detectCollision(isRemoved, this.fallingLetters.get(i), i);	
			}
		}
		
		generateLetter();
	}
	
	public void dispose(){
		this.lettersTexture.dispose();
	}
}
