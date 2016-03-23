package recognition;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Identify_figure {
	public Boolean check_figure(ArrayList<Vector_direction> array)
	{
		Read_file f = new Read_file("D:/square1.txt");
		double epsilon = 0.03;
		System.out.println("Points ");
		int index_source_direction = 0;
		Vector_direction source_direction = f.get_direction(index_source_direction);
		
		int count_wrong_direction = 0;
		for(int i = 0; i < array.size(); i++)
		{
			if(Math.abs(Math.abs(source_direction.n_x) - Math.abs(array.get(i).n_x)) <= epsilon &&
			   Math.abs(Math.abs(source_direction.n_y) - Math.abs(array.get(i).n_y)) <= epsilon	)
			{
				System.out.println("It is okay! ");
				//count_wrong_direction = 0;
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
		System.out.println("count_wrong_direction " + count_wrong_direction);
		return true;
	}
	public Identify_figure()
	{
		
	}
}
