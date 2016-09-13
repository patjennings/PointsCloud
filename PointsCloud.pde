int tempo; // tempo varie, cf. draw()
ArrayList<Point> points = new ArrayList<Point>();
boolean opacityMode = true; // est-ce que l'opacité diminue au cours de l'apparition ?
String drawMode = "FILL"; // mode de dessin, “STROKE" ou "FILL"
String centerMode = "ELLIPSE"; // CROSS ou ELLIPSE
float xoff = 0.0; // à utiliser pour le noise qui gère le tempo

void setup(){
  size(1800, 360);
  frameRate(25);

  smooth(8);

  background(0);
}
void draw(){
  background(0);

  xoff = xoff + .01;

  tempo = ceil(noise(xoff)*5);
  //tempo = floor(random(1,50));

  if(frameCount%tempo == 1)
  {
    points.add(new Point(random(width), random(height), random(0.1), random(50, 100)));
  }

  for(int i = 0 ; i < points.size() ; i++)
  {
    Point part = points.get(i);
    part.display();
  }
}

class Point {
   int px, py; //position
   float ssize, dsize, opac, easing, tsize; // taille de départ, diff (dynamique) entre taille finale et taille courante, opacité courante, easing, taille finale
   float windForce = random(0.05, 0.5);
   float windx = 0;

    Point (float pointX, float pointY, float Easing, float TSize){
        px = floor(pointX);
        py = floor(pointY);
        easing = Easing;
        ssize = 0;
        tsize = TSize;


    }
    void display(){

        windx += windForce;

        dsize = tsize - ssize; // update dsize
        ssize += dsize * easing; // update size courante

        //controle du mode d'opacité
        if(opacityMode == true){
          opac = (255*tsize/ssize)/10;
        }
        else{
          opac = 255;
        }

        // controle du mode de dessin
        if(drawMode == "FILL"){
          fill(255, opac);
          noStroke();
        }
        if(drawMode == "STROKE"){
          stroke(255, opac);
          strokeWeight(2);
          noFill();
        }

        // Augmentation de la taille, jusqu'à la taille finale
        if(ssize < tsize){
           ellipse(px+windx, py, ssize, ssize);
        }
        else{
            ellipse(px+windx, py, tsize, tsize);
        }

        if(centerMode == "ELLIPSE"){
          fill(255);
          noStroke();
          ellipse(px+windx, py, 4, 4);
        }
        if(centerMode == "CROSS"){
          stroke(255);
          strokeWeight(1);
          line(px-4+windx, py, px+4+windx, py);
          line(px+windx, py-4, px+windx, py+4);

        }


    }
}
