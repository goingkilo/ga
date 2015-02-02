import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

r = new java.util.Random( System.currentTimeMillis())

def crossover(g1,g2) {
	for(int i = g1.chromosomes.size()/2 ; i < g1.chromosomes.size() ; i++ ) {
		Chromosome c = g1.chromosomes.get(i);
		g1.chromosomes.set(i, g2.chromosomes.get(i));
		g2.chromosomes.set(i,c);
	}
	g1
}

def mutate(g) {
	candidate = r.nextInt(50)	//pick one of the 50 chromosomes in a gene to mutate
	g1 = g.chromosomes.get( candidate )
	switch( r.nextInt(6) ) {
		case 1:
			g1.x = r.nextInt(200);
			break;
		case 2:
			g1.y = r.nextInt(200);
			break;
		case 3:
			g1.width = r.nextInt(200);
			break;
		case 4:
			g1.height = r.nextInt(200);
			break;
		case 5:
			g1.color= new Color( 80 + r.nextInt(20), 80 + r.nextInt(20), 80 + r.nextInt(20));
			break;
		default:
			break;
	}
	g.chromosomes.set( candidate, g1)
	g
}

class Gene {
	public java.util.List<Chromosome> chromosomes = [];
	public Integer score;
	def add(Chromosome c){
		chromosomes.add(c);
	}
	def getSize(){
		return chromosomes.size();
	}
	def tostring() {
		String retu = ">>"+score
		//chromosomes.each {retu = retu +" " + it.getstring()}
		return retu
	}

}
class Chromosome {
	int x,y,width,height
	boolean fill;
	public color;
	def getstring() {
		"["+x +","+y+","+width+","+height+","+fill+","+color.getRed()+","+color.getGreen()+","+color.getBlue()+"]"
	}
}

//336/576
def createGeneR () {
	gene = new Gene()
	for( i in 0..100) {
		a = new Chromosome()
		a.x = r.nextInt(336)
		a.y = r.nextInt(576)
		a.width = r.nextInt(336)//336
		a.height = r.nextInt(576)//576
		a.color = new Color( r.nextInt(200), r.nextInt(200), r.nextInt(200));

		a.fill = (r.nextInt(2) > 0);
		gene.chromosomes.add(a)
	}
	gene
}

def loadImage(name) {
	BufferedImage source = null;
	try {
		source = ImageIO.read(new File(name));
	}
	catch (IOException e) {
		println e
	}
	source
}

def drawImage( gene) {
	//println '>>'+gene.getClass().getName()
	target = new BufferedImage( 336, 576, BufferedImage.TYPE_INT_ARGB);
	g  = target.createGraphics();
	g.setBackground(Color.white)
	g.setColor(Color.white)
	g.fillRect( 0, 0, target.width, target.height );
	float opacity = 0.5f
	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity))

	//paint = new GradientPaint(0,0,Color.GRAY,100, 0,Color.WHITE);
	for( x in gene.chromosomes) {
		g.setColor( x.color)
		//println x.x + ","+x.y+","+x.width +","+x.height
		if( x.fill ) {
			g.fillRect(   x.x, x.y, x.width ,x.height )
		}
		else {
			g.draw( new Rectangle2D.Double(  x.x, x.y, x.width ,x.height ))
		}
	}
	target
}

def score( source, target ) {
	diff = 0
	for( i in 0..(source.width-1) ) {
		for( j in 0..(source.height-1) ) {
			c1 = new Color( source.getRGB(i,j), true)
			c2 = new Color( target.getRGB(i,j), true)
			diff +=   Math.abs(c1.getRed() - c2.getRed()) +  Math.abs(c1.getBlue() - c2.getBlue()) + Math.abs(c1.getGreen() - c2.getGreen())
		}
	}
	diff
}

def saveImage( image , type, name) {
	// ImageIO.write( image, "png", new File("/c:/users/a692413/Desktop/k/projects/ga/a1.png") )
	ImageIO.write( image, type, new File(name) )
}

img1 = loadImage("c:/users/a692413/Desktop/k/projects/ga/a.jpg")

population = []
scores = []

//6 genes
8.times {
	population.add( createGeneR() )
}

counter = 0;
while( true ) {
	//println counter
	for ( g in population ) {

		img2 = drawImage( g )
		score1 = score(img1, img2)
		g.score = score1
		if( counter % 50 == 0 ) {
			saveImage( img2, "png", "c:/users/a692413/Desktop/k/projects/ga/dump/"+counter+".png")
		}
		
	}
	if( counter % 1000 == 0 ) {
		println counter
		def file = new File("c:/users/a692413/Desktop/k/projects/ga/dump/dump.txt");
		for( g in population) {
			for( chromosome in g.chromosomes) {
				file << chromosome.getstring()
				file << "\n"
			}
		}
		file << "-------------------------\n"
	}
	population.sort{a,b -> a.score <=>b.score }

	//mutation
	population.set( 2, mutate(population.get(0)) );
	population.set( 3, mutate(population.get(1)) );
	//crossover
	population.set( 4, crossover(population.get(0),population.get(1)) );
	population.set( 5, crossover(population.get(1),population.get(0)) );
	//two random genes
	population.set( 6, createGeneR() );
	population.set( 7, createGeneR() );

	counter += 1;
}


