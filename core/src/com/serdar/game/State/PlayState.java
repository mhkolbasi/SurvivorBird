package com.serdar.game.State;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {
    private Texture bird;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Texture("bird.png");
    }
    @Override
    protected void handleInput() {
    }
    @Override
    public void update(float dt) {
    }
    @Override
    public void render(SpriteBatch sb) {
            sb.begin();
            sb.end();
    }
    @Override
    public void dispose() {
    }
}
