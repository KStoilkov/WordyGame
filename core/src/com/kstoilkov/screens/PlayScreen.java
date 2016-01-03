package com.kstoilkov.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kstoilkov.game.Config;
import com.kstoilkov.game.WordyMain;
import com.kstoilkov.gameObjects.GameStage;

public class PlayScreen implements Screen{
	
	private WordyMain game;
	private OrthographicCamera camera;
	private Viewport port;
	
	private GameStage gameStage;
	
	private Stage stage;
	
	public PlayScreen(WordyMain game) {
		this.game = game;
		this.camera = new OrthographicCamera();
		this.port = new ScreenViewport(this.camera);
		this.gameStage = new GameStage();
		this.stage = new Stage(this.port, this.game.batch);
		this.stage.addActor(this.gameStage);
		this.setupCamera(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
	}

	@Override
	public void show() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		this.gameStage.updateWord("KOKOSHKA");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.handleInput(delta);
		
		this.gameStage.getHUD().update(1500);
		
		this.stage.getViewport().apply();
		this.stage.act(delta);
		this.stage.draw();
		this.stage.act(delta);
		this.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.stage.getViewport().update(width, height, true);
		setupCamera(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		this.gameStage.dispose();
		this.stage.dispose();
	}
	
	private void setupCamera(float width,float height) {
		this.camera.position.set(width / 2f, height / 2f, 0);
		this.camera.update();
	}
	
	private void handleInput(float delta) {
		if(Gdx.input.isTouched()) {
			float touchX = Gdx.input.getX();
			
			if (touchX < Config.SCREEN_WIDTH / 2f) {
				this.gameStage.getPlayer().moveLeft(delta);
			} else {
				this.gameStage.getPlayer().moveRight(delta);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			this.gameStage.getPlayer().moveLeft(delta);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			this.gameStage.getPlayer().moveRight(delta);
		} else {
			this.gameStage.getPlayer().stay();
		}
	}
}
