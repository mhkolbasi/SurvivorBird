package com.serdar.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Serdar on 18.5.2018.
 */

public class MenuState extends  State {
    private Texture background;
    private Texture playbtn;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background.png");
        playbtn = new Texture("Button.png");
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }


    @Override
    public void update(float dt) {
                    handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sb.draw(playbtn,400,600,300,280);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playbtn.dispose();
    }

}
