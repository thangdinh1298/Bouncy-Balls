import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;

public class Window {
    private static double boundX = 1;
    private static double boundY = 1;
    private int numBall = 3;
    private static Balls[] balls;
    private double clock = 0.0;
    public static double getBoundX(){
        return boundX;
    }
    public static double getBoundY(){
        return boundY;
    }
    public double lastTimeUpdate;

    //todo fix possible loss of kinetic energy preservation due to three-way(n-way) collision

    public Window(){
        balls = new Balls[numBall];
        for(int i = 0; i <numBall; i ++){ // create n balls
            float radius = (float) 0.03;
            double x = StdRandom.uniform(0.1,boundX-radius);
            double y = StdRandom.uniform(0.1,boundY-radius);
            double vx = StdRandom.uniform(-0.005,0.005);
            double vy = StdRandom.uniform(-0.005,0.005);
            int r = StdRandom.uniform(0,255);
            int g = StdRandom.uniform(0,255);
            int b = StdRandom.uniform(0,255);
            Balls ball = new Balls(x,y,vx,vy,radius,1,new Color(r,g,b));
            balls[i] = ball;
        }

//        Balls ball1 = new Balls(0.1,0.1,0.005,0,0.03,1,Color.red);
//        Balls ball2 = new Balls(0.5,0.1,-0.005,0,0.03,1,Color.black);
//        Balls ball3 = new Balls(0.3,0.1,0,0,0.03,1,Color.BLUE);
//        balls[0] = ball1;
//        balls[1] = ball2;
//        balls[2] = ball3;
    }

    public void redraw(Events e){
        int frameCount = 0;
        while (frameCount <= e.getT() - clock) {
            long currentTime = System.nanoTime();
            StdDraw.clear();
            if(currentTime - lastTimeUpdate >= 17000000){
                frameCount ++;
                for(Balls b: balls){
                    b.move();
                }
                lastTimeUpdate = currentTime;
                StdDraw.show();
            }
        }
    }

    public void eventPrediction(MinPQ<Events> eventQueue){
        for(int i = 0; i < balls.length;i++){
            for(int j = i + 1; j < balls.length; j++){
                double t = balls[i].timeTo(balls[j]);
                if(t > 0){ // != -1
                    Event e = new Event(balls[i],balls[j],t+clock);
                        eventQueue.insert(e);
                }
            }
        }

        for(int i = 0; i < balls.length;i++){
            if(!balls[i].timeTo(clock).isEmpty()){
                for(Events e: balls[i].timeTo(clock)){
                    eventQueue.insert(e);
                }
            }
        }
    }

    public void simulate(){
        MinPQ<Events> eventQueue = new MinPQ<>();
        eventPrediction(eventQueue);

        double xA = 0;
        double xB = 0;
        double yA = 0;
        double yB = 0;

        while(!eventQueue.isEmpty()){
            Events e = eventQueue.delMin();
            if(e.getT() < clock){
                continue;
            }
            if(!e.isValid()) {
                continue;
            }
            if(e.getClass() == Event.class){
                Event event = (Event) e;
                xA = event.ballA.x;
                xB = event.ballB.x;
                yA = event.ballA.y;
                yB = event.ballB.y;
            }
            redraw(e);
            if (e.getClass() == WallEvent.class){
                e.collide();
            }
            else if(e.getClass() == Event.class){
                e.collide(xA,yA,xB,yB,e.getT(),clock);
            }
            clock = e.getT();
            eventPrediction(eventQueue);
        }
    }

    public static void main(String[] args) {
        Window window = new Window();

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, window.boundX);
        StdDraw.setYscale(0, window.boundY);
        window.simulate();
        }

}
