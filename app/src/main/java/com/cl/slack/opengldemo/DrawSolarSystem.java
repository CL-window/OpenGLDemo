package com.cl.slack.opengldemo;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

/** 迷你太阳系
 * 太阳居中，逆时针自转。
 * 地球绕太阳顺时针公转，本身不自转。
 * 月亮绕地球顺时针公转，自身逆时针自转。
 * 使用 GLU 的 gluLookAt 来定义 modelview Matrix ，把相机放在正对太阳中心(0,0,0)，距离 15 (0,0,15)。
 * 使用 glPushMatrix 和 glPopMatrix 来将当前 Matrix 入栈或是出栈。
 * 首先将当前 matrix 入栈，以红色绘制太阳，并逆向转动，将当前 matrix 入栈的目的是在能够在绘制地球时恢复当前栈。
 * 然后绘制地球，使用局部坐标系来想象地球和太阳之间的相对运动，地球离开一距离绕太阳公转，
 * 相当于先旋转地球的局部坐标系，然后再平移地球的局部坐标系。对应到代码为先 glRotatef ,然后 glTranslate.
 * 最后是绘制月亮，使用类似的空间想象方法。
 * Created by slack on 2016/11/17 11:12.
 */
public class DrawSolarSystem extends OpenGLESActivity {

    private Star sun=new Star();
    private Star earth=new Star();
    private Star moon=new Star();
    private int angle=0;

    @Override
    public void onDraw(GL10 gl) {
        super.onDraw(gl);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0.0f, 0.0f, 15.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);
        // Star A
        // Save the current matrix.
        gl.glPushMatrix();
        // Rotate Star A counter-clockwise.
        gl.glRotatef(angle, 0, 0, 1);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        // Draw Star A.
        sun.draw(gl);
        // Restore the last matrix.
        gl.glPopMatrix();
        // Star B
        // Save the current matrix
        gl.glPushMatrix();
        // Rotate Star B before moving it,
        //making it rotate around A.
        gl.glRotatef(-angle, 0, 0, 1);
        // Move Star B.
        gl.glTranslatef(3, 0, 0);
        // Scale it to 50% of Star A
        gl.glScalef(.5f, .5f, .5f);
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        // Draw Star B.
        earth.draw(gl);
        // Star C
        // Save the current matrix
        gl.glPushMatrix();
        // Make the rotation around B
        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of Star B
        gl.glScalef(.5f, .5f, .5f);
        // Rotate around it's own center.
        gl.glRotatef(angle*10, 0, 0, 1);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        // Draw Star C.
        moon.draw(gl);
        // Restore to the matrix as it was before C.
        gl.glPopMatrix();
        // Restore to the matrix as it was before B.
        gl.glPopMatrix();
        // Increse the angle.
        angle++;
    }
}
