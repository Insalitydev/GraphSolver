package ru.insality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import ru.insality.Graph.States;


public class Main {
	public static void main(String[] args) {
		Log.print(Log.system, "Hello, GraphSolver!");
		Log.print(Log.system, "Graph's presents: " + Arrays.toString(States.values()));
		Graph graph = null;
		try {
			 graph = GraphParser.parseGraph(new File("testGraph.txt"));
		} catch (FileNotFoundException e) {
			Log.print(Log.error, "File " + e.toString() + "not found!");
			e.printStackTrace();
		}
		
		if (graph != null){
			graph.printGraph();
			graph.setState(States.ARR_ADJ);
			graph.printGraph();
			graph.setState(States.ARR_INC);
			graph.printGraph();
			graph.setState(States.ARR_ADJ);
			graph.printGraph();
		}
		
	}

}
