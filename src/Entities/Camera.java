package Entities;

import java.awt.RenderingHints.Key;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Keyboard;
import org.lwjglx.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.pitch += dx;
		this.yaw += dy;
		this.roll += dz;
	}
	
	public void move() {
		if (DisplayManager.isKeyPressed(GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
			position.z -= 0.02f;
		}
		if (DisplayManager.isKeyPressed(GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
			position.z += 0.02f;
		}
		if (DisplayManager.isKeyPressed(GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
			position.x -= 0.02f;
		}
		if (DisplayManager.isKeyPressed(GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
			position.x += 0.02f;
		}
		if (DisplayManager.isKeyPressed(GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
			position.y += 0.02f;
		}
		if (DisplayManager.isKeyPressed(GLFW.GLFW_KEY_X) == GLFW.GLFW_PRESS) {
			position.y -= 0.02f;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	};	
}
