package com.jeff.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.jeff.views.GameScreen;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private FrameBuffer frameBuffer;
	private GameScreen gamescreen;
	private Matrix4 normalProjection;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(normalProjection);
		gamescreen = new GameScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		frameBuffer.begin();
		gamescreen.update();
		gamescreen.render(batch);
		batch.draw(frameBuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
		frameBuffer.end();
		batch.end();
	}

	@Override
	public void dispose() {
		frameBuffer.dispose();
		batch.dispose();
	}
}
