package com.cl.slack.opengldemo;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack on 2016/11/16.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private final IOpenGLDemo openGLDemo;
    public OpenGLRenderer(IOpenGLDemo demo){
        openGLDemo=demo;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);
        if(openGLDemo!=null){
            openGLDemo.onCreated( gl, config);
        }
    }

    public void onDrawFrame(GL10 gl) {
        if(openGLDemo!=null){
            openGLDemo.onDraw(gl);
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        // 全屏，坐标系圆点为左下角
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix 投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        /**
         * fovy,这个最难理解,我的理解是,眼睛睁开的角度,即,视角的大小,如果设置为0,相当你闭上眼睛了,所以什么也看不到,如果为180,那么可以认为你的视界很广阔
         * aspect,这个好理解,就是实际窗口的纵横比,即x/y
         * zNear,这个呢,表示你近处的裁面
         * zFar表示远处的裁面
         */
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
        if(openGLDemo!=null){
            openGLDemo.onChanged( gl, width, height);
        }
    }
}