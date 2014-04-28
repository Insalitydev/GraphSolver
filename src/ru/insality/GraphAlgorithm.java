package ru.insality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import ru.insality.Graph.States;

public class GraphAlgorithm {

	private static Graph getClearGraph(int N, int M) {
		// init:
		int[][] arr_inc = new int[N][M];
		int[][] arr_adj = new int[N][N];
		@SuppressWarnings("unchecked")
		ArrayList<ListNode>[] list_adj = new ArrayList[N];

		// init data:
		for (int i = 0; i < N; i++) {
			Arrays.fill(arr_adj[i], 0);
			Arrays.fill(arr_inc[i], 0);
			list_adj[i] = new ArrayList<ListNode>();
		}

		return new Graph(arr_inc, arr_adj, list_adj, States.ARR_ADJ, N, M);
	}

	/** Ищет остовное дерево, сумма ребер которого минимальна. O(N^2) */
	public static int Prima(Graph input) {
		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Prima algorithm:");

		// init:
		int N = input.getVertexCount();
		int M = input.getEdgeCount();
		int[][] arr_inc = new int[N][M];
		int[][] arr_adj = new int[N][N];
		@SuppressWarnings("unchecked")
		ArrayList<ListNode>[] list_adj = new ArrayList[N];

		// init data:
		for (int i = 0; i < N; i++) {
			Arrays.fill(arr_adj[i], 0);
			Arrays.fill(arr_inc[i], 0);
			list_adj[i] = new ArrayList<ListNode>();
		}

		// prima init:
		int start = 0;
		int[] distance = new int[N];
		int index = 0, u;
		boolean[] visited = new boolean[N];
		int[] prev = new int[N];

		for (int i = 0; i < N; i++) {
			distance[i] = 9_999_999;
			prev[i] = 0;
			visited[i] = false;
		}
		distance[start] = 0;
		System.out.println("Минимальное остовное дерево:");
		for (int count = 0; count < N; count++) {
			double min = 9_999_999;
			for (int i = 0; i < N; i++)
				if (!visited[i] && distance[i] <= min) {
					min = distance[i];
					index = i;
				}
			u = index;
			visited[u] = true;
			for (int i = 0; i < N; i++)
				if (!visited[i] && (input.arr_adj[u][i] > 0)
						&& distance[u] < 9_999_999
						&& input.arr_adj[u][i] < distance[i]) {
					distance[i] = input.arr_adj[u][i];
					prev[i] = u;
				}
			if (prev[u] != u)
				System.out.println("Prima: edge: " + (prev[u] + 1) + " "
						+ (u + 1));
		}
		// return new Graph(arr_inc, arr_adj, list_adj, States.ARR_ADJ, N, M);
		return 0;
	}

	public static int Kruskal(Graph input) {
		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Kruskal algorithm:");

		// init:
		int N = input.getVertexCount();
		int M = input.getEdgeCount();
		int[][] arr_inc = new int[N][M];
		int[][] arr_adj = new int[N][N];
		@SuppressWarnings("unchecked")
		ArrayList<ListNode>[] list_adj = new ArrayList[N];

		// init data:
		for (int i = 0; i < N; i++) {
			Arrays.fill(arr_adj[i], 0);
			Arrays.fill(arr_inc[i], 0);
			list_adj[i] = new ArrayList<ListNode>();
		}

		// Kruskal init:
		// For Krustal needed list of edges:
		DSF dsf = new DSF(input.getVertexCount());
		ArrayList<Edge> edges = input.getEdges();
		Collections.sort(edges);

		int cost = 0;
		for (Edge e : edges)
			if (dsf.union(e.u, e.v)) {
				System.out.println("Kruskal: edge " + (e.u + 1) + "-"
						+ (e.v + 1));
				cost += e.w;
			}
		return cost;
	}

