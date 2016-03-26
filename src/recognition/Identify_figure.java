package recognition;

import java.util.ArrayList;

import user_interface.Line;

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
	private int choose_source_direction(ArrayList<Vector_direction> player_direction)
	{
		Read_file f2 = new Read_file(path_example);
		double epsilon = 0.25;
		Vector_direction temp1;
		int count_wrong = 0;
		int count_correct = 0;
		for(int i = 0; i < f2.count_lines(); i++)
		{
			temp1 = f2.get_direction(i);
			count_wrong = 0;
			count_correct = 0;
			double s_n_x = 0; //source figure
			double s_n_y = 0;
			double p_n_x = 0;
			double p_n_y = 0;
			for(int j = 0; j < player_direction.size(); j++)
			{
				s_n_x = temp1.n_x;
				s_n_y = temp1.n_y;
				p_n_x = player_direction.get(j).n_x;
				p_n_y = player_direction.get(j).n_y;
				if(Math.abs( s_n_x - p_n_x) <= epsilon &&
				   Math.abs( s_n_y - p_n_y) <= epsilon	)
				{
					count_correct++;
				}
				else
				{
					count_wrong++;
				}
			}
			if(count_correct > count_wrong-2) return i;
		}
		return -1;
	}
	public Boolean check_figure(ArrayList<Vector_direction> array)
	{
		Read_file f = new Read_file(path_example);
		double epsilon = 0.25;
		System.out.println("check_figure --------------");
		int index_source_direction = 0;
		int count_wrong_direction = 0;
		/*Vector_direction temp2;
		for(int i = 0; i < f.count_lines(); i++)
		{
			temp2 = f.get_direction(i);
			System.out.println("source_directions: " + temp2.n_x + " " + temp2.n_y);
		}*/
		//Get the first 10 elements in direct_list to receive source_direction
		 ArrayList<Vector_direction> temp_dir = new ArrayList<Vector_direction>();
		 if(array.size() > 11)
		 {
			 temp_dir.addAll(array.subList(0, 10));
		 }
		 else
		 {
			 System.out.println(" < 10 elements");
			 return false;
		 }
		 index_source_direction = choose_source_direction(temp_dir);
		 temp_dir.clear();
		 System.out.println("index_source_direction " + index_source_direction);
		 if(index_source_direction == -1)
		 {
			 for(int z = 0; z < array.size(); z++)
			 {
				 System.out.println(array.get(z).n_x + "  " + array.get(z).n_y);
			 }
			 System.out.println("index_source_direction = -1 - exit ");
		     return false;
		 }
		Vector_direction source_direction = f.get_direction(index_source_direction);
		int current_i_wrong = 0 ;
		int count_sides = f.count_lines()-1;
		 System.out.println("count_sides " + count_sides);
		for(int i = 0; i < array.size(); i++)
		{
    		if(Math.abs(source_direction.n_x - array.get(i).n_x) <= epsilon &&
			   Math.abs(source_direction.n_y - array.get(i).n_y) <= epsilon	)
			{
				count_wrong_direction = 0;
				current_i_wrong = i;
				show_debug(array, i, "correct");
			}
			else
			{
				show_debug(array, i, "not correct");
				count_wrong_direction++;
			}
			if(count_wrong_direction == 5)
			{
				temp_dir.addAll(array.subList(current_i_wrong, i));
				index_source_direction = choose_source_direction(temp_dir); //like for the first element
				if(index_source_direction == -1)
				{
					System.out.println("-1-1-1-1-1-1-1-1--1-1-1-1-1-1-1-: " + count_sides);
					return false;
				}
				temp_dir.clear();
				System.out.println("move to next side of figure " + index_source_direction);
				count_sides--;
				//if(index_source_direction == f.count_lines()) return false;
				source_direction = f.get_direction(index_source_direction);
				i = i - 5;
				count_wrong_direction = 0;
			}
			if(count_wrong_direction == 12)
			{
				System.out.println(" return false ");
				return false;
			}
		}
		//We have to check if player drew all lines
		System.out.println("count_sides " + count_sides);
		if(count_sides == 0)
			return true;
		else 
			return false;
	}
	public Identify_figure(String path_to_file)
	{
		path_example = path_to_file;
	}
}
