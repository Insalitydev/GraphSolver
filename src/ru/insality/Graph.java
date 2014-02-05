package ru.insality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Graph {

	/**
	 * Тип представления графа. ARR - массив, LIST - список, INC(incidence) -
	 * инцидентности, ADJ(adjacency) - смежности
	 */
	enum type {
		ARR_INC, ARR_ADJ, LIST_ADJ
	}

	private type state;
	private int N, M;

	public int[][] arr_inc;
	public int[][] arr_adj;
	public ArrayList<Integer>[] list_adj;

	/** Создает граф по всем параметрам. Достаются из GraphParser */
	public Graph(int[][] arr_inc, int[][] arr_adj,
			ArrayList<Integer>[] list_adj, type state, int N, int M) {
		this.arr_inc = arr_inc;
		this.arr_adj = arr_adj;
		this.list_adj = list_adj;
		this.state = state;
		this.N = N;
		this.M = M;
	}

	public void printGraph() {
		// TODO: Вывод графа и его информации на консоль
		Log.print(Log.system, "Printing graph's data");
		Log.print(Log.system, "Type\t\t" + state);
		Log.print(Log.system, "Vertex Count:\t" + N);
		Log.print(Log.system, "Edge Count:\t" + M);
		switch (getState()) {
		case ARR_INC:
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					System.out.print(arr_inc[i][j] + " ");
				}
				System.out.println();
			}
			break;
		case ARR_ADJ:
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					System.out.print(arr_adj[i][j] + " ");
				}
				System.out.println();
			}
			break;
		case LIST_ADJ:
			for (int i = 0; i < N; i++) {
				System.out.println((i + 1) + ": "
						+ Arrays.toString(list_adj[i].toArray()));
			}
			break;
		}
	}

	public type getState() {
		return state;
	}

	public void setState(type state) {
		// TODO: do a graph's present switch
		convertGraph(state);
		this.state = state;
	}

	/** обнуляет (заполняет начальное состояние) для графа определенного вида */
	private void initGraph(type state) {
		for (int i = 0; i < N; i++) {
			if (state == type.ARR_ADJ)
				for (int j = 0; j < N; j++) {
					arr_adj[i][j] = 0;
				}
			if (state == type.ARR_INC)
				for (int j = 0; j < M; j++) {
					arr_inc[i][j] = 0;
				}
			if (state == type.LIST_ADJ)
				list_adj[i] = new ArrayList<Integer>();
		}
	}

	private void convertToArrAdj() {

		Log.print(Log.system, "Converting graph to ARR_ADJ");

		switch (getState()) {
		case ARR_INC:
			initGraph(type.ARR_ADJ);
			for (int j = 0; j < M; j++) {
				boolean isFirstFinded = false;
				int posFirst = 0;

				for (int i = 0; i < N; i++) {
					if (arr_inc[i][j] == 1) {
						if (!isFirstFinded) {
							isFirstFinded = true;
							posFirst = i;
						} else {
							arr_adj[posFirst][i] = 1;
							arr_adj[i][posFirst] = 1;
							isFirstFinded = false;
						}
					}
				}
			}
			break;
		case LIST_ADJ:
			initGraph(type.ARR_ADJ);
			for (int i = 0; i < N; i++) {
				for (int vertex : list_adj[i]) {
					arr_adj[i][vertex-1] = 1;
				}
			}
			break;
		default:
			break;
		}

	}

	private void convertGraph(type state) {
		Log.print(Log.system, "Converting graph...");
		switch (state) {
		case ARR_INC:
			convertToArrAdj();
//		 TODO: convert to AI from AA
			break;
		case ARR_ADJ:
			convertToArrAdj();
			break;
		case LIST_ADJ:
// TODO convert to LA from AA
			break;
		default:
			Log.print(Log.error, "Unexpected switch in convertGraph(Graph)");
			break;
		}
	}

}
