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
import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import recognition.Identify_figure;
import recognition.Read_file;
import recognition.Vector_direction;

public class GameScreen extends JPanel implements ActionListener{
		private static final long serialVersionUID = 1L;
        private int x, y;
        int previous_x, previous_y;
        ArrayList<Line> lines = new ArrayList<Line>();
        ArrayList<Line> new_lines = new ArrayList<Line>();
        ArrayList<Line> figures = new ArrayList<Line>();
        ArrayList<Vector_direction> directions = new ArrayList<Vector_direction>();
        ArrayList<Vector_direction> new_directions = new ArrayList<Vector_direction>();
        ArrayList<Line> example_lines = new ArrayList<Line>();
        private Boolean adding_figure = false; 
        
        private String current_file = "";
        private int level = 5;
        private int max_level = 5;
        
        private String new_figure = "Levels/5 - star.txt";
	public GameScreen()
	{      
	    addKeyListener(new TAdapter());
	    addMouseListener(new CustomListener());
	    addMouseMotionListener(new CustomListener());
	    setFocusable(true);
	   // Color color1 = new Color(127, 255, 212);
	    Color color2 = new Color(251,	236,	93);
	    setBackground(color2);
	    setDoubleBuffered(true);
	    start_game();
	}
	private String get_path()
	{
		String path = "";
		File[] files = new File("Levels").listFiles();
	   	   //If this pathname does not denote a directory, then listFiles() returns null. 
	   	   for (File file : files) {
	   	       if (file.isFile()) {
	   	    	   path = file.getName();
	   	    	   if(path.startsWith(Integer.toString(level)) == true)
	   	    	   {
	   	    		  return path;
	   	    	   } 
	   	       }
	   	   }
	   	  return "";
	}
	private void init_level()
	{
		 current_file = "Levels/" + get_path();
		 System.out.println("current_file " + current_file);
		 Read_file r = new Read_file(current_file);
  	     for(int i = 0; i < r.count_lines(); i++)
  	     {
  		     Line line = r.get_line(i);
  	         example_lines.add(line);
  	     }
	}
	private void start_game()
	{
   	   init_level();
	}
	private void next_level()
	{
		level++;
		example_lines.clear();
		directions.clear();
		lines.clear();
		init_level();
	}
	private void show_directions(ArrayList<Vector_direction> array, String name_arraylist)
	{
		for(int i = 0; i < array.size(); i++)
		{
			System.out.println(name_arraylist  + "[" + i + "] = " + array.get(i).n_x
					+ " " + array.get(i).n_y);
		}
	}
	private void show_new_lines(ArrayList<Line> array, String name_arraylist)
	{
		System.out.println("-----------------------------------------");
		for(int i = 0; i < new_lines.size(); i++)
		{
			System.out.println("name_arraylist: " + "[" + i + "] = " + array.get(i).x1
					 + " " +	array.get(i).y1 + " " 
					+ array.get(i).x2 + " " + array.get(i).y2	);
		}
	}
	private void correct_directions()
	{
		int r_i = 0;
		int count_repeat = 0;
		Boolean fix_position = false;
		double d_n_x = 0;
		double d_n_y = 0;
		for(int i = 0; i < directions.size(); i++)
		{
			d_n_x = directions.get(i).n_x;
			d_n_y = directions.get(i).n_y;
			if((d_n_x == -1.0 || d_n_x == 0.0 || d_n_x == 1.0) &&
			   (d_n_y == -1.0 || d_n_y == 0.0 || d_n_y == 1.0))
			{
			
				if(fix_position == false)
				{
					r_i = i;
					fix_position = true;
				}
				count_repeat++;
			}
			if(count_repeat == 5)
			{
				Line l1 = new Line(lines.get(r_i).x1, lines.get(r_i).y1, lines.get(i).x2, lines.get(i).y2);
				new_lines.add(l1);
				count_repeat = 0;
				fix_position = false;
			}
		}
	}
		public void paint(Graphics g)
		{
	        super.paint(g);
	        Graphics2D g2 = (Graphics2D) g;
	        for(int i = 0; i < lines.size(); i++)
	        {
	        	g2.setStroke(new BasicStroke(5));
	        	g2.draw(new Line2D.Double(lines.get(i).x1, lines.get(i).y1, lines.get(i).x2, lines.get(i).y2));
	        }
	        for(int i = 0; i < figures.size(); i++)
	        {
	        	g2.setStroke(new BasicStroke(2));
	        	g2.draw(new Line2D.Double(figures.get(i).x1,  figures.get(i).y1,  figures.get(i).x2,  figures.get(i).y2));
	        }
	        if(adding_figure == true)
	        g2.drawLine(previous_x, previous_y, x, y);
	        
	        for(int i = 0; i < example_lines.size(); i++)
	        {
	           	g2.setStroke(new BasicStroke(1));
	           	g2.draw(new Line2D.Double(example_lines.get(i).x1,  example_lines.get(i).y1,  example_lines.get(i).x2,  example_lines.get(i).y2));
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
	    		Vector_direction v = new Vector_direction(n[0], n[1]);
	    		directions.add(v);
	    	}
	    //	show_directions(directions, "directions: ");
	    	if(adding_figure == false)
	    	{
	    		correct_directions();
	    		for(int i = 0; i < new_lines.size(); i++)
		    	{
		    		double[] n = normalize_vector(new_lines.get(i).x2 - new_lines.get(i).x1, new_lines.get(i).y2 - new_lines.get(i).y1);
		    		Vector_direction v = new Vector_direction(n[0], n[1]);
		    		new_directions.add(v);
		    	}
	    	//	show_directions(new_directions, "new_directions ");
	    		Identify_figure i_f = new Identify_figure(current_file);
		    	if(i_f.check_figure(new_directions) == true)
		    	{
		    		JOptionPane.showMessageDialog(null, "Congratulations!!! It Is correct!");
		    		if(level < max_level) 
		    		  next_level();
		    	}
		    	else
		    	{
		    		JOptionPane.showMessageDialog(null, "Figure is not correct! Try again!");
		    	}
	    	}
	    	directions.clear();
	    	new_directions.clear();
	     	lines.clear();
	     	new_lines.clear();
	     	repaint();
        }
    }
    private double[] normalize_vector(double a, double b)
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
	        	   Writer w = new Writer(new_figure);
	        	   String str = "";
	        	   for(int i = 0; i < figures.size(); i++)
	        	   {
	        		   str = Integer.toString((int)figures.get(i).x1)  + " " + Integer.toString((int)figures.get(i).y1) + 
	        				   " " + Integer.toString((int)figures.get(i).x2) + " " + Integer.toString((int)figures.get(i).y2);
	        		   w.write(str);
	        	   }
	           }
	           if(key == KeyEvent.VK_SHIFT)
	           {
	        	   adding_figure = true;
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