package com.goingkilo.ga.simple;

public class Gene {

	float x ,y, w, h, color;
	boolean fill;

	public Gene() {}
	
	public Gene( Gene g) {
		this.x = g.x;
		this.y = g.y;
		this.w = g.w;
		this.h = g.h;
		this.fill = g.fill;
		this.color = g.color;
	}
	

	public Gene(float x, float y, float w, float h, boolean b, float color) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.fill = b;
		this.color = color;
	}
	
	public String toString() {
		return "[" + x + "," + y +"," + w +"," + h +","+fill + ","+color + "]";
	}
	

}
