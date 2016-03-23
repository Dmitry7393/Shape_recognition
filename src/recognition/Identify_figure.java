package recognition;

import java.util.ArrayList;

public class Identify_figure {
	private String path_example = "";
	private void show_debug(ArrayList<Vector_direction> array, int i, String str)
	{
		if(array.get(i).n_x >= 0 && array.get(i).n_y >= 0)
		System.out.println(str + " Right Down " + array.get(i).n_x + " " + array.get(i).n_y);
		
		if(array.get(i).n_x < 0 && array.get(i).n_y >= 0)
    	System.out.println(str + " Left Down " + array.get(i).n_x + " " + array.get(i).n_y);
		
		if(array.get(i).n_x < 0 && array.get(i).n_y < 0)
	    System.out.println(str + " Left Up " + array.get(i).n_x + " " + array.get(i).n_y);
		
		if(array.get(i).n_x >= 0 && array.get(i).n_y < 0)
	    System.out.println(str + " Right Up " + array.get(i).n_x + " " + array.get(i).n_y);
	}
	public Boolean check_figure(ArrayList<Vector_direction> array)
	{
		Read_file f = new Read_file(path_example);
		double epsilon = 0.2;
		System.out.println("Points ");
		int index_source_direction = 0;
		Vector_direction source_direction = f.get_direction(index_source_direction);
		int count_wrong_direction = 0;
		for(int i = 0; i < array.size(); i++)
		{
    		if(Math.abs(source_direction.n_x - array.get(i).n_x) <= epsilon &&
			   Math.abs(source_direction.n_y - array.get(i).n_y) <= epsilon	)
			{
				count_wrong_direction = 0;
				show_debug(array, i, "correct");
			}
			else
			{
				show_debug(array, i, "not correct");
				count_wrong_direction++;
				//System.out.println("It is not okay! ");
			}
			if(count_wrong_direction == 5)
			{
				index_source_direction++;
				if(index_source_direction == f.count_lines()) return false;
				source_direction = f.get_direction(index_source_direction);
				i = i - 5;
				count_wrong_direction = 0;
			}
			if(count_wrong_direction == 10)
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
	public Identify_figure(String path_to_file)
	{
		path_example = path_to_file;
	}
}
