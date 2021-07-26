package ru.gb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private int x = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		x++;
		ScreenUtils.clear(0.4f, 0.24f, 0.51f, 1);
		batch.begin();
		batch.draw(img, x, 0);
		batch.setColor(0.56f, 0.32f, 0.67f, 0.5f);
		batch.draw(img, 300, 300, 100, 100);
		batch.setColor(1f, 1f, 1f, 0.8f);
		batch.draw(img, 230, 50, 100, 50);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
