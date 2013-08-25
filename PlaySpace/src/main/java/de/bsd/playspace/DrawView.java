package de.bsd.playspace;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import de.bsd.playspace.commands.AbstractDrawCommand;

/**
 * Created by hrupp on 17.08.13.
 */
public class DrawView extends View {

    List<AbstractDrawCommand> commands = new ArrayList<AbstractDrawCommand>();
    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float middleX = canvas.getWidth() / 2;
        float middleY = canvas.getHeight() / 2;

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3f);
        paint.setStrokeJoin(Paint.Join.ROUND);

        OPoint startingPoint = new OPoint(middleX, middleY, -90, paint); // TODO start at 0/0

        int i =0;
        for (AbstractDrawCommand command: commands) {
            if (i%3==0) {
                paint.setColor(Color.BLACK);
            } else {
                paint.setColor(Color.RED);
            }

//            System.out.println("New point: " + startingPoint.toString() + " c=" + command.getClass().getSimpleName());
            startingPoint = command.drawSelf(canvas, startingPoint);  // Translate coordinates system to center of screen
            i++;
        }



    }

    public void addCommand(AbstractDrawCommand line) {
        if (commands ==null)
            commands = new ArrayList<AbstractDrawCommand>();
        commands.add(line);
    }

    public void clean() {
       commands.clear();
       invalidate();
    }

   public List<AbstractDrawCommand> getCommands() {
      return commands;
   }
}
