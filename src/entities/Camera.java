package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(15,10,10);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float movespeed = 0.2f;
	private float pitchspeed = 10f;
	private float rollspeed = 10f;
	private float yawspeed = 2f;
	
	private float speed;
	
	public Camera()
	{
		this.speed = 0.5f;
	}
	
	public void move()
	{
		
		yaw =  - (Display.getWidth() - Mouse.getX() / 2);
		pitch =  (Display.getHeight() / 2) - Mouse.getY();
		
		if (pitch >= 90)
		{
			
			pitch = 90;
			
		}
		else if (pitch <= -90)
		{
			
			pitch = -90;
			
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{

			position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
			position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;
			
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
			position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;


		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			
			position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
			position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;

		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			
			position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
			position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;

		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	
	
}
