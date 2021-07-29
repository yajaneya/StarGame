package ru.gb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private TextureRegion region;
	private Texture img;
	private Texture fon;

	private float x, y;

	@Override
	public void create () {
		batch = new SpriteBatch();
		fon = new Texture("fon.jpg");
		img = new Texture("badlogic.jpg");
		region = new TextureRegion(img, 10, 10, 200, 200);
	}

	@Override
	public void render () {
		x++;
		ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
		batch.begin();
		batch.draw(fon,0,0);
		batch.draw(img, x, y);
		Gdx.graphics.getHeight();
		Gdx.graphics.getWidth();
		batch.draw(region, 300+x*0.5f, 300+y, 150, 150);
		batch.end();
		if (x>200) {
			x=0;
			y+=10;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
