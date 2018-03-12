public class Events implements Comparable {
    private double t;

    public Events(double t){
        this.t = t;
    }

    public int compareTo(Object o) {
        Events that = (Events) o;
        if(this.t > that.t) return 1;
        if(this.t == that.t) return 0;
        return -1;
    }
    public double getT(){
        return t;
    }
    public void collide(){

    }
    public double distance(){
        return 0;
    }
    public void collide(double xA, double yA, double xB, double yB,double time,double clock ){

    }
    public boolean isValid(){
        return true;
    }
}
