public abstract class Enemy {

    private double angle;
    private double rv;
    private double exposionStart;
    private double exposionEnd;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRv() {
        return rv;
    }

    public void setRv(double rv) {
        this.rv = rv;
    }

    public double getExposionStart() {
        return exposionStart;
    }

    public void setExposionStart(double exposionStart) {
        this.exposionStart = exposionStart;
    }

    public double getExposionEnd() {
        return exposionEnd;
    }

    public void setExposionEnd(double exposionEnd) {
        this.exposionEnd = exposionEnd;
    }

    public abstract void shoot(long currentTime);
    
}