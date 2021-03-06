package org.object;

import java.awt.event.KeyEvent;
import java.io.IOException;

import org.graphics.Animation;
import org.graphics.Renderer;
import org.input.Input;
import org.world.World;

public class Player extends Mob{

	private float velocityY = 10;
	private float gravity = 90.f;
	private float jumpHeight = 150;
	
	private int direction = 1;
	
	public Player(float posX, float posY) {
		super(posX, posY);
		
		width = 16;
		height = 16;
//		runSpeed = 100; 
		Animation anim = new Animation();
		try {
			anim.images.add(Renderer.loadImage("/resources/images/roach/1.gif"));
			anim.images.add(Renderer.loadImage("/resources/images/roach/2.gif"));
			anim.images.add(Renderer.loadImage("/resources/images/roach/3.gif"));
			anim.images.add(Renderer.loadImage("/resources/images/roach/4.gif"));
			anim.images.add(Renderer.loadImage("/resources/images/roach/5.gif"));
			anim.images.add(Renderer.loadImage("/resources/images/roach/6.gif"));
			anim.images.add(Renderer.loadImage("/resources/images/roach/7.gif"));
			anim.images.add(Renderer.loadImage("/resources/images/roach/8.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		animations = new Animation[]{anim};

	}
	
	public void update (float deltaTime){
		float moveX = 0;

		if (Input.getKey(KeyEvent.VK_LEFT)){
			moveX -= runSpeed;
		}
		
		if (Input.getKey(KeyEvent.VK_RIGHT)){
			moveX += runSpeed;
		}
		
		if (moveX > 0) {
			direction = 1;
		}

		if (moveX < 0) {
			direction = -1;
		}
		
		velocityY += gravity*deltaTime;

		if (doesCollide(posX, posY +1)){
			if (Input.getKeyDown(KeyEvent.VK_UP)){
				velocityY = (float) -Math.sqrt(2*jumpHeight*gravity);
			}
		}
		
		// COLLISIONS
		if (doesCollide(posX + moveX*deltaTime, posY)) {
			moveX -= moveX;
		}
		
		if (doesCollide(posX, posY + velocityY*deltaTime)) {
			velocityY -= velocityY;
		}
		
		// END COLLISIONS
		if (Input.getKey(KeyEvent.VK_SPACE)){
			Bullet bullet = new Bullet(posX, posY, direction);
			World.currentWorld.addSprite(bullet);
		}
		
		posX += moveX * deltaTime;
		posY += velocityY*deltaTime;
		
		Renderer.camX = posX;
		Renderer.camY = 600;
		
	}
	
}
