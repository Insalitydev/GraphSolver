package ru.insality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {

	/**
	 * Тип представления графа. ARR - массив, LIST - список, INC(incidence) -
	 * инцидентности, ADJ(adjacency) - смежности
	 */
	enum States {
		ARR_INC, ARR_ADJ, LIST_ADJ
	}

	public int[][] arr_inc;
	public int[][] arr_adj;
	public ArrayList<Integer>[] list_adj;
	boolean[] isVisited;

	private States state;
	private int N, M;

	/** Создает граф по всем параметрам. Достаются из GraphParser */
	public Graph(int[][] arr_inc, int[][] arr_adj,
			ArrayList<Integer>[] list_adj, States state, int N, int M) {
		this.arr_inc = arr_inc;
		this.arr_adj = arr_adj;
		this.list_adj = list_adj;
		this.state = state;
		this.N = N;
		this.M = M;
		this.isVisited = new boolean[N];
	}

	/** Breadth-first search algorithm */
	public void bfs(int from) {
		Log.print(Log.system, "Start the BFS algorythm, starting from: " + from);
		clearNodes();

		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(from);
		System.out.print(from + " ");
		isVisited[from] = true;
		while (!queue.isEmpty()) {
			int node = queue.remove();
			int child = -1;
			while ((child = getUnvisitedChildNode(node)) != -1) {
				isVisited[child] = true;
				System.out.print(child + " ");
				queue.add(child);
			}
		}
		System.out.println();
		clearNodes();
	}

	/** Deep-first search algorithm */
	public void dfs(int from) {
		Log.print(Log.system, "Start the DFS algorythm, starting from: " + from);
		clearNodes();

		Stack<Integer> stack = new Stack<Integer>();
		stack.push(from);
		System.out.print(from + " ");
		isVisited[from] = true;
		while (!stack.isEmpty()) {
			int node = stack.peek();
			int child = getUnvisitedChildNode(node);
			if (child != -1) {
				isVisited[child] = true;
				System.out.print(child + " ");
				stack.push(child);
			} else {
				stack.pop();
			}
		}
		System.out.println();
		clearNodes();
	}

	/**
	 * @return first unvisited (from isVisited[]) vertex child from vertex node.
	 *         If have not vertex: return -1
	 */
	private int getUnvisitedChildNode(int node) {
		int result = -1;
		switch (getState()) {
		case ARR_ADJ:
			for (int i = 0; i < N; i++)
				if (arr_adj[node][i] != 0 && !isVisited[i]) {
					result = i;
					break;
				}
			break;
		case ARR_INC:
			for (int i = 0; i < M; i++)
				if (arr_inc[node][i] != 0)
					// Finding incid. vertex to our node
					for (int j = 0; j < N; j++)
						if (arr_inc[j][i] != 0 && j != node) {
							result = j;
							break;
						}
			break;
		case LIST_ADJ:
			for (int curNode : list_adj[node])
				if (!isVisited[curNode]) {
					result = curNode;
					break;
				}
			break;
		default:
			Log.print(Log.error, "Error in switch, unexpected graph State");
			break;
		}

		return result;

	}

	/** обнуляет (заполняет начальное состояние) для графа определенного вида */
	private void clearGraph(States state) {
		for (int i = 0; i < N; i++) {
			if (state == States.ARR_ADJ)
				Arrays.fill(arr_adj[i], 0);
			if (state == States.ARR_INC)
				Arrays.fill(arr_inc[i], 0);
			if (state == States.LIST_ADJ)
				list_adj[i] = new ArrayList<Integer>();
		}
	}

	/** Convert graph to chosen state */
	public void setState(States state) {
		Log.print(Log.system, "Converting graph...");
		switch (state) {
		// ARR_ADJ -> ARR_INC
		case ARR_INC:
			convertToArrAdj();
			arr_inc = new int[N][M];
			clearGraph(States.ARR_INC);

			int curEdge = 0;
			for (int i = 0; i < N; i++) {
				for (int j = i; j < N; j++) {
					if (arr_adj[i][j] != 0) {
						arr_inc[i][curEdge] = arr_adj[i][j];
						arr_inc[j][curEdge] = arr_adj[i][j];
						curEdge++;
					}
				}
			}
			assert (curEdge <= M);
			break;
		// ANYTHING -> ARR_ADJ
		case ARR_ADJ:
			convertToArrAdj();
			break;
		// ARR_ADJ -> LIST_ADJ
		case LIST_ADJ:
			convertToArrAdj();
			clearGraph(States.LIST_ADJ);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (arr_adj[i][j] != 0) {
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
		// ARR_INC -> ARR_ADJ
		case ARR_INC:
			clearGraph(States.ARR_ADJ);
			for (int j = 0; j < M; j++) {
				boolean isFirstFinded = false;
				int posFirst = 0;

				for (int i = 0; i < N; i++) {
					if (arr_inc[i][j] != 0) {
						if (!isFirstFinded) {
							isFirstFinded = true;
							posFirst = i;
						} else {
							arr_adj[posFirst][i] = arr_inc[i][j];
							arr_adj[i][posFirst] = arr_inc[i][j];
							isFirstFinded = false;
						}
					}
				}
			}
			break;
		// LIST_ADJ -> ARR_ADJ
		case LIST_ADJ:
			clearGraph(States.ARR_ADJ);
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

	public States getState() {
		return state;
	}

	/** Выводит информацию о графе и сам граф на консоль */
	public void printGraph() {
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

	/** Выводит списки посещенных вершин */
	public void printVisited() {
		Log.print(Log.system, "Printing visited vertexes");
		for (int i = 0; i < N; i++)
			if (isVisited[i])
				System.out.print((i + 1) + ", ");
		System.out.println();
	}

	/** Clear the isVisited array to false */
	private void clearNodes() {
		Arrays.fill(isVisited, false);
	}

	/**
	 * Считает кол-во ребер и записывает их в параметр M. Работает только в
	 * режиме ARR_ADJ
	 */
	private void countEdges() {
		int edges = 0;
		if (getState() == States.ARR_ADJ)
			for (int i = 0; i < N; i++)
				for (int j = i; j < N; j++)
					if (arr_adj[i][j] != 0)
						edges++;
		this.M = edges;
	}
}
