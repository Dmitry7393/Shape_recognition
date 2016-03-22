package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GameScreen extends JPanel implements ActionListener{
		private static final long serialVersionUID = 1L;
        private int x, y;
        int previous_x, previous_y;
        ArrayList<Line> lines = new ArrayList<Line>();
	public GameScreen()
	{      
	    addKeyListener(new TAdapter());
	    addMouseListener(new CustomListener());
	    addMouseMotionListener(new CustomListener());
	    setFocusable(true);
	    setBackground(Color.YELLOW);
	    setDoubleBuffered(true);
	}
	
		public void paint(Graphics g)
		{
	        super.paint(g);
	        Graphics2D g2 = (Graphics2D) g;
	        for(int i = 0; i < lines.size(); i++)
	        {
	        	g2.drawLine(lines.get(i).x1, lines.get(i).y1, lines.get(i).x2, lines.get(i).y2);
	        }
		}
  
        public void actionPerformed(ActionEvent e)
        {
            repaint();  
        }      
    public class CustomListener extends MouseAdapter implements MouseListener, MouseMotionListener                                                                                             
    {      
        public void mouseMoved(MouseEvent e){
        }
        public void mouseDragged(MouseEvent e)
        {
        	x = e.getX();
	    	y = e.getY();
	    	Line line = new Line(previous_x, previous_y, x, y);
	    	previous_x = x;
	    	previous_y = y;
	    	lines.add(line);
	    	repaint();
        }
	    public void mouseClicked(MouseEvent e) {
	      }
	    public void mouseEntered(MouseEvent e) {
	    }
	    public void mouseExited(MouseEvent e) {
	    }
	    public void mousePressed(MouseEvent e) {
	    	previous_x = e.getX();
	    	previous_y = e.getY();
	    }
	    public void mouseReleased(MouseEvent e){
	   
        }
    }
	private class TAdapter extends KeyAdapter  implements KeyListener //
	{
	        public void keyReleased(KeyEvent e)
	        {
	        }
	        public void keyTyped(KeyEvent e) {
	        }
	        public void keyPressed(KeyEvent e) 
	        {
	           int key = e.getKeyCode();
	           if(key == KeyEvent.VK_SPACE)
	           {
	        	   
	        	  
	           }
	           if(key == KeyEvent.VK_DELETE)
	           {
	        	  
	           }
	      }
	}
}