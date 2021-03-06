package presentationLayer.guiLogicPkg;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class ContentPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int xx;
	private int xy;

	public ContentPane(LaTazzaFrame frame) {

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
                Point p = ContentPane.this.getLocationOnScreen();
                p.x = p.x + arg0.getX() - xx;
                p.y = p.y + arg0.getY() - xy;
                frame.setLocation(p);
			}
		});
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
                xx = arg0.getX();
                xy = arg0.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
                Point p = ContentPane.this.getLocationOnScreen();
                if (Math.abs(p.x) < 12)
                    p.x = 0;
                if (Math.abs(p.y) < 12)
                    p.y = 0;
                frame.setLocation(p);
			}
		});
		
	}
	
}
