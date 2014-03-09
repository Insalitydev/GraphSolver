package ru.insality;

import java.util.ArrayList;
import java.util.Arrays;

import ru.insality.Graph.States;

public class GraphAlgorithm {

	/** Ищет остовное дерево, сумма ребер которого минимальна. O(N^2)*/
	public static Graph Prima(Graph input) {
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
		int[] Dist = new int[N];
		int[] Prev = new int[N];
		boolean[] inTree = new boolean[N];
		Arrays.fill(Dist, 9_999_999);
		Arrays.fill(Prev, -1);
		Arrays.fill(inTree, false);
		Dist[0] = 0;
		inTree[0] = true;
		Prev[0] = 0;

		for (int i = 1; i < N; i++) {
			int vMin = -1;
			for (int j = 1; j < N; j++) {
				if (!inTree[j] && (vMin == -1 || Dist[j] < Dist[vMin])) {
					vMin = j;
				}
			}
//			if (Dist[vMin] == 9_999_999) {
//				Log.print(Log.error, "No MST!");
//				return null;
//			}

			inTree[vMin] = true;
			if (Prev[vMin] != -1) {
				System.out.println(vMin + "-" + Prev[vMin]);
			}

			for (int to = 0; to < N; to++) {
				if (input.arr_adj[vMin][to] < Dist[to]) {
					Dist[to] = input.arr_adj[vMin][to];
					Prev[to] = vMin;
				}
			}
		}

		return new Graph(arr_inc, arr_adj, list_adj, States.ARR_ADJ, N, M);
	}
}
