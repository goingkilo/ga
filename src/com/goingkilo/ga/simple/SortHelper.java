package com.goingkilo.ga.simple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortHelper {
	
	public SortHelper(){}
	
	public Comparator<Item> itemSorter = new Comparator<Item>() {

		@Override
		public int compare(Item o1, Item o2) {
			return (int) (o2.score  - o1.score );
		}

	};

	
	private List<Item> makeCompositeList(Chromosome[] c, float[] scores) {
		List<Item> items  = new ArrayList<Item>();
		for( int i = 0 ; i < c.length ; i++ ) {
			items.add( new Item( c[i], scores[i]));
		}
		return items;
	}
	
	private List<Chromosome> topSix( List<Item> list) {
		List<Chromosome> selected = new ArrayList<Chromosome>();
		Collections.sort( list, itemSorter);
		for( int i = 0 ; i < 6 ; i++  ) {
			selected.add( list.get( i).c );
		}
		return selected;
	}

	
	public List<Chromosome> getSmallestScorers( Chromosome[] c, float[] scores, int N) {
		List<Item> list = makeCompositeList( c, scores);
		List<Chromosome> selected = new ArrayList<Chromosome>();
		Collections.sort( list, itemSorter);
		for( int i = 0 ; i < N ; i++  ) {
			selected.add( list.get(list.size() -1 -i).c );
		}
		return selected;
	}
	public static void main (String[] args) {
		
	}

}

class Item {
	Chromosome c;
	float score;
	
	public Item(Chromosome c, float score) {
		this.c = c;
		this.score = score;
	}
	
	
}
