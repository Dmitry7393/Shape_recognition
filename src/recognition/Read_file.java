package recognition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read_file {
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
	private void get_vectors(String source_string)
	{
		String[] arr = source_string.split(" ");
		int[] s = new int[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			s[i] = Integer.parseInt(arr[i]) ;
		}
		double[] n = normalize_vector(s[2]-s[0], s[3] - s[1]);
		System.out.println("  " + n[0] + " " + n[1]);
	} 
	public Read_file(String path)
	{
		//Reading from file
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(path));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				System.out.println(sCurrentLine);
				get_vectors(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}	
}
