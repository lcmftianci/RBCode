package com.lcmf.cube;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLView extends GLSurfaceView
{
	MyRenderer myRenderer;

	public MyGLView(Context context)
	{
		super(context);
		myRenderer = new MyRenderer();
		setRenderer(myRenderer);
	}
	
	public void rotateView(float xr, float yr)
	{
		myRenderer.setRotationX(xr);
		myRenderer.setRotationY(yr);
	}
}
