package com.goingkilo.ga.simple.misc;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;


public class TargetImage extends PApplet {

	PImage target;
	int W = 336, H = 576; 

	public void setup(){
		size( W, H);
		background(254);
		target = loadImage( "/home/kraghu/a.jpg");
		println( target.width);
		println( target.height);
	}

	public void draw() {
		image(target, 0, 0);
		int [] pix = this.pixels;;
		println( pix );
		for(int i = 0 ; i < target.width;  i++ ){
			for( int j = 0 ; j < target.height ; j++ ) {
				int c = color(target.get(i,j));
				println(  c + " " + red(c) + " " + green(c) + " " + blue(c) );
			}
		}
		noLoop();
		
	}
}