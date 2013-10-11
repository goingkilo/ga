package com.goingkilo.ga.simple;

import java.util.Date;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class GADisplayRunner extends PApplet {

	PGraphics pg;
	PImage	target;
	Chromosome[] population ;
	SortHelper sh = new SortHelper();

	int W = 336, H = 576;

	int c= 0 ;

	GA ga  = new GA();

	public void setup() {
		size(400,400);
		background(200);

		pg = createGraphics( W, H);
		target = loadImage( "/home/kraghu/ga/a.jpg");
		population 		= ga.initPopulation( GA.populationSize);
	}

	public void draw() {
		background(254);
		rect( 10,10,100,100);
		
		float scores[] 	= drawNScore( target, population, 100);
		List<Chromosome> winners = sh.getSmallestScorers( population, scores, 10);

		c++;
		if( c == 900 ) {
			c = 0;
			println( " ["+ c + "] " + new Date());
			println( "winner :" + winners.get(0).toString());
			println( "random score :" +  scores[0]);

		}
		population = ga.regrowPopulation( winners);
	}

	public float[] drawNScore( PImage target , Chromosome[] chromosomes, int print ) {

		float[] scores = new float[chromosomes.length];

		for( int x = 0 ; x < chromosomes.length ; x++ ) { //20 chromosomes
			Chromosome chromosome = chromosomes[x];

			pg.beginDraw();
			pg.background( 254);

			for( int j = 0 ; j < chromosome.genes.length ; j++ ) { //50 chromosome.genes
				if( chromosome.genes[j].fill ) {
					pg.fill( chromosome.genes[j].color );
				}
				else {
					pg.stroke( chromosome.genes[j].color );
				}

				pg.rect( chromosome.genes[j].x, 
						chromosome.genes[j].y ,
						chromosome.genes[j].w, 
						chromosome.genes[j].h);
			}
			pg.endDraw();

			if( c == print && x == 18 ) {
				pg.save( "/home/kraghu/ga/b_" + System.currentTimeMillis() + ".jpg");
			}

			float score = 0f;
			for (int i = 0; i < pg.width; i++) {
				for (int j = 0; j < pg.height; j++) {

					int a = color( pg.get(i, j));
					int b = color(target.get(i, j));

					float r1 = red(a);
					float r2 = red(b);
					float g1 = green(a);
					float g2 = green(b);
					float b1 = blue(a);
					float b2 = blue(b);

					score += Math.abs(r1 - r2) 
							+ Math.abs(g1 - g2)
							+ Math.abs(b1 - b2);
				}
			}
//			println( "S["+ x + "] " + new Date());
			scores[x] = score;
		}
		return scores;
	}

	public void score( PImage img1 , PImage img2) {
		float score = 0f;
		for (int i = 0; i < img1.width; i++) {
			for (int j = 0; j < img1.height; j++) {

				int a = color( img1.get(i, j));
				int b = color( img2.get(i, j));

				float r1 = red(a);
				float r2 = red(b);
				float g1 = green(a);
				float g2 = green(b);
				float b1 = blue(a);
				float b2 = blue(b);

				score += Math.abs(r1 - r2) 
						+ Math.abs(g1 - g2)
						+ Math.abs(b1 - b2);
				System.out.println( score);
			}
		}
		System.out.println( score);
	}
}
