package de.bsd.playspace.commands;

import android.graphics.Canvas;
import de.bsd.playspace.OPoint;

/**
 * // TODO: Document this
 * @author Heiko W. Rupp
 */
public abstract class AbstractDrawCommand {

    protected int angle;

    public abstract OPoint drawSelf(Canvas canvas, OPoint previousPoint);

    protected float degToRad(int orientation) {

        return (float) (orientation * Math.PI / 180f);
    }

}
