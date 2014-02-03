package ru.insality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import ru.insality.Graph.type;

/** Загружает файл и возвращает объект графа в нужном представлении */
public class GraphParser {

	public static Graph parseGraph(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);

		// parsing type of present
		String nextLine = sc.nextLine().trim();
		type state;
		try {
			state = type.valueOf(nextLine);
		} catch (IllegalArgumentException e) {
			Log.print(Log.error, "Error in parsing first string: check graph's present");
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
			Log.print(Log.error, "UNEXPECTED SWITCH IN GraphParser (N and M reading)!");
			break;
		}

		// Parsing the graph's data
		Log.print(Log.system, "Start parsing graph's data");
		Log.print(Log.system, "Type\t\t" + state);
		Log.print(Log.system, "Vertex Count:\t" + N);
		Log.print(Log.system, "Edge Count:\t" + M);
		int[][] arr_inc = new int[N][M];
		boolean[][] arr_adj = new boolean[N][N];
		ArrayList<ArrayList<Integer>> list_adj = new ArrayList<ArrayList<Integer>>();

		// init data:
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				arr_adj[i][j] = false;
			}
			for (int j = 0; j < M; j++) {
				arr_inc[i][j] = 0;
			}
		}

		switch (state) {
		case ARR_INC:
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					arr_inc[i][j] = sc.nextInt();
				}
			}
			break;
		case ARR_ADJ:
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (sc.nextInt() != 0)
						arr_adj[i][j] = true;
				}
			}
			break;
		case LIST_ADJ:
			//TODO: DONT IMPLEMENTED
			break;
		default:
			Log.print(Log.error, "UNEXPECTED SWITCH IN GraphParser (parsing data)!");
			break;
		}

		sc.close();
		return null;
	}
}
