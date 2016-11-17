package com.cl.slack.opengldemo;

import javax.microedition.khronos.opengles.GL10;

public class DrawSquare extends OpenGLESActivity {

    Square square = new Square();

    @Override
    public void onDraw(GL10 gl) {
        super.onDraw(gl);
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
                GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        // Draw our square.
        square.draw(gl); // ( NEW )
    }
}
