package ru.insality;

public class DSF {

	int[] set; // номер множества
	int[] rnk; // ранг

	DSF(int size) {
		set = new int[size];
		rnk = new int[size];
		for (int i = 0; i < size; i++)
			set[i] = i;
	}

	/* Возвращает множество, которому принадлежит x */
	int set(int x) {
		return x == set[x] ? x : (set[x] = set(set[x]));
	}

	/* Если u и v лежат в разных множествах, то сливаем их и возвращаем true */
	boolean union(int u, int v) {
		if ((u = set(u)) == (v = set(v)))
			return false;
		if (rnk[u] < rnk[v]) {
			set[u] = v;
		} else {
			set[v] = u;
			if (rnk[u] == rnk[v])
				rnk[u]++;
		}
		return true;
	}
}
