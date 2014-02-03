package ru.insality;

import java.util.ArrayList;

public class Graph {

	/**
	 * Тип представления графа. ARR - массив, LIST - список, INC(incidence) -
	 * инцидентности, ADJ(adjacency) - смежности
	 */
	static enum type {
		ARR_INC, ARR_ADJ, LIST_ADJ
	}

	private type state;
	public int[][] arr_inc;
	public boolean[][] arr_adj;
	public ArrayList<ArrayList<Integer>> list_adj;

	public void printGraph(){
		// TODO: Вывод графа и его информации на консоль
	}
	
	
	public type getState() {
		return state;
	}

	public void setState(type state) {

		// TODO: do a graph's present switch
		this.state = state;
	}
}
