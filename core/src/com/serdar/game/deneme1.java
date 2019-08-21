package com.serdar.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.serdar.game.State.GameStateManager;
import com.serdar.game.State.MenuState;
import java.util.Random;

public class deneme1 extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bird2;
	Texture wood1;
	Texture wood2;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	Texture bee4;

	Texture bird4;
	Texture background2;

	int vibrator=0;
	int sayac=0;

	float birdX=0;
	float birdY=0;

	int gameState=0;
	float velocity=0;
	float fly=0.3f;
	float enemyVelocity=2;
	Random random;
	int score = 0;
	int scoredEnemy=0;
	BitmapFont font;
	BitmapFont font2;
	BitmapFont font3;
	private Music music;

	Circle birdCircle;
	ShapeRenderer shapeRenderer;

	int numberOfEnemies=4;
	float []enemyY = new float[numberOfEnemies];
	float []enemyOffset = new float[numberOfEnemies];
	float []enemyOffset2 = new float[numberOfEnemies];
	float []enemyOffset3 = new float[numberOfEnemies];
	float []enemyOffset4 = new float[numberOfEnemies];


	float distance=0;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	Circle[] enemyCircles4;


	private GameStateManager gsm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		wood1 = new Texture("wood1.png");
		wood2 = new Texture("wood2.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");
		bird2 = new Texture("bird2.png");
		background2 =new Texture("background.png");


		music=Gdx.audio.newMusic(Gdx.files.internal("happy.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

		distance=Gdx.graphics.getWidth()/2;
		random=new Random();
		birdX = Gdx.graphics.getWidth()/2-bird.getHeight()/2;
		birdY=Gdx.graphics.getHeight()/3;
		shapeRenderer =new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles =new Circle[numberOfEnemies];
		enemyCircles2 =new Circle[numberOfEnemies];
		enemyCircles3 =new Circle[numberOfEnemies];
		enemyCircles4 =new Circle[numberOfEnemies];


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(6);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(5);



		for(int i=0;i<numberOfEnemies;i++){

			enemyOffset[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
			enemyOffset2[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
			enemyOffset3[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
			enemyOffset4[i]=(random.nextFloat()-0.67f)*(Gdx.graphics.getHeight()-200);

			enemyY[i]=Gdx.graphics.getWidth()-bee1.getWidth()/2+i*distance;


			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
			enemyCircles4[i] = new Circle();

		}

	}

	@Override
	public void render () {

		batch.begin();
		if(score<3) {

			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			batch.draw(wood1,170,200,280,290);
			batch.draw(wood2,770,200,280,290);
		}
		if(score>=3){
			batch.draw(background2,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		}

		if(gameState==1){

			float accelX = Gdx.input.getAccelerometerX();
			birdX = birdX-2*(accelX); // 0-1000
			System.out.println(birdX);
			// sağa kırınca - sola kırınca +

			if(birdX>1000){
				birdX=birdX-1000;
			}
			else if(birdX<0){
				birdX=1000-birdX;
			}

			if(enemyY[scoredEnemy]<Gdx.graphics.getWidth()/2-bird.getHeight()/2){
				score++;

				if(scoredEnemy<numberOfEnemies-1){
					scoredEnemy++;
				}else{
					scoredEnemy=0;
				}
			}

			if(Gdx.input.justTouched()){
				velocity=-7;
			}


			for(int i=0;i<numberOfEnemies;i++){

				if(enemyY[i]<170){
					enemyY[i] = enemyY[i]+numberOfEnemies*distance;

					enemyOffset[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset4[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);

				}
				else{
					enemyY[i]=enemyY[i]-enemyVelocity;
				}

				enemyY[i]=enemyY[i]-enemyVelocity;
				batch.draw(bee1,Gdx.graphics.getHeight()/2+enemyOffset[i],enemyY[i],170,160);
				batch.draw(bee1,Gdx.graphics.getHeight()/2+enemyOffset2[i],enemyY[i],170,160);
				batch.draw(bee1,Gdx.graphics.getHeight()/2+enemyOffset3[i],enemyY[i],170,160);
				batch.draw(bee1,Gdx.graphics.getHeight()/2+enemyOffset4[i],enemyY[i],170,160);


				enemyCircles[i] = new Circle(Gdx.graphics.getHeight()/2+enemyOffset[i]+80,enemyY[i]+85,85);
				enemyCircles2[i] = new Circle(Gdx.graphics.getHeight()/2+enemyOffset2[i]+80,enemyY[i]+85,85);
				enemyCircles3[i] = new Circle(Gdx.graphics.getHeight()/2+enemyOffset3[i]+80,enemyY[i]+85,85);
				enemyCircles4[i] = new Circle(Gdx.graphics.getHeight()/2+enemyOffset4[i]+80,enemyY[i]+85,85);
			/*enemyCircles[i] = new Circle(enemyY[i]+85,+Gdx.graphics.getHeight()/2+enemyOffset[i]+80,85);*/

			}
			enemyVelocity+=0.001f;

			if(birdY>0){
				velocity=velocity+fly;
				birdY=birdY-velocity; }

			else{
				gameState=2;
			}
			if(birdY>Gdx.graphics.getHeight()){
				gameState=2;
			}

		}else if(gameState==0){
			if(Gdx.input.justTouched()){
				gameState=1;
				vibrator=0;
			}
		}else if(gameState==2){
			font2.draw(batch,"Game Over Tap To Play Again!",30,210);

			if(Gdx.input.justTouched()){
				gameState=1;
				vibrator=0;
				birdY=Gdx.graphics.getHeight()/3;

				for(int i=0;i<numberOfEnemies;i++){

					enemyOffset[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset4[i]=(random.nextFloat()-0.6f)*(Gdx.graphics.getHeight()-200);

					enemyY[i]=Gdx.graphics.getWidth()-bee1.getWidth()/2+i*distance;


					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
					enemyCircles4[i] = new Circle();
				}


				velocity =0;
				scoredEnemy=0;
				score=0;
				enemyVelocity=2;

			}
		}


		if(Gdx.input.isTouched()){
			batch.draw(bird2,birdX,birdY,160,150);
		}
		else{
			batch.draw(bird,birdX,birdY,160,150);
		}

		font.draw(batch,String.valueOf(score),60,110);


		batch.end();
		birdCircle.set(birdX+85,birdY+80,80);
	/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	shapeRenderer.setColor(Color.BLACK);
	shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);*/


		for( int i=0;i<numberOfEnemies;i++){
		/*shapeRenderer.circle(Gdx.graphics.getHeight()/2+enemyOffset[i]+80,enemyY[i]+85,85);
		shapeRenderer.circle(Gdx.graphics.getHeight()/2+enemyOffset2[i]+80,enemyY[i]+85,85);
		shapeRenderer.circle(Gdx.graphics.getHeight()/2+enemyOffset3[i]+80,enemyY[i]+85,85);*/

			if (Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i]) ||Intersector.overlaps(birdCircle,enemyCircles4[i]) ){
				if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)){
					gameState =2;
					if(vibrator<10){
						Gdx.input.vibrate(11);
						vibrator++;
					}
					System.out.println("Collision Detection");
				}
			}
		}
		//shapeRenderer.end();
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		super.dispose();
		music.dispose();
	}

}
