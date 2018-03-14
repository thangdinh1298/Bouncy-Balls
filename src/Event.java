import edu.princeton.cs.algs4.Point2D;

public class Event extends Events {
    public Balls ballA,ballB;
    private int countA,countB;
    public Event(Balls ballA, Balls ballB, double t){
        super(t);
        this.ballA = ballA;
        this.ballB = ballB;
        countA = ballA.count;
        countB = ballB.count;
    }
    public double distance(){
        Point2D pointA = new Point2D(ballA.x,ballA.y);
        Point2D pointB = new Point2D(ballB.x,ballB.y);
        return pointA.distanceTo(pointB);
    }

    @Override
    public void collide(double xA, double yA, double xB, double yB, double time, double clock){
        double rxA = xA + ballA.getVx()*(time-clock);
        double ryA = yA + ballA.getVy()*(time-clock);
        double rxB = xB + ballB.getVx()* (time-clock);
        double ryB = yB + ballB.getVy()*(time-clock);
//        System.out.println("rxA: "+ rxA);
//        System.out.println("ryA: "+ ryA);
//        System.out.println("rxB: "+ rxB);
//        System.out.println("ryB: "+ ryB);
        Point2D pointA = new Point2D(rxA,ryA);
        Point2D pointB = new Point2D(rxB,ryB);
//        System.out.println("distance between centers: "+pointA.distanceTo(pointB));
        double impulse  = ballA.impulse(ballB,rxA,ryA,rxB,ryB);
        double impulseX = impulse*(rxA - rxB)/(ballA.getRadius() + ballB.getRadius());
        double impulseY = impulse*(ryA - ryB)/(ballA.getRadius() + ballB.getRadius());
        double newVx =  ballA.getVx() - (impulseX/ballA.getMass());
        double newVy =   ballA.getVy() - (impulseY/ballA.getMass());
        ballA.setVx(newVx);
        ballA.setVy(newVy);
        newVx = ballB.getVx() + (impulseX/ballB.getMass());
        newVy = ballB.getVy() + (impulseY/ballB.getMass());
        ballB.setVx(newVx);
        ballB.setVy(newVy);
        ballA.count++;
        ballB.count++;
    }
    public boolean isValid(){
        return(countA == ballA.count && countB == ballB.count);
    }


}
