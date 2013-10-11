package com.goingkilo.ga.simple;

import java.util.List;
import java.util.Random;


public class GA {

	int W = 336, H = 576;

	public static int populationSize = 20;
	
	static Random r = new Random(System.currentTimeMillis());
	
	SortHelper sh = new SortHelper();
	
	public Chromosome[]  initPopulation(int size) {
		Chromosome[] chromosome = new Chromosome[size];
		for( int i = 0 ; i < size ; i ++ ) {
			chromosome[i] = new Chromosome();
			for (int j = 0; j < Chromosome.COUNT; j++) {
				chromosome[i].genes[j] = createGene( r, W, H);
			}
		}
		return chromosome;
	}
	
	public Gene createGene(Random r, float W, float H) {
		float x = r.nextFloat() * W/2;
		float y = r.nextFloat() * H/2;
		float l = r.nextFloat() * W / 2;
		float w = r.nextFloat() * H / 2;
		float color = r.nextFloat() * 135 + 120f;
		boolean fill = (r.nextInt(100) % 2 == 0);
		Gene g = new Gene(x, y, l, w, fill, color);
		return g;
	}

	public Gene mutate( Gene g1, Random r, int pos) {
		Gene g = new Gene( g1);
		switch(pos) {
		case 0:
			g.x = r.nextFloat() * W/2;
			break;
		case 1:
			g.y = r.nextFloat() * H/2;
			break;
		case 2:
			g.w = r.nextFloat() * W/2;
			break;
		case 3:
			g.h = r.nextFloat() * H/2;
			break;
		case 4:
			g.color = r.nextFloat() * 135 + 120f;
			break;
		case 5:
			g.fill = (r.nextInt(100) % 2 == 0);
			break;
		}
		return g;
		
	}
	
	public Gene crossover( Gene donor, Gene recipient, int pos) {
		Gene g = new Gene( recipient);
		switch(pos) {
		
		case 0:
			g.x =  donor.x;
			break;
		case 1:
			g.y = donor.y;
			break;
		case 2:
			g.w = donor.w;
			break;
		case 3:
			g.h = donor.h;
			break;
		case 4:
			g.color = donor.color;
			break;
		case 5:
			g.fill = donor.fill;
			break;
		}
		return g;
	}
	
	public Chromosome[] regrowPopulation( List<Chromosome> items) {
		Chromosome[] n = new Chromosome[populationSize];
		
		n[0] = items.get(0);
		n[1] = items.get(1);
		n[2] = items.get(2);
		n[3] = items.get(3);
		n[4] = items.get(4);
		n[5] = items.get(5);
		n[6] = items.get(6);
		n[7] = items.get(7);
		n[8] = items.get(8);
		n[9] = items.get(9);
		
		n[10] = doMutation( items.get(0), r, r.nextInt(50));
		n[11] = doMutation( items.get(1), r, r.nextInt(50));
		n[12] = doMutation( items.get(2), r, r.nextInt(50));
		n[13] = doMutation( items.get(3), r, r.nextInt(50));
		n[14] = doMutation( items.get(4), r, r.nextInt(50));
		
		n[15] = doCrossover( items.get(0), items.get(1), r, r.nextInt(50));
		n[16] = doCrossover( items.get(1), items.get(2), r, r.nextInt(50));
		n[17] = doCrossover( items.get(2), items.get(3), r, r.nextInt(50));
		n[18] = doCrossover( items.get(3), items.get(4), r, r.nextInt(50));
		n[19] = doCrossover( items.get(4), items.get(0), r, r.nextInt(50));
		
		return n;
	}
	
	public Chromosome doMutation( Chromosome c, Random r, int n) {
		Chromosome newOne = new Chromosome(c);
		newOne.genes[n] = mutate( newOne.genes[n], r, r.nextInt(6) );
		return newOne;
	}
	
	public Chromosome doCrossover( Chromosome x, Chromosome y, Random r, int n) {
		Chromosome newOne = new Chromosome(x);
		for( int i  = n;  i < x.genes.length ; i++ ) {
			newOne.genes[i] = y.genes[i];
		}
		return newOne;
	}
	
	public static void main(String[] args) {

	}

}
