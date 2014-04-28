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
			 graph = GraphParser.parseGraph(new File("testGraph2.txt"));
		} catch (FileNotFoundException e) {
			Log.print(Log.error, "File " + e.toString() + "not found!");
			e.printStackTrace();
		}
		
		if (graph != null){
			graph.printGraph();
			
//			GraphAlgorithm.Prima(graph);
//			System.out.println(GraphAlgorithm.Kruskal(graph));
//			GraphAlgorithm.Boruvka(graph);
//			GraphAlgorithm.Fleury(graph);
			GraphAlgorithm.Rhid(graph);
			Log.print(Log.system, "Program end");
		}
		
	}

}
