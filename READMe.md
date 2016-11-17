使用EGL的绘图的一般步骤：

	获取 EGLDisplay 对象
	初始化与 EGLDisplay 之间的连接
	获取 EGLConfig 对象
	创建 EGLContext 实例
	创建 EGLSurface 实例
	连接 EGLContext 和 EGLSurface
	使用 GL 指令绘制图形
	断开并释放与 EGLSurface 关联的 EGLContext 对象
	删除 EGLSurface 对象
	删除 EGLContext 对象
	终止与 EGLDisplay 之间的连接

在Android 平台中提供了一个 android.opengl 包，
类 GLSurfaceView 提供了对Display,Surface,Context 的管理，
大大简化了 OpenGL ES 的程序框架,对应大部分 OpenGL ES 开发，
只需调用一个方法来设置 OpenGLView 用到的 GLSurfaceView.Renderer

Android OpenGL ES 相关的包主要定义在
// https://android.googlesource.com/platform/frameworks/base/+/master/opengl/java/javax/microedition/khronos/opengles?autodive=0%2F%2F/
javax.microedition.khronos.opengles GL 绘图指令
javax.microedition.khronos.egl EGL 管理Display, surface等
android.opengl Android GL辅助类，连接OpenGL 与Android View，Activity
javax.nio Buffer类
其中 GLSurfaceView 为 android.opengl 包中核心类：

起到连接 OpenGL ES 与 Android 的 View 层次结构之间的桥梁作用。

GLSurfaceView.Renderer 定义了一个统一图形绘制的接口，它定义了如下三个接口函数：

// Called when the surface is created or recreated.
public void onSurfaceCreated(GL10 gl, EGLConfig config)
// Called to draw the current frame.
public void onDrawFrame(GL10 gl)
// Called when the surface changed size.
public void onSurfaceChanged(GL10 gl, int width, int height)
onSurfaceCreated：在这个方法中主要用来设置一些绘制时不常变化的参数，比如：背景色，是否打开 z-buffer等。
onDrawFrame：定义实际的绘图操作。
onSurfaceChanged：如果设备支持屏幕横向和纵向切换，这个方法将发生在横向<->纵向互换时。此时可以重新设置绘制的纵横比率。

如果有需要，也可以通过函数来修改 GLSurfaceView 一些缺省设置：

setDebugFlags(int) 设置 Debug 标志。
setEGLConfigChooser (boolean) 选择一个 Config 接近 16bitRGB 颜色模式，可以打开或关闭深度(Depth)Buffer ,缺省为RGB_565 并打开至少有 16bit 的 depth Buffer。
setEGLConfigChooser(EGLConfigChooser) 选择自定义 EGLConfigChooser。
setEGLConfigChooser(int, int, int, int, int, int) 指定 red ,green, blue, alpha, depth ,stencil 支持的位数，缺省为 RGB_565 ,16 bit depth buffer。
GLSurfaceView 缺省创建为 RGB_565 颜色格式的 Surface，如果需要支持透明度，可以调用 getHolder().setFormat(PixelFormat.TRANSLUCENT)。

GLSurfaceView 的渲染模式有两种，一种是连续不断的更新屏幕，另一种为 on-demand ，只有在调用 requestRender() 在更新屏幕。 缺省为 RENDERMODE_CONTINUOUSLY 持续刷新屏幕。

OpenGL ES 定义的几种模式：
    GL_POINTS 绘制独立的点
    GL_LINE_STRIP 绘制一系列线段,按次序连接

    GL_LINE_LOOP类同上，但是首尾相连，构成一个封闭曲线
    GL_LINES 顶点两两连接，为多条线段构成

    GL_TRIANGLES 每隔三个顶点构成一个三角形，为多个三角形组成 独立的三角形
    GL_TRIANGLE_STRIP 每相邻三个顶点组成一个三角形，为一系列相接三角形构成
    GL_TRIANGLE_FAN 以一个点为三角形公共顶点，组成一系列相邻的三角形。


打开顶点开关后，将顶点坐标传给 OpenGL 管道的方法为：glVertexPointer：
    public void glVertexPointer(int size,int type,int stride,Buffer pointer)
    size：每个顶点坐标维数，可以为2，3，4。
    type：顶点的数据类型，可以为 GL_BYTE, GL_SHORT, GL_FIXED,或 GL_FLOAT，缺省为浮点类型 GL_FLOAT。
    stride：每个相邻顶点之间在数组中的间隔（字节数），缺省为 0，表示顶点存储之间无间隔。
    pointer：存储顶点的数组。

public abstract void glDrawArrays(int mode, int first, int count)
    使用 VetexBuffer 来绘制，顶点的顺序由 vertexBuffer 中的顺序指定
    mode:上面定义的模式
    first:开始的点序号，count:一共画多少个点 ,相对glVertexPointer里参数 pointer而言
public abstract void glDrawElements(int mode, int count, int type, Buffer indices)
    可以重新定义顶点的顺序，顶点的顺序由 indices Buffer 指定


















