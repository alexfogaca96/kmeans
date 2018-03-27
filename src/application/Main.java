package application;

import java.util.LinkedList;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import algorithm.kmeans.DataPoint;
import algorithm.kmeans.KMeans;


public class Main
{    
	public static void main(final String[] args)
    {
		final int groups = 30;
		final double error = 1.0;
	
//		double[][] data = initializeData();
//		LinkedList<DataPoint> result = KMeans.executeKMeans(data, groups, error);
//		SwingUtilities.invokeLater (() -> {
//			ScatterPlot scatterPlot = new ScatterPlot("Dados", result, groups);
//				
//			scatterPlot.setSize ( 800, 400 );
//			scatterPlot.setLocationRelativeTo(null);
//			scatterPlot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//			scatterPlot.setVisible(true);
//		});
		
		double[][] data = initializeData2();
		LinkedList<DataPoint> result = KMeans.executeKMeans(data, groups, error);
		
		SwingUtilities.invokeLater (() -> {
			ScatterPlot scatterPlot = new ScatterPlot("Dados", result, groups);
			
			scatterPlot.setSize ( 800, 400 );
			scatterPlot.setLocationRelativeTo(null);
		    scatterPlot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    scatterPlot.setVisible(true);
		});
    }
	
	static double[][] initializeData ()
	{
		return new double[][] {
		     { 3.0, 10.0 },
		     { 3.5, 10.5 },
		    { 10.0, 10.0 },
		    { 10.5, 10.5 },
		     { 3.0, 3.0 },
		     { 3.5, 3.5 },
		    { 10.0, 3.0 },
		    { 10.5, 3.5 } };
	}
	
	static double[][] initializeData2()
	{
		return new double[][]  {
			{ 35, 140 },
			{ 110, 176 },
			{ 54, 156 },
			{ 62, 159 },
			{ 91, 187 },
			{ 66, 162 },
			{ 57, 153 },
			{ 44, 188 },
			{ 88, 150 },
			{ 48, 185 },
			{ 112, 143 },
			{ 94, 140 },
			{ 70, 167 },
			{ 71, 148 },
			{ 95, 155 },
			{ 50, 155 },
			{ 97, 171 },
			{ 101, 141 },
			{ 45, 149 },
			{ 90, 153 },
			{ 49, 174 },
			{ 112, 149 },
			{ 71, 143 },
			{ 110, 193 },
			{ 107, 183 },
			{ 100, 158 },
			{ 61, 172 },
			{ 64, 154 },
			{ 40, 157 },
			{ 40, 169 },
			{ 48, 197 },
			{ 78, 197 },
			{ 97, 141 },
			{ 118, 146 },
			{ 43, 164 },
			{ 39, 193 },
			{ 41, 189 },
			{ 42, 182 },
			{ 89, 178 },
			{ 54, 188 },
			{ 46, 147 },
			{ 112, 187 },
			{ 103, 189 },
			{ 81, 167 },
			{ 97, 181 },
			{ 61, 164 },
			{ 55, 181 },
			{ 113, 185 },
			{ 49, 187 },
			{ 61, 165 },
			{ 36, 194 },
			{ 107, 191 },
			{ 48, 181 },
			{ 54, 200 },
			{ 115, 195 },
			{ 114, 200 },
			{ 61, 149 },
			{ 37, 182 },
			{ 38, 175 },
			{ 82, 141 },
			{ 35, 180 },
			{ 109, 188 },
			{ 46, 196 },
			{ 63, 168 },
			{ 96, 185 },
			{ 115, 190 },
			{ 52, 198 },
			{ 72, 177 },
			{ 55, 200 },
			{ 55, 174 },
			{ 44, 146 },
			{ 90, 188 },
			{ 106, 183 },
			{ 73, 156 },
			{ 101, 149 },
			{ 73, 189 },
			{ 60, 183 },
			{ 50, 187 },
			{ 82, 180 },
			{ 112, 156 },
			{ 109, 148 },
			{ 38, 148 },
			{ 83, 192 },
			{ 107, 167 },
			{ 40, 186 },
			{ 87, 155 },
			{ 51, 177 },
			{ 71, 199 },
			{ 43, 184 },
			{ 96, 161 },
			{ 95, 194 },
			{ 115, 150 },
			{ 86, 182 },
			{ 51, 147 },
			{ 67, 196 },
			{ 81, 173 },
			{ 53, 151 },
			{ 80, 182 },
			{ 44, 189 },
			{ 64, 190 }
		};
	}
	
}
