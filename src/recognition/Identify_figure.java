package recognition;

import java.util.ArrayList;

public class Identify_figure {
	public Boolean check_figure(ArrayList<Vector_direction> array)
	{
		Read_file f = new Read_file("Levels/square.txt");
		double epsilon = 0.03;
		System.out.println("Points ");
		int index_source_direction = 0;
		Vector_direction source_direction = f.get_direction(index_source_direction);
		
		int count_wrong_direction = 0;
		for(int i = 0; i < array.size(); i++)
		{
			if(array.get(i).n_x >= 0 && array.get(i).n_y >= 0)
    		System.out.println("Right Down" + array.get(i).n_x + " " + array.get(i).n_y);
    		
    		if(array.get(i).n_x < 0 && array.get(i).n_y >= 0)
	    	System.out.println("Left Down " + array.get(i).n_x + " " + array.get(i).n_y);
    		
    		if(array.get(i).n_x < 0 && array.get(i).n_y < 0)
		    System.out.println("Left Up " + array.get(i).n_x + " " + array.get(i).n_y);
    		
    		if(array.get(i).n_x >= 0 && array.get(i).n_y < 0)
		    System.out.println("Right Up " + array.get(i).n_x + " " + array.get(i).n_y);
			
    		if(Math.abs(source_direction.n_x - array.get(i).n_x) <= epsilon &&
			   Math.abs(source_direction.n_y - array.get(i).n_y) <= epsilon	)
			{
				count_wrong_direction = 0;
			}
			else
			{
				count_wrong_direction++;
				System.out.println("It is not okay! ");
			}
			if(count_wrong_direction == 5)
			{
				index_source_direction++;
				if(index_source_direction == 4) return false;
				source_direction = f.get_direction(index_source_direction);
				i = i - 5;
				count_wrong_direction = 0;
			}
			if(count_wrong_direction == 12)
			{
				return false;
			}
		}
		//We have to check if user drew all lines 
		System.out.println("index_source_direction " + index_source_direction);
		if(index_source_direction == f.count_lines()-1)
			return true;
		else 
			return false;
	}
	public Identify_figure()
	{
		
	}
}
