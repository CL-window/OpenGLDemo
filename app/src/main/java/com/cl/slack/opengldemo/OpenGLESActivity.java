package com.cl.slack.opengldemo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/** 基类
 * 在onCreate 方法中创建一个 GLSurfaceView mGLSurfaceView，并将屏幕设置为全屏。并为mGLSurfaceView 设置 Render。
 * onResume，onPause 处理 GLSurfaceView 的暂停和恢复。
 * DrawScene 使用黑色清空屏幕。
 * OpenGL ES 内部存放图形数据的 Buffer 有 COLOR ,DEPTH (深度信息）等，在绘制图形只前一般需要清空 COLOR 和 DEPTH Buffer。
 * Created by slack on 2016/11/16.
 */

public class OpenGLESActivity extends Activity implements IOpenGLDemo{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setRenderer(new OpenGLRenderer(this));
        setContentView(mGLSurfaceView);
    }

    // 使用黑色清空屏幕
    public void onDraw(GL10 gl) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void onCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onChanged(GL10 gl, int width, int height) {

    }

    @Override
    protected void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }
    @Override
    protected void onPause() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }
    protected GLSurfaceView mGLSurfaceView;
}