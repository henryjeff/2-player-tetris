package com.jeff.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.jeff.views.GameScreen;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private FrameBuffer frameBuffer;
	private GameScreen gamescreen;
	private ShaderProgram shader;
	public static OrthographicCamera camera;
	public static TextureAtlas atlas;
	public static float delta;
	public static float elapsedTime;
	public static final int SIZE_CLIP = 32;
	private float cameraY;
	private float cameraX;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		delta = Gdx.graphics.getDeltaTime();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true);
//		camera.zoom = 1.10f;
		camera.update();
		cameraY = camera.position.y;
		cameraX = camera.position.x;
		atlas = new TextureAtlas(Gdx.files.internal("animations/atlas.txt"));
		gamescreen = new GameScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) camera.viewportWidth, (int) camera.viewportHeight, false);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		shader = new ShaderProgram(Gdx.files.internal("shaders/vignette.vert"), Gdx.files.internal("shaders/vignette.frag"));
		if (!shader.isCompiled()) {
			throw new GdxRuntimeException(shader.getLog());
		}
		ShaderProgram.pedantic = false;
	}

	@Override
	public void render() {
		delta = Gdx.graphics.getDeltaTime();
		elapsedTime += delta;

		cameraY += (float) (Math.cos(2 * elapsedTime) / 10);
		camera.position.y = cameraY;
		
		cameraX = Gdx.graphics.getWidth() / 2 + (float) (Math.sin(elapsedTime) * 2);
		camera.position.x = cameraX;

		batch.setProjectionMatrix(camera.combined);
		camera.update();
		batch.begin();
		Gdx.gl.glClearColor(0.054f, 0.054f, 0.90f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gamescreen.update(delta);
		gamescreen.render(batch);
		batch.flush();
		batch.end();
		batch.setShader(null);
	}

	@Override
	public void dispose() {
		frameBuffer.dispose();
		batch.dispose();
	}
}
