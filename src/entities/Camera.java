package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float movespeed = 0.2f;
	private float pitchspeed = 10f;
	private float rollspeed = 10f;
	private float yawspeed = 2f;
	
	public Camera()
	{
		
	}
	
	public void move()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z -= movespeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z += movespeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x += movespeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.x -= movespeed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			yaw += yawspeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
		{
			yaw -= yawspeed;
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
