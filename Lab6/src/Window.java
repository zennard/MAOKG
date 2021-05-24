import javax.media.j3d.Canvas3D;
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
	private Canvas3D canvas;

	public Window(Canvas3D canvas) {
		this.canvas = canvas;

		configureWindow();

		getContentPane().add(canvas, BorderLayout.CENTER);
	}

	private void configureWindow() {
		setTitle("ladybug");
		setSize(800, 600);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - getWidth()) / 2;
		int locationY = (screenSize.height - getHeight()) / 2;
		setLocation(locationX,locationY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}



}
