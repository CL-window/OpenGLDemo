package com.cl.slack.opengldemo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack on 2016/11/16.
 */

public interface IOpenGLDemo {
    void onCreated(GL10 gl, EGLConfig config);
    void onDraw(GL10 gl);
    void onChanged(GL10 gl, int width, int height);
}
