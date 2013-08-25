package de.bsd.playspace;

import android.content.Intent;
import de.bsd.playspace.commands.AbstractDrawCommand;
import de.bsd.playspace.commands.Left;
import de.bsd.playspace.commands.Lineto;
import de.bsd.playspace.commands.Move;
import de.bsd.playspace.commands.Right;
import de.bsd.playspace.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    private DrawView contentView;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        contentView = (DrawView) findViewById(R.id.fullscreen_content);

        AbstractDrawCommand c = new Lineto(50);
        contentView.addCommand(c);
        c = new Lineto(50);
       contentView.addCommand(c);
        c = new Lineto(50);
       contentView.addCommand(c);
        c = new Left(45);
        contentView.addCommand(c);
        c = new Lineto(90);
       contentView.addCommand(c);
        c = new Right();
        contentView.addCommand(c);
        c = new Lineto(80);
       contentView.addCommand(c);
        c = new Left(45);
        contentView.addCommand(c);

        for (int i = 0; i < 192; i++) {
            c= new Lineto(80);
            contentView.addCommand(c);
           if (i%8==0)
               c= new Left(30);
           else
            c = new Left(45);
            contentView.addCommand(c);
        }

     c = new Lineto(80);
       contentView.addCommand(c);


        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.edit_button).setOnTouchListener(mDelayHideTouchListener);
        findViewById(R.id.run_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
        contentView.invalidate();
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void rerun(View view) {
        contentView.invalidate();
    }

   public void edit(View view) {

      Intent intent = new Intent(this,EditAction.class);

      ArrayList<String> commandList = new ArrayList<String>();
      for (AbstractDrawCommand c : contentView.getCommands()) {
         commandList.add(c.getClass().getSimpleName());
      }
      intent.putStringArrayListExtra("commands",commandList);
       startActivityForResult(intent, 2);

   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode==2 && resultCode == RESULT_OK) {
         contentView.clean();
         if (data==null)
            return;

         List<String> commands =  data.getStringArrayListExtra("commands");

         for (String command : commands) {
            String m = command.substring(command.indexOf(';')+1);
            Integer i;
            try {
               i = Integer.valueOf(m);
            }
            catch (NumberFormatException nfe) {
               i = null;
            }
            AbstractDrawCommand c;
            // TODO make the next language dependent. Or totally independent
            if (command.startsWith("left")) {
               c = new Left(i);
            } else if (command.startsWith("line")) {
               c = new Lineto(i);
            } else if (command.startsWith("right")) {
               c = new Right(i);
            } else if (command.startsWith("move")) {
               c = new Move(i);
            } else {
               throw new IllegalArgumentException("Unknown command " + command);
            }
            contentView.addCommand(c);
         }
         contentView.invalidate();
      }
   }
}
