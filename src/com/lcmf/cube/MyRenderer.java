package com.lcmf.cube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class MyRenderer implements Renderer
{
	MyCube cube = new MyCube();
	static double[] cq = {1.0, 0.0, 0.0, 0.0};
	static double[] tq = new double[4];
	static double[] rt = new double[16];	
	float xr = 0f;
	float yr = 0f;
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -3f);
		gl.glRotatef(yr, 1, 0, 0);
		gl.glRotatef(xr, 0, 1, 0);
		//gl.glRotatef(yr, 0.8f, 0.2f, 0f);
		cube.draw(gl);
	}
	

	// Multiplication for quaternion r <- p x q
	private double[] qmul(double r[], double p[], double q[])
	{
		r[0] = p[0] * q[0] - p[1] * q[1] - p[2] * q[2] - p[3] * q[3];
		r[1] = p[0] * q[1] + p[1] * q[0] + p[2] * q[3] - p[3] * q[2];
		r[2] = p[0] * q[2] - p[1] * q[3] + p[2] * q[0] + p[3] * q[1];
		r[3] = p[0] * q[3] + p[1] * q[2] - p[2] * q[1] + p[3] * q[0];
		
		return r;
	}

	// Transform quaternion
	private double[] qrot(double r[], double q[])
	{
		double x2 = q[1] * q[1] * 2.0;
		double y2 = q[2] * q[2] * 2.0;
		double z2 = q[3] * q[3] * 2.0;
		double xy = q[1] * q[2] * 2.0;
		double yz = q[2] * q[3] * 2.0;
		double zx = q[3] * q[1] * 2.0;
		double xw = q[1] * q[0] * 2.0;
		double yw = q[2] * q[0] * 2.0;
		double zw = q[3] * q[0] * 2.0;
		
		r[ 0] = 1.0 - y2 - z2;
		r[ 1] = xy + zw;
		r[ 2] = zx - yw;
		r[ 4] = xy - zw;
		r[ 5] = 1.0 - z2 - x2;
		r[ 6] = yz + xw;
		r[ 8] = zx + yw;
		r[ 9] = yz - xw;
		r[10] = 1.0 - x2 - y2;
		r[ 3] = r[ 7] = r[11] = r[12] = r[13] = r[14] = 0.0;
		r[15] = 1.0;
		
		return r;
	}

	public void setRotationX(float xr)
	{
		this.xr += xr;
		if(this.xr > 360)
			this.xr %= 360;
	}
	public void setRotationY(float yr)
	{
		this.yr += yr;
		if(this.yr > 360)
			this.yr %= 360;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();    
		GLU.gluPerspective(gl, 45f,(float) width / height, 1f, 50f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		// lighting
		gl.glEnable(GL10.GL_LIGHTING);
	    gl.glEnable(GL10.GL_LIGHT0);
	}
}
