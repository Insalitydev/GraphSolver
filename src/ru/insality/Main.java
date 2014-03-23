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
	public static boolean isShowWeight = false;
	
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
//			graph.setState(States.LIST_ADJ);
//			graph.printGraph();
//			graph.setState(States.ARR_ADJ);
//			graph.printGraph();
//			graph.setState(States.ARR_INC);
//			graph.printGraph();
//			graph.setState(States.ARR_ADJ);
//			graph.printGraph();
//			graph.setState(States.LIST_ADJ);
//			graph.printGraph();
//			graph.setState(States.ARR_INC);
//			graph.printGraph();
//			graph.setState(States.ARR_ADJ);
//			graph.printGraph();
//			graph.bfs(0);
//			graph.dfs(0);
			
			Graph primaGraph = GraphAlgorithm.Prima(graph);
			primaGraph.setState(States.LIST_ADJ);
			primaGraph.setState(States.ARR_ADJ);
			primaGraph.printGraph();
			
			System.out.println(GraphAlgorithm.Kruskal(graph));
		
		}
		
	}

}
