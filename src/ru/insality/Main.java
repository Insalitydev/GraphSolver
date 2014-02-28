package ru.insality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import ru.insality.Graph.States;

/**
 * GraphSolver - Программа для работы с графами.
 * Возможно представлять графы в нескольких представлениях, а также переключаться между ними
 * Присутствует ряд алгоритмов для работы нам графом 
 * */
public class Main {
	
//	TODO: 
	public static boolean isShowWeight = true;
	
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
			graph.setState(States.LIST_ADJ);
			graph.printGraph();
			graph.setState(States.ARR_ADJ);
			graph.printGraph();
			graph.setState(States.ARR_INC);
			graph.printGraph();
			graph.setState(States.ARR_ADJ);
			graph.printGraph();
			graph.setState(States.LIST_ADJ);
			graph.printGraph();
			graph.setState(States.ARR_INC);
			graph.printGraph();
			graph.setState(States.ARR_ADJ);
			graph.printGraph();
			System.out.println(graph.isAdjacency(0, 1));
			System.out.println(graph.isAdjacency(1, 0));
			System.out.println(graph.isAdjacency(1, 3));
			System.out.println(graph.isAdjacency(3, 1));
			System.out.println(graph.isAdjacency(2, 1));
			graph.bfs(0);
			graph.dfs(0);
			System.out.println(graph.getDegree(0));
			System.out.println(graph.getDegree(1));
			System.out.println(graph.getDegree(2));
			System.out.println(graph.getDegree(3));
		
		}
		
	}

}
