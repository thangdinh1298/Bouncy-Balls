public class WallEvent extends Events {
    public Balls ballA;
    public int wall;
    private double countA;
    public WallEvent(Balls ballA, double t, int wall) {
        super(t);
        this.ballA = ballA;
        this.wall = wall;
        this.countA = ballA.count;
    }

    public void collide(){
        if(wall == 1 || wall == 3) ballA.setVy(-ballA.getVy());
        if(wall == 2 || wall == 4) ballA.setVx(-ballA.getVx());
        ballA.count ++;
    }

    public boolean isValid(){
        return(countA == ballA.count);
    }
}
