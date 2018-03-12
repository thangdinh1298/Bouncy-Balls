import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Balls {
    public double x,y;
    private double vx, vy;
    private double radius;
    private float mass;
    public int count = 0;
    public Color color;

    public Balls(double x,double  y, double vx, double vy,double radius, float mass, Color color){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = color;
    }
    public double getV(){
        return Math.sqrt(vx*vx + vy*vy);
    }

    public Point2D center(){
        return new Point2D(x,y);
    }

    public double getMass(){
        return this.mass;
    }

    public double getRadius(){
        return this.radius;
    }
    private void draw(){
        StdDraw.filledCircle(x,y,this.radius);
    }

    public void move(){
        StdDraw.setPenColor(color);
        x+= vx;
        y += vy;
        draw();
    }

    public double getVx() {
        return vx;
    }
    public double getVy(){
        return vy;
    }
    public void setVx(double vx){
        this.vx = vx;
    }
    public void setVy(double vy){
        this.vy = vy;
    }
    public ArrayList<WallEvent> timeTo(double clock){ //todo : add in a smoothing factor in the times before collision
        ArrayList<WallEvent> list = new ArrayList<>();
        if(vx > 0){
            list.add(new WallEvent(this, (Window.getBoundX() - radius - x)/vx + clock,2));
        }
        else if(vx < 0){
            list.add(new WallEvent(this, (radius - x)/vx + clock,4));
        }
        if(vy > 0){
            list.add(new WallEvent(this, (Window.getBoundY() -radius - y)/vy + clock,1));
        }
        else if(vy < 0){
            list.add(new WallEvent(this, (radius - y)/vy + clock,3));
        }
        return list;
    }

    public double impulse(Balls that, double xA, double yA, double xB, double yB){
        Vector dv = new Vector((this.vx - that.vx), (this.vy - that.vy));
        Vector dr = new Vector(xA - xB, yA - yB);
        double dvdr = dv.dot(dr);
        double impulse = 2 * this.mass * that.mass * dvdr  / ((this.mass + that.mass)*(this.radius+that.radius));
        return impulse;
    }

    public double timeTo(Balls that){
        if (this == that) return -1;
        double dx  = that.x - this.x;
        double dy  = that.y - this.y;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return -1;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        // if (drdr < sigma*sigma) StdOut.println("overlapping particles");
        if (d < 0) return -1;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }
}
