package com.cl.slack.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/** 绘制一个绕 Y 轴不断旋转的 20 面体
 * 一个正 20 面体，有 12 个顶点，20 个面，30 条边构成
 * Created by slack on 2016/11/16 17:40.
 */
public class DrawIcosahedron extends OpenGLESActivity {

    static final float X=.525731112119133606f;
    static final float Z=.850650808352039932f;
    // 12 个顶点
    static float vertices[] = new float[]{
            -X, 0.0f, Z,  X, 0.0f, Z,   -X, 0.0f, -Z,  X, 0.0f, -Z,
            0.0f, Z, X,   0.0f, Z, -X,  0.0f, -Z, X,   0.0f, -Z, -X,
            Z, X, 0.0f,  -Z, X, 0.0f,   Z, -X, 0.0f,   -Z, -X, 0.0f
    };
    // 20 个面(三个点为一个面)
    static short indices[] = new short[]{
            0,4,1,  0,9,4,  9,5,4,  4,5,8,  4,8,1,
            8,10,1, 8,3,10, 5,3,8,  5,2,3,  2,7,3,
            7,10,3, 7,6,10, 7,11,6, 11,0,6, 0,1,6,
            6,1,10, 9,0,11, 9,11,2, 9,2,5,  7,2,11 };
    // 为每个顶点随机定义一些颜色,当给每个顶点定义一个颜色时，OpenGL自动为不同顶点颜色之间生成中间过渡颜色（渐变色）
    float[] colors = {
            0f, 0f, 0f, 1f,
            0f, 0f, 1f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 1f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f
    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer indexBuffer;

    private float angle;

    @Override
    public void onCreated(GL10 gl, EGLConfig config) {
        super.onCreated(gl, config);
        // 顶点，面，颜色存放到 Buffer 中以提高性能
        ByteBuffer vbb
                = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        // float has 4 bytes, colors (RGBA) * 4 bytes
        ByteBuffer cbb
                = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
        ByteBuffer ibb
                = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

    }

    public void onDraw(GL10 gl) {
        super.onDraw(gl);

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        gl.glRotatef(angle, 0, 1, 0);
        // 设置逆时针方法为面的“前面”
        gl.glFrontFace(GL10.GL_CCW);
        // 打开 忽略“后面”设置
        gl.glEnable(GL10.GL_CULL_FACE);
        // 明确指明“忽略”哪个面的代码
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
        angle++;
    }
}
