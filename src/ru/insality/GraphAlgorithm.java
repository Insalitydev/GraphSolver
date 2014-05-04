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

	/**
	 * 0 - euler; 1 - half-euler; 2 - not euler
	 */
	private static int checkEuler(Graph graph) {
		int count = 0;
		for (int i = 0; i < graph.getVertexCount(); i++) {
			if (graph.getDegree(i) % 2 == 1) {
				count++;
			}
		}

		if (count == 0) {
			Log.print(Log.system, "The graph is euler");
			return 0;
		}
		if (count == 2) {
			Log.print(Log.system, "The graph is half-euler");
			return 1;
		}
		Log.print(Log.system, "The graph is not euler");
		return 2;
	}

	/** Ищет эйлеров цикл в графе */
	public static int Fleury(Graph input) {
		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Fleury algorithm:");

		int startNode = 0;
		int isEuler = checkEuler(input);

		if (isEuler == 2) {
			Log.print(Log.error, "The graph is incorrect for Fleury algorithm");
			return -1;
		}

		if (isEuler == 1) {
			for (int i = 0; i < input.getVertexCount(); i++) {
				if (input.getDegree(i) % 2 == 1) {
					startNode = i;
					break;
				}
			}
		}

		Graph out = getClearGraph(input.getVertexCount(), input.getEdgeCount());
		// copy graph:
		for (int i = 0; i < out.getVertexCount(); i++)
			for (int j = 0; j < out.getVertexCount(); j++) {
				out.arr_adj[i][j] = input.arr_adj[i][j];
			}

		out.setState(States.ARR_ADJ);

		System.out.println("Steps count: <= " + out.getEdgeCount());
		// Fleury:
		int curNode = startNode;
		int nextNode = 0;
		for (int i = 0; i < out.getEdgeCount(); i++) {
			// Find the next node and del edge:
			for (int j = 0; j < out.getVertexCount(); j++) {

				int tmp1 = input.arr_adj[curNode][j];
				int tmp2 = input.arr_adj[j][curNode];
				input.arr_adj[curNode][j] = 0;
				input.arr_adj[j][curNode] = 0;
				boolean isBridge = !input.bfs(curNode, j);
				input.arr_adj[curNode][j] = tmp1;
				input.arr_adj[j][curNode] = tmp2;
				// System.out.println((curNode+1) + " " + (j+1) + " " +
				// isBridge);

				if (out.arr_adj[curNode][j] > 0 && !isBridge) {
					nextNode = j;
					out.arr_adj[curNode][j] = 0;
					out.arr_adj[j][curNode] = 0;
					break;
				}

				if (out.arr_adj[curNode][j] > 0 && isBridge
						&& out.getDegree(curNode) == 1) {
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

	/** Алгоритм Рида */
	public static int Rhid(Graph input) {

		int startNode = 0;

		int isEuler = checkEuler(input);
		if (isEuler == 2) {
			Log.print(Log.error, "The graph is incorrect for Rid algorithm");
			return -1;
		}

		if (isEuler == 1) {
			for (int i = 0; i < input.getVertexCount(); i++) {
				if (input.getDegree(i) % 2 == 1) {
					startNode = i;
					break;
				}
			}
		}

		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Rhid algorithm");

		Graph out = getClearGraph(input.getVertexCount(), input.getEdgeCount());

		// copy graph:
		for (int i = 0; i < out.getVertexCount(); i++)
			for (int j = 0; j < out.getVertexCount(); j++) {
				out.arr_adj[i][j] = input.arr_adj[i][j];
			}

		int curNode = startNode;
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

			while (!head.isEmpty() && out.getDegree(head.peek()) == 0) {
				tail.push(head.pop());
			}
		}

		System.out.println("Rhid way:");
		while (!tail.isEmpty()) {
			System.out.print((tail.pop() + 1) + " ");
		}
		System.out.println();

		return 0;
	}

	// -=-=-==-=-kuhn ...
	static int n;
	static int k;
	static boolean[] used;
	static int[] mt;

	private static boolean tryKuhn(Graph input, int v) {
		if (used[v])
			return false;
		used[v] = true;

		for (int i = 0; i < input.getVertexCount(); i++) {
			if (input.arr_adj[v][i] == 0)
				continue;
			int to = i;
			if (mt[to] == -1 || tryKuhn(input, mt[to])) {
				mt[to] = v;
				return true;
			}
		}
		return false;
	}

	public static void Kuhn(Graph input) {
		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Kuhn algorithm:");

		// init
		int n = input.getNodeCountFirstPartite();
		int k = input.getVertexCount() - n;
		mt = new int[k];
		used = new boolean[n];
		Arrays.fill(mt, -1);

		for (int i = 0; i < n; i++) {
			Arrays.fill(used, false);
			tryKuhn(input, i);
		}

		for (int i = 0; i < k; i++)
			if (mt[i] != -1)
				System.out.println((mt[i] + 1) + " " + (i + 1));
	}

	// -=-=-=-=-=--==- Edmonds...

	static boolean[] blossom;
	static int[] p;
	static int[] q;
	static int[] base;
	
	private static int lca(int a, int b){
		boolean[] used = new boolean[n];
		for (;;){
			a = base[a];
			used[a] = true;
			if (mt[a] == -1) break;
			a = p[mt[a]];
		}
		// from b node
		for (;;){
			b = base[b];
			if (used[b]) return b;
			b = p[mt[b]];
		}
	}
	
	private static void markPath(int v, int b, int children){
		while (base[v] != b){
			blossom[base[v]] = blossom[base[mt[v]]] = true;
			p[v] = children;
			children = mt[v];
			v = p[mt[v]];
		}
	}

	private static int findPath(Graph input, int root) {
		Arrays.fill(p, -1);
		Arrays.fill(used, false);
		for (int i = 0; i < n; ++i)
			base[i] = i;

		used[root] = true;
		int qh = 0, qt = 0;
		q[qt++] = root;

		while (qh < qt) {
			int v = q[qh++];
			for (int i = 0; i < input.getVertexCount(); i++) {
				if (input.arr_adj[v][i] == 0)
					continue;

				int to = i;
				if (base[v] == base[to] || mt[v] == to)
					continue;
				if (to == root || mt[to] != -1 && p[mt[to]] != -1) {
					int curBase = lca(v, to);
					Arrays.fill(blossom, false);
					markPath(v, curBase, to);
					markPath(to, curBase, v);
					for (int j = 0; j < n; j++) {
						if (blossom[base[i]]) {
							base[i] = curBase;
							if (!used[i]) {
								used[i] = true;
								q[qt++] = i;
							}
						}
					}
				} else if (p[to] == -1) {
					p[to] = v;
					if (mt[to] == -1)
						return to;
					to = mt[to];
					used[to] = true;
					q[qt++] = to;
				}
			}
		}
		return -1;
	}

	public static void Edmonds(Graph input) {
		input.setState(States.ARR_ADJ);
		Log.print(Log.system, "Edmonds algorithm:");
		// init...
		n = input.getVertexCount();
		used = new boolean[n];
		blossom = new boolean[n];
		mt = new int[n];
		p = new int[n];
		q = new int[n];
		base = new int[n];
		Arrays.fill(mt, -1);

		for (int i = 0; i < n; i++) {
			if (mt[i] == -1) {
				int v = findPath(input, i);
				while (v != -1) {
					int pv = p[v];
					int ppv = mt[pv];
					mt[v] = pv;
					mt[pv] = v;
					v = ppv;
				}
			}
		}

		for (int i = 0; i < n; i++)
		if (mt[i] != -1)
			System.out.println((mt[i] + 1) + " " + (i + 1));
	}

}
