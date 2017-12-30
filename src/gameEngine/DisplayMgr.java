package gameEngine;

import org.lwjgl.LWJGLException;

import java.awt.GraphicsEnvironment;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;

public class DisplayMgr {	
	public static int width;
	public static int height;
	
	public static void createDisplay(String title) {
		ContextAttribs attributes = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			java.awt.DisplayMode ddm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
			
			width = ddm.getWidth();
			height = ddm.getHeight();
			
			Display.setDisplayMode(new DisplayMode(ddm.getWidth()/2,ddm.getHeight()/2));
			Display.setTitle(title);
			Display.create(new PixelFormat(), attributes);
			Display.setFullscreen(true);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void updateDisplay() {
		Display.sync(60);
		Display.update();
	}
	public static void closeDisplay() {
		Display.destroy();
	}
}