	public static int Boruvka(Graph input) {
		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Boruvka algorithm:");

		// init:
		int N = input.getVertexCount();
		int M = input.getEdgeCount();
		int[][] arr_inc = new int[N][M];
		int[][] arr_adj = new int[N][N];
		@SuppressWarnings("unchecked")
		ArrayList<ListNode>[] list_adj = new ArrayList[N];

		// init data:
		for (int i = 0; i < N; i++) {
			Arrays.fill(arr_adj[i], 0);
			Arrays.fill(arr_inc[i], 0);
			list_adj[i] = new ArrayList<ListNode>();
		}

		// Boruvka init:
		ArrayList<Edge> edges = input.getEdges();
		ArrayList<Edge> resultEdges = new ArrayList<>();
		DSF dsf = new DSF(input.getVertexCount());
		Edge[] min = new Edge[input.getVertexCount()];

		while (resultEdges.size() < input.getVertexCount() - 1) {
			Arrays.fill(min, new Edge(0, 0, 9_999_999));
			for (Edge e : edges) {
				if (dsf.set(e.u) != dsf.set((e.v))) {
					if (e.w < min[e.u].w)
						min[e.u] = e;
				}
			}
			for (int i = 0; i < input.getVertexCount(); i++) {
				if (min[i].w != 9_999_999) {
					if (dsf.set(min[i].u) != dsf.set((min[i].v))) {
						resultEdges.add(min[i]);
						System.out.println("Boruvka: edge " + (min[i].u + 1)
								+ " " + (min[i].v + 1));
					}
					dsf.union(min[i].u, min[i].v);
				}
			}
		}
		return 0;
	}

	/** Ищет эйлеров цикл в графе */
	public static int Fleury(Graph input) {
		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Fleury algorithm:");

		Graph out = getClearGraph(input.getVertexCount(), input.getEdgeCount());
		// copy graph:
		for (int i = 0; i < out.getVertexCount(); i++)
			for (int j = 0; j < out.getVertexCount(); j++) {
				out.arr_adj[i][j] = input.arr_adj[i][j];
			}

		out.setState(States.ARR_ADJ);

		System.out.println("Steps count: <= " + out.getEdgeCount());
		// Fleury:
		int curNode = 0;
		int nextNode = 0;
		for (int i = 0; i < out.getEdgeCount(); i++) {
			// Find the next node and del edge:
			for (int j = 0; j < out.getVertexCount(); j++) {
				if (out.arr_adj[curNode][j] > 0 && out.getDegree(curNode) > 1) {
					nextNode = j;
					out.arr_adj[curNode][j] = 0;
					out.arr_adj[j][curNode] = 0;
					break;
				}

				if (out.arr_adj[curNode][j] > 0 && out.getDegree(curNode) == 1) {
					nextNode = j;
					out.arr_adj[curNode][j] = 0;
					out.arr_adj[j][curNode] = 0;
					break;
				}

			}
			if (curNode == nextNode)
				break;
			System.out.println("Edge: " + (curNode + 1) + " " + (nextNode + 1));
			curNode = nextNode;

		}

		return 0;
	}

	/** Алгоритм рид */
	public static int Rhid(Graph input) {

		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Rhid algorithm");

		Graph out = getClearGraph(input.getVertexCount(), input.getEdgeCount());

		// copy graph:
		for (int i = 0; i < out.getVertexCount(); i++)
			for (int j = 0; j < out.getVertexCount(); j++) {
				out.arr_adj[i][j] = input.arr_adj[i][j];
			}

		int curNode = 0;
		int nextNode = 0;
		Stack<Integer> head = new Stack<Integer>();
		head.push(curNode);
		Stack<Integer> tail = new Stack<Integer>();

		while (!head.isEmpty()) {
			while (out.getDegree(head.peek()) > 0) {
				// Find the next node and del edge:
				curNode = head.peek();
				for (int j = 0; j < out.getVertexCount(); j++) {
					if (out.arr_adj[curNode][j] > 0) {
						nextNode = j;
						head.push(nextNode);
						out.arr_adj[curNode][j] = 0;
						out.arr_adj[j][curNode] = 0;
						break;
					}
				}
			}
			
			while (!head.isEmpty() && out.getDegree(head.peek()) == 0){
				tail.push(head.pop());
			}
		}
		System.out.println("Rhid way:");
		while (!tail.isEmpty()){
			System.out.print((tail.pop()+1) + " ");
		}
		System.out.println();
		return 0;
	}
}
