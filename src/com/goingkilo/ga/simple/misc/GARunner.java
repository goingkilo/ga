package com.goingkilo.ga.simple.misc;

import java.util.List;
import java.util.Random;

import com.goingkilo.ga.simple.Chromosome;
import com.goingkilo.ga.simple.Gene;
import com.goingkilo.ga.simple.SortHelper;

import processing.core.PApplet;
import processing.core.PImage;

public class GARunner extends PApplet {

	Chromosome[] chromosome;
	
	Chromosome[] nextGen;
	
	float[] scores;
	
	PImage target;
	int W = 336, H = 576;

	int population = 20;
	int iteration = 0;

	int printLimit = 100;
	
	boolean looper;

	static Random r = new Random(System.currentTimeMillis());
	
	SortHelper sh = new SortHelper();
	
	String folder = "/home/kraghu/ga/";

	public void setup() {
		size(W, H);
		background(254);
	
		target = loadImage( folder + "a.jpg");
		
		chromosome 	= initPopulation(population);
		scores 		= new float[population];
		nextGen 	= new Chromosome[5];

	}

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

	public void draw() {

		drawChromosome( iteration );

		save( folder + iteration + ".jpg");
		scores[iteration] =   score( this, folder + iteration + ".jpg");

		printLimit--;
		if( printLimit <= 0 ) {
			System.out.println( "top score after 1M iterations is " + scores[0]);
			printLimit = 1000000;
		}

		iteration++;
		if( iteration >= population )  {
			List<Chromosome> winners = sh.getSmallestScorers( chromosome, scores, 5);
			
			chromosome = regrowPopulation( winners);
			//System.exit(0);
			//that's right, no end to this process
			iteration = 0;
		}

	}

	public void drawChromosome( int n) {
		for( int j = 0 ; j < chromosome[n].genes.length ; j++ ) {
			
			if( chromosome[n].genes[j].fill ) {
				fill( chromosome[n].genes[j].color );
			}
			else {
				stroke( chromosome[n].genes[j].color );
			}
			
			rect( chromosome[n].genes[j].x, 
					chromosome[n].genes[j].y ,
					chromosome[n].genes[j].w, 
					chromosome[n].genes[j].h);
		}	
	}


	public void mouseClicked() {
		loop();
//		System.out.println( "->");
//		if( looper ) {
//			noLoop();
//		}
//		else {
//			loop();
//		}
//		looper = !looper;
	}

	public float score(PApplet p, String contender) {
		PImage img = p.loadImage(contender);
		float score = 0f;
		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int a = color(img.get(i, j));
				int b = color(target.get(i, j));
				float r1 = red(a);
				float r2 = red(b);
				float g1 = green(a);
				float g2 = green(b);
				float b1 = blue(a);
				float b2 = blue(b);

				score += Math.abs(r1 - r2) + Math.abs(g1 - g2)
						+ Math.abs(b1 - b2);
			}
		}
		return score;
	}

	public Gene createGene(Random r, float W, float H) {
		float x = r.nextFloat() * W;
		float y = r.nextFloat() * H;
		float l = r.nextFloat() * W / 3;
		float w = r.nextFloat() * H / 3;
		float color = r.nextFloat() * 135 + 120f;
		boolean fill = (r.nextInt(100) % 2 == 0);
		Gene g = new Gene(x, y, l, w, fill, color);
		return g;
	}

	//as random as can be.
	public Gene mutate( Gene g1, Random r, int pos) {
		Gene g = new Gene( g1);
		switch(pos) {
		case 0:
			g.x = r.nextFloat() * W;
			break;
		case 1:
			g.y = r.nextFloat() * H;
			break;
		case 2:
			g.w = r.nextFloat() * W/3;
			break;
		case 3:
			g.h = r.nextFloat() * H/3;
			break;
		case 4:
			g.color = r.nextFloat() * 50 + 120f;
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
		Chromosome[] n = new Chromosome[population];
		
		n[0] = items.get(0);
		n[1] = items.get(1);
		n[2] = items.get(2);
		n[3] = items.get(3);
		n[4] = items.get(4);
		
		n[5] = helpMutate( items.get(0), r, r.nextInt(50));
		n[6] = helpMutate( items.get(1), r, r.nextInt(50));
		n[7] = helpMutate( items.get(2), r, r.nextInt(50));
		n[8] = helpMutate( items.get(3), r, r.nextInt(50));
		n[9] = helpMutate( items.get(4), r, r.nextInt(50));
		
		n[10] = helpCrossover( items.get(0), items.get(1), r, r.nextInt(50));
		n[11] = helpCrossover( items.get(1), items.get(2), r, r.nextInt(50));
		n[12] = helpCrossover( items.get(2), items.get(3), r, r.nextInt(50));
		n[13] = helpCrossover( items.get(3), items.get(4), r, r.nextInt(50));
		n[14] = helpCrossover( items.get(4), items.get(0), r, r.nextInt(50));
		
		n[15] = helpCrossover( items.get(0), n[5], r, r.nextInt(50));
		n[16] = helpCrossover( items.get(1), n[6], r, r.nextInt(50));
		n[17] = helpCrossover( items.get(2), n[7], r, r.nextInt(50));
		n[18] = helpCrossover( items.get(3), n[8], r, r.nextInt(50));
		n[19] = helpCrossover( items.get(4), n[9], r, r.nextInt(50));
		
		return n;
	}
	
	public Chromosome helpMutate( Chromosome c, Random r, int n) {
		Chromosome newOne = new Chromosome(c);
		newOne.genes[n] = mutate( newOne.genes[n], r, r.nextInt(6) );
		return newOne;
	}
	
	public Chromosome helpCrossover( Chromosome donor, Chromosome recipient, Random r, int n) {
		Chromosome newOne = new Chromosome(recipient);
		newOne.genes[n] = crossover( donor.genes[n], recipient.genes[n], r.nextInt(6) );
		return newOne;
	}
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "com.goingkilo.ga.simple.GARunner" });
	}

}
