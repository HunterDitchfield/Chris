package view.surface.hunter.chris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Main extends Activity{
    OurView ourView;//'OurView' is a class we created to handle drawing and running our game.
    Context context;//'Context' is a special class that allows us to access many and various actions in our application.
    Activity activity;//'Activity' is another special class that allows us to access many and various actions in our activity.
    RelativeLayout layout;//'RelativeLayout' is the base for all our drawing implementations. We will use it to add 'OurView', which is a drawing class.

    Game game;//'Game' is another class that I created. I separated it from class Main to be more organized.

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate(Bundle savedInstanceState) is called when our application is opened. Everything we do in the brackets is something we only want to do once and before our game starts.
        super.onCreate(savedInstanceState);//Automatically generated
        ourView = new OurView(this);//OurView is a class we created near code line 78. The class allows us to draw to the device screen.
        context = this;//Every activity has a context. 'this' gets our activity's context. Our activity is Main.
        activity = this;//This is putting Main into a variable.

        //HOW TO CREATE AN ICON SHORTCUT ON THE HOME SCREEN
        SharedPreferences prefs = getSharedPreferences("icon_shortcut", MODE_PRIVATE);//SharedPreferences gives you access to read and write data variables to the device. Think of a variable that we never want to be erased. A common use of this is storing high scores.
        SharedPreferences.Editor ed = prefs.edit();//SharedPreferences.Editor allows you to write to your data variables.
        if (!prefs.getBoolean("add_icon_shortcut", false)) {//We are checking our data for the value of the variable 'add_icon_shortcut'. We also specify a default value, in case it hasn't been created.
            Intent shortcutIntent = new Intent(getApplicationContext(), Main.class);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Intent addIntent = new Intent();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getApplicationContext().sendBroadcast(addIntent);

            ed.putBoolean("add_icon_shortcut", true);//Edit the variable 'add_icon_shortcut' to 'true' so that we don't keep creating icons every time the app is launched.
            ed.apply();//Save our edit
        }

        //HOW TO MAKE YOUR APPLICATION FULLSCREEN
        ourView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide navigation bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        //SETUP YOUR LAYOUT
        layout = new RelativeLayout(context);//Create a new layout with our applications 'context'. It is good to have a common layout so that you can change or add new types of drawing interfaces. We can use this to display anything else we can think of, anything from our drawings, to an advertisement.
        layout.addView(ourView);//OurView is definitely something we want to display to the screen, since it is our custom drawings.
        activity.setContentView(layout);//We need to pass our new layout into our activity so that it knows about it.
    }

    //HOW TO KEEP YOUR APPLICATION IN FULLSCREEN MODE
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {//Every single time the focus of our application changes, 'onWindowFocusChanged' will intercept that change. Examples are, exiting the application or a virtual keyboard popping up. All these can ruin your fullscreen mode.
        super.onWindowFocusChanged(hasFocus);
        //We use the same code to make our application fullscreen.
        getWindow().getDecorView().setSystemUiVisibility(//This is a new way of doing the same thing. ('getWindow().getDecorView()' versus 'ourView')
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION// hide navigation bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN// hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    private boolean run = true;//This variable will keep track of whether we can call 'onDraw' or not.
    private boolean once = true;//This variable is used to initiate our game, then we set it to false so that we don't keep making a new game.

    public class OurView extends View {//This class is called OurView and is set to class 'View', which has our canvas. The canvas is the phone's screen that we can soon draw to.
        public OurView(Context myContext) {//This is called a constructor and is auto-generated by class 'View'.
            super(myContext);
        }

        @Override
        protected void onDraw(Canvas canvas){//This is where we can get the 'canvas' variable. This gets called with the function 'invalidate()'.
            if (once){//Remember we only want to create our game once.
                game = new Game(canvas, canvas.getWidth(), canvas.getHeight(), Main.this, this, Main.this, layout);//Pass in all the vital variables we have created into our 'Game' class.
                once = false;//Set once to false so that we don't set a new game.
            }
            game.Run(canvas);//Now that we have a variable reference to our 'Game' we can use it to run our game! Since 'canvas' is given to us through 'onDraw', we need to give it ro 'Run'.

            if (run) {//Before we 'invalidate()', check our run variable to make sure we should be doing that.
                invalidate();//This calls the function 'onDraw(Canvas canvas)'... yes we are calling that from within itself. This will create an endless cycle of it running through itself. Now 'game.Run()' will be called over and over so that we can run a game.
            }
        }

        public void pause() {
            run = false;//If our application is exited, we set run to false. We don't want to be running if our application is not being used.
        }

        public void resume() {
            run = true;//Set run to true because our application is running again
            invalidate();//Start the endless game cycle again!
        }
    }

    @Override
    protected void onPause() {//If our application is paused, 'onPause()' will intercept that and run this code.
        super.onPause();//Auto-generated, can't be deleted. It calls another 'onPause()' that runs the android creator's code, we don't have permission to that.
        ourView.pause();//Use our variable ourView of OurView to call the function 'pause()'.
    }

    @Override
    protected void onResume() {//If our application is resumed, 'onResume()' will intercept that and run this code.
        super.onResume();//Auto-generated, can't be deleted. It calls another 'onResume()' that runs the android creator's code, we don't have permission to that.
        ourView.resume();//Use our variable ourView of OurView to call the function 'resume()'.
    }

    @Override
    public void onBackPressed() {//This function intercepts whenever the back button is pressed. We aren't going to run any code if this happens yet, but I wanted you to know about it.

    }
}