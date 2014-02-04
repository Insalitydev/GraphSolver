package ru.insality;

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {

	/**
	 * Тип представления графа. ARR - массив, LIST - список, INC(incidence) -
	 * инцидентности, ADJ(adjacency) - смежности
	 */
	static enum type {
		ARR_INC, ARR_ADJ, LIST_ADJ
	}

	private type state;
	private int N, M;

	public int[][] arr_inc;
	public boolean[][] arr_adj;
	public ArrayList<Integer>[] list_adj;


	public void printGraph() {
		// TODO: Вывод графа и его информации на консоль
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
					System.out.print(arr_inc[i][j] + " ");
				}
				System.out.println();
			}
			break;
		case LIST_ADJ:
			for (int i = 0; i< N; i++){
				System.out.println((i+1) + ": " + Arrays.toString(list_adj[i].toArray()));
			}
			break;
		}
	}

	public type getState() {
		return state;
	}

	public void setState(type state) {

		// TODO: do a graph's present switch
		this.state = state;
	}
}
