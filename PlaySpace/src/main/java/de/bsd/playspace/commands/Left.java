package de.bsd.playspace.commands;

import android.graphics.Canvas;
import de.bsd.playspace.OPoint;

/**
 * // TODO: Document this
 * @author Heiko W. Rupp
 */
public class Left extends AbstractDrawCommand {

    public Left() {
        angle =90;
    }

    public Left(Integer degrees) {
       if (degrees!=null) {
        angle =degrees;
       }
       else {
          angle = 90;
       }
    }

    @Override
    public OPoint drawSelf(Canvas canvas, OPoint previousPoint) {

        OPoint result = new OPoint(previousPoint);
        result.orientation -= angle;
        result.orientation %= 360;
//        System.out.println(" - new angle " + result.orientation);
        return result;
    }
}
