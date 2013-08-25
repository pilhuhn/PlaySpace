package de.bsd.playspace;

import android.graphics.Paint;

/**
 * // TODO: Document this
 * @author Heiko W. Rupp
 */
public class OPoint {

    public float x;
    public int orientation;
    public float y;
    public Paint currentPaint;

    public OPoint(float x, float y, int orientation, Paint currentPaint) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.currentPaint = currentPaint;
    }

    public OPoint(OPoint other) {
        this.x = other.x;
        this.y = other.y;
        this.orientation = other.orientation;
        this.currentPaint = other.currentPaint;
    }


    @Override
    public String toString() {
        return "OPoint{" +
            "x=" + x +
            ", y=" + y +
            ", angle=" + orientation +
            ", currentColor=" + currentPaint.getColor() +
            '}';
    }
}
