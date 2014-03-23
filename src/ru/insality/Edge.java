package ru.insality;

public class Edge implements Comparable<Edge>{
	public int u;
	public int v;
	public int w;

	Edge(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Edge edge) {
		if (w != edge.w)
			return w < edge.w ? -1 : 1;
		return 0;
	}

}
