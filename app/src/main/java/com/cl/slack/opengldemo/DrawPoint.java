package com.cl.slack.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/** 在屏幕上使用红色绘制 3 个点  坐标圆心在屏幕中间
 * 首先是使用 FloatBuffer 存放三个顶点坐标。
 * 使用 glColor4f(float red, float green, float blue, float alpha) 将当前颜色设为红色。
 * glPointSize(float size) 可以用来设置绘制点的大小。
 * 使用 glEnableClientState 打开 Pipeline 的Vectex 顶点“开关”
 * 打开顶点开关后，将顶点坐标传给 OpenGL 管道的方法为：glVertexPointer
 * 使用 glVertexPointer 通知 OpenGL ES 图形库顶点坐标。
 * 使用 GL_POINTS 模式使用 glDrawArrays 绘制 3 个顶点。
 */

public class DrawPoint extends OpenGLESActivity {

    float[] vertexArray = new float[]{
            -0.8f, -0.4f * 1.732f, 0.0f,
            0.8f, -0.4f * 1.732f, 0.0f,
            0.0f, 0.4f * 1.732f, 0.0f,
    };

    private ByteBuffer vbb;
    private FloatBuffer vertex;

    @Override
    public void onCreated(GL10 gl, EGLConfig config) {
        super.onCreated(gl, config);
        vbb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertex = vbb.asFloatBuffer();
        vertex.put(vertexArray);
        vertex.position(0);
    }

    @Override
    public void onDraw(GL10 gl) {
        super.onDraw(gl);
        // 使用0…1之间的浮点数表示。 0为0，1相当于255（0xFF)
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glPointSize(10f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        gl.glDrawArrays(GL10.GL_POINTS, 0, 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
