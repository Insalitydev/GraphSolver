package ru.insality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import ru.insality.Graph.States;

/** Loading file and return new Graph object from this file */
public class GraphParser {

	public static Graph parseGraph(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);

		// parsing type of present
		String nextLine = sc.nextLine().trim();
		States state = null;
		try {
			state = States.valueOf(nextLine);
		} catch (IllegalArgumentException e) {
			Log.print(Log.error,
					"Error in parsing first string: check graph's present");
			e.printStackTrace();
			sc.close();
			return null;
		}

		// N - vertex count, M - edge count
		int N = 0, M = 0;
		switch (state) {
		case ARR_INC:
			N = sc.nextInt();
			M = sc.nextInt();
			break;
		case ARR_ADJ:
		case LIST_ADJ:
			N = sc.nextInt();
			break;
		default:
			Log.print(Log.error,
					"UNEXPECTED SWITCH IN GraphParser (N and M reading)!");
			break;
		}

		// Parsing the graph's data
		Log.print(Log.system, "Start parsing graph's data");
		Log.print(Log.system, "Type\t\t" + state);
		Log.print(Log.system, "Vertex Count:\t" + N);
		Log.print(Log.system, "Edge Count:\t" + M);

		int[][] arr_inc = new int[N][M];
		int[][] arr_adj = new int[N][N];
		@SuppressWarnings("unchecked")
		ArrayList<ListNode>[] list_adj = new ArrayList[N];

		// clear data:
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				arr_adj[i][j] = 0;
			}
			for (int j = 0; j < M; j++) {
				arr_inc[i][j] = 0;
			}
			list_adj[i] = new ArrayList<ListNode>();
		}
		// skip to next line:
		sc.nextLine();

		// Parsing graph's struct data
		switch (state) {
		case ARR_INC:
			for (int i = 0; i < N; i++)
				for (int j = 0; j < M; j++)
					arr_inc[i][j] = sc.nextInt();
			break;
		case ARR_ADJ:
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					arr_adj[i][j] = sc.nextInt();
			break;
		// The most difficult part...
		case LIST_ADJ:
			for (int i = 0; i < N; i++) {
				String dataLine = sc.nextLine().trim();
				Log.print(Log.debug, "Parsing string: " + dataLine);
				// if vertex has no adj. another vertexes, continue:
				if (dataLine.split(":").length <= 1) {
					continue;
				}
				int curIndex = Integer.parseInt(dataLine.split(":")[0].trim());
				// minus 1 because arrays [0..]
				curIndex -= 1;
				// Some magic: создание массива из чисел второй части строки,
				// преобразование её в коллекцию, добавление в данные графа
				// Array String of adj. vertexs:
				String[] curDataStr = dataLine.split(":")[1].trim().split(" ");
				ArrayList<ListNode> curData = new ArrayList<ListNode>(
						curDataStr.length);
				for (int j = 0; j < curDataStr.length; j++) {
					// If number format: xx-yy, yy is a weight. Else weight = 1;
					if (curDataStr[j].contains("-")) {
						int curNodeData = Integer.parseInt(curDataStr[j]
								.split("-")[0]);
						int curNodeWeight = Integer.parseInt(curDataStr[j]
								.split("-")[1]);
						curData.add(new ListNode(curNodeData, curNodeWeight));
					} else
						curData.add(new ListNode(Integer
								.parseInt(curDataStr[j])));
				}
				list_adj[curIndex].addAll(curData);
			}
			break;
		default:
			Log.print(Log.error,
					"UNEXPECTED SWITCH IN GraphParser (parsing data)!");
			break;
		}

		sc.close();
		return new Graph(arr_inc, arr_adj, list_adj, state, N, M);
	}
}
