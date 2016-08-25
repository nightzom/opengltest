package renderEngine;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class DisplayManager {
	
	private static long window;
	
	private static final int WIDTH = 1100;
	private static final int HEIGHT = 600;
	
	public static void createDisplay() {		
        GLFWErrorCallback.createPrint(System.err).set();
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable 
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "OpenGL engine v1.0", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());       
        glfwSetWindowPos(
            window,
            (vidmode.width() - WIDTH) / 2,
            (vidmode.height() - HEIGHT) / 2
        );
        
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);       
        glfwShowWindow(window);
	};
	
	public static void updateDisplay() {
		
		glfwSwapBuffers(window);
		glfwPollEvents();
		
	};
	
	public static void closeDisplay() {
		glfwHideWindow(window);
	};
	
	public static void showWindow() {
		glfwShowWindow(window);
	}
	
	public static long getWindow() {
		return window;
	}
	
	public static int getWindowWidth() {
		int[] width = new int[1];
		int[] height = new int[1];
		glfwGetWindowSize(window, width, height);		
		return width[0];
	}
	
	public static int getWindowHeight() {
		int[] width = new int[1];
		int[] height = new int[1];
		glfwGetWindowSize(window, width, height);		
		return height[0];
	}
	
	public static int isKeyPressed(int key) {
		return glfwGetKey(window, key);
	}

}
