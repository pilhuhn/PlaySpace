package de.bsd.playspace.commands;


import android.graphics.Canvas;
import de.bsd.playspace.OPoint;

/**
 * // TODO: Document this
 *
 * @author Heiko W. Rupp
 */
public class Move extends AbstractDrawCommand {


   private int lenght;

   public Move(Integer lenght) {
      if (lenght!=null) {
        this.lenght = lenght;
      }
        else {
         this.lenght = 10;
      }
   }

   @Override
   public OPoint drawSelf(Canvas canvas, OPoint previousPoint) {

       OPoint target = computeNextPoint(previousPoint);

       return target;

   }

   private OPoint computeNextPoint(OPoint previousPoint) {

       float angle = degToRad(previousPoint.orientation);

       float newX = (float) (previousPoint.x + Math.cos(angle) * lenght);
       float newY = (float) (previousPoint.y + Math.sin(angle) * lenght);

       OPoint result = new OPoint(newX ,newY ,previousPoint.orientation ,previousPoint.currentPaint);

       return result;

   }

}
