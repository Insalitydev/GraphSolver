package ru.insality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import ru.insality.Graph.type;


public class Main {
	public static void main(String[] args) {
		System.out.println("Hello, GraphSolver!");
		System.out.println("Graph's presents: " + Arrays.toString(type.values()));
		
		try {
			GraphParser.parseGraph(new File("testGraph.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File " + e.toString() + "not found!");
			e.printStackTrace();
		}
		
	}

}
