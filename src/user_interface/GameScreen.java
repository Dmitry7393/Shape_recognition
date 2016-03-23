package user_interface;

import java.awt.BasicStroke;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JPanel;

import recognition.Read_file;

public class GameScreen extends JPanel implements ActionListener{
		private static final long serialVersionUID = 1L;
        private int x, y;
        int previous_x, previous_y;
        ArrayList<Line> lines = new ArrayList<Line>();
        
        ArrayList<Line> figures = new ArrayList<Line>();
        private Boolean adding_figure = false; 
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
	        	g2.setStroke(new BasicStroke(5));
	        	g2.drawLine(lines.get(i).x1, lines.get(i).y1, lines.get(i).x2, lines.get(i).y2);
	        }
	        for(int i = 0; i < figures.size(); i++)
	        {
	        	g2.setStroke(new BasicStroke(2));
	        	g2.drawLine( figures.get(i).x1,  figures.get(i).y1,  figures.get(i).x2,  figures.get(i).y2);
	        }
	        if(adding_figure == true)
	        g2.drawLine(previous_x, previous_y, x, y);
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
        	if(adding_figure == false)
        	{
        	 	x = e.getX();
    	    	y = e.getY();
    	    	Line line = new Line(previous_x, previous_y, x, y);
    	    	previous_x = x;
    	    	previous_y = y;
    	    	lines.add(line);
    	    	repaint();
        	}
        	else
        	{
        		x = e.getX();
        		y = e.getY();
    	    	repaint();
        	}
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
	    	if(adding_figure == true)
	    	{
	    		Line line = new Line(previous_x, previous_y, x, y);
		    	figures.add(line);
	    	}
	    	for(int i = 0; i < lines.size(); i++)
	    	{
	    		double[] n = normalize_vector(lines.get(i).x2 - lines.get(i).x1, lines.get(i).y2 - lines.get(i).y1);
	    		
	    		//System.out.println(lines.get(i).x1 + " " + lines.get(i).y1 + " " +
	    						//   lines.get(i).x2 + " " + lines.get(i).y2);

	    		if(n[0] >= 0 && n[1] >= 0)
	    		System.out.println("Right Down" + n[0] + " " + n[1]);
	    		
	    		if(n[0] < 0 && n[1] >= 0)
		    	System.out.println("Left Down " + n[0] + " " + n[1]);
	    		
	    		if(n[0] < 0 && n[1] < 0)
			    System.out.println("Left Up " + n[0] + " " + n[1]);
	    		
	    		if(n[0] >= 0 && n[1] < 0)
			    System.out.println("Right Up " + n[0] + " " + n[1]);
	    	}
	     	lines.clear();
	     	repaint();
	     	
        }
    }
    private double[] normalize_vector(int a, int b)
    {
    	double[] array = new double[2];
		double s = Math.sqrt(a*a + b*b);
		double n1 = a / s;
		double n2 = b / s;
		array[0] = n1;
		array[1] = n2;
		return array;
    }
	private class TAdapter extends KeyAdapter  implements KeyListener //
	{
	        public void keyReleased(KeyEvent e)
	        {
	        	adding_figure = false;
	        }
	        public void keyTyped(KeyEvent e) {
	        }
	        public void keyPressed(KeyEvent e) 
	        {
	           int key = e.getKeyCode();
	           if(key == KeyEvent.VK_SPACE)
	           {
	        	   Writer w = new Writer("D:/square1.txt");
	        	   String str = "";
	        	   for(int i = 0; i < figures.size(); i++)
	        	   {
	        		   str = Integer.toString(figures.get(i).x1)  + " " + Integer.toString(figures.get(i).y1) + 
	        				   " " + Integer.toString(figures.get(i).x2) + " " + Integer.toString(figures.get(i).y2);
	        		   w.write(str);
	        	   }
	           }
	           if(key == KeyEvent.VK_SHIFT)
	           {
	        	   adding_figure = true;
	           }
	           if(key == KeyEvent.VK_R)
	           {
	        	   Read_file f = new Read_file("D:/square1.txt");
	           }
	      }
	}
	public static void write_array(String path, String text)
	{
    	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true))))
    	{
    		out.println(text);
    	}
    	catch (IOException e)
    	{
    	    
    	}
	}
}