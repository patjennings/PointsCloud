import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PointsCloud extends PApplet {

int tempo; // tempo varie, cf. draw()
ArrayList<Point> points = new ArrayList<Point>();
boolean opacityMode = true; // est-ce que l'opacit\u00e9 diminue au cours de l'apparition ?
String drawMode = "FILL"; // mode de dessin, \u201cSTROKE" ou "FILL"
String centerMode = "ELLIPSE"; // CROSS ou ELLIPSE
float xoff = 0.0f; // \u00e0 utiliser pour le noise qui g\u00e8re le tempo

public void setup(){
  
  frameRate(25);

  

  background(0);
}
public void draw(){
  background(0);

  xoff = xoff + .01f;

  tempo = ceil(noise(xoff)*5);
  //tempo = floor(random(1,50));

  if(frameCount%tempo == 1)
  {
    points.add(new Point(random(width), random(height), random(0.1f), random(50, 100)));
  }

  
}

class Point {
   int px, py; //position
   float ssize, dsize, opac, easing, tsize; // taille de d\u00e9part, diff (dynamique) entre taille finale et taille courante, opacit\u00e9 courante, easing, taille finale
   float windForce = random(0.05f, 0.5f);
   float windx = 0;

    Point (float pointX, float pointY, float Easing, float TSize){
        px = floor(pointX);
        py = floor(pointY);
        easing = Easing;
        ssize = 0;
        tsize = TSize;


    }
    public void display(){

        windx += windForce;

        dsize = tsize - ssize; // update dsize
        ssize += dsize * easing; // update size courante

        //controle du mode d'opacit\u00e9
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

        // Augmentation de la taille, jusqu'\u00e0 la taille finale
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
  public void settings() {  size(1800, 360);  smooth(8); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PointsCloud" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
