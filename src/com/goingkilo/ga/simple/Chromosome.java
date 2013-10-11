package com.goingkilo.ga.simple;

public class Chromosome {

	// contains 50 rectangles
	public static final int COUNT = 50;
	
	Gene[] genes;
	
	public Chromosome(){
		genes = new Gene[COUNT];
	}
	
	public Chromosome( Chromosome c) {
		genes = new Gene[COUNT];
		for( int i = 0 ; i < COUNT; i++ ){
			genes[i] = new Gene(c.genes[i]);
		}
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		for( Gene g : genes ) {
			b.append( g.toString() );
		}
		return b.toString();
	}
	
}
