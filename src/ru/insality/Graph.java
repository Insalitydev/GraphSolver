package ru.insality;

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {

	/**
	 * Тип представления графа. ARR - массив, LIST - список, INC(incidence) -
	 * инцидентности, ADJ(adjacency) - смежности
	 */
	enum States {
		ARR_INC, ARR_ADJ, LIST_ADJ
	}

	private States state;
	private int N, M;

	public int[][] arr_inc;
	public int[][] arr_adj;
	public ArrayList<Integer>[] list_adj;

	/** Создает граф по всем параметрам. Достаются из GraphParser */
	public Graph(int[][] arr_inc, int[][] arr_adj,
			ArrayList<Integer>[] list_adj, States state, int N, int M) {
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

	public States getState() {
		return state;
	}

	/** обнуляет (заполняет начальное состояние) для графа определенного вида */
	private void initGraph(States state) {
		for (int i = 0; i < N; i++) {
			if (state == States.ARR_ADJ)
				for (int j = 0; j < N; j++) {
					arr_adj[i][j] = 0;
				}
			if (state == States.ARR_INC)
				for (int j = 0; j < M; j++) {
					arr_inc[i][j] = 0;
				}
			if (state == States.LIST_ADJ)
				list_adj[i] = new ArrayList<Integer>();
		}
	}

	/**
	 * Считает кол-во ребер и записывает их в параметр M. Работает только в
	 * режиме ARR_ADJ
	 */
	private void countEdges() {
		int edges = 0;
		if (getState() == States.ARR_ADJ) {
			for (int i = 0; i < N; i++) {
				for (int j = i; j < N; j++) {
					if (arr_adj[i][j] == 1) {
						edges++;
					}
				}
			}
		}
		this.M = edges;
	}

	/** Convert graph to choosen state */
	public void setState(States state) {
		Log.print(Log.system, "Converting graph...");
		switch (state) {
		case ARR_INC:
			convertToArrAdj();
			arr_inc = new int[N][M];
			initGraph(States.ARR_INC);

			int curEdge = 0;
			for (int i = 0; i < N; i++) {
				for (int j = i; j < N; j++) {
					if (arr_adj[i][j] == 1) {
						arr_inc[i][curEdge] = 1;
						arr_inc[j][curEdge] = 1;
						curEdge++;
					}
				}
			}
			assert (curEdge <= M);
			break;
		case ARR_ADJ:
			convertToArrAdj();
			break;
		case LIST_ADJ:
			convertToArrAdj();
			initGraph(States.LIST_ADJ);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (arr_adj[i][j] == 1) {
						list_adj[i].add(j + 1);
					}
				}
			}

			break;
		default:
			Log.print(Log.error, "Unexpected switch in convertGraph(Graph)");
			break;
		}
		
		this.state = state;
	}

	/** Convert graph to Adjacency array */
	private void convertToArrAdj() {
		Log.print(Log.system, "Converting graph to ARR_ADJ");

		switch (getState()) {
		case ARR_INC:
			initGraph(States.ARR_ADJ);
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
			initGraph(States.ARR_ADJ);
			for (int i = 0; i < N; i++) {
				for (int vertex : list_adj[i]) {
					arr_adj[i][vertex - 1] = 1;
				}
			}
			break;
		default:
			break;
		}

		this.state = States.ARR_ADJ;
		// After convert have to count edges.
		countEdges();
	}
}
