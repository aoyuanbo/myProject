import java.nio.FloatBuffer;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Ball implements GLEventListener {
	
	float lightPosition[] = {10.0f,10.0f,10.0f,0.0f};  
	float whiteLight[] = {0.8f,0.8f,0.8f,1.0f};
	
	float matSpecular [] = {0.3f,0.3f,0.3f,1.0f};  
	float matShininess [] = {20.0f};  
	float matEmission [] = {0.3f,0.3f,0.3f,1.0f};


	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO 自动生成的方法存根
		final GL2 gl=drawable.getGL().getGL2();
		gl.glClearColor(0.3f, 0.3f, 0.3f, 1f);
		gl.glClearDepth(1);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition,0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, whiteLight,0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteLight,0);
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO 自动生成的方法存根
        final GL2 gl = drawable.getGL().getGL2();
        GLUT glut=new GLUT();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-10.0,10.0,-10.0,10.0,-10.0,10.0);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glRotated(0, 0, 0, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpecular,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShininess,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, matEmission,0);
        glut.glutSolidSphere(5.0, 60, 60);
        gl.glPopMatrix();
		gl.glFlush();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO 自动生成的方法存根
		drawable.getGL().getGL2().glViewport(0, 0, width, height);;
	}
	
	public static void main(String[] args) {
		final GLProfile proifile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(proifile);

        // 画布
        final GLCanvas glCanvas = new GLCanvas(capabilities);
        Ball ball=new Ball();
        glCanvas.addGLEventListener(ball);
        glCanvas.setSize(600, 600);

        // 创建JFrame
        final JFrame frame = new JFrame("带光照效果的旋转多边形");

        frame.getContentPane().add(glCanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
	}
}
