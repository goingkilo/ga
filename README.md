Using a genetic algorithm to generate a picture of the Bodhidharma using a simple pixel comparison fitness function which is 
called 'score()'.  

I use 20 chromosomes.
A chromosome consists of 50 genes.
Each gene has the following attributes :
- x position
- y position
- width
- height
- fill (or no) - this is a boolean
- color (range from 120 - 254 - shades of grey)

I use Processing (processing.org) to handle the image drawing/comparison.
I used to draw the chromosome to the screen and then save it to disk then load it into a PImage
before I discovered the PGraphics API.

So now, the code draws stuff offline, sort-of like a double buffering game where the backup never gets to go in and bat. 
The scores are in the 1.xxxE7 range, and that IS quite disturbing.
It plateaus after a few hours of running, and again, that sucks.  
That's probably because I select only 5 top scorers on the fitness function, randomly mutate one of their genes(rectangles)
etc etc

What I'm going to try next is to give the user the ability to set the variables alsing allowed -

number of vertices
mutation percentage
crossover size
etc etc . There are lots.

Also , separating the gene expression (drawing the rectangles) and manipulation (mutation & crossover) 
would be a good idea, but life doesn't work that way, atleast not without a patterns in triplicate.

I will go weep now.
