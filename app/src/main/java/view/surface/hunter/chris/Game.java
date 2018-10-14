package view.surface.hunter.chris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

class Game implements View.OnTouchListener {
    //HERE OUR OUR SPECIAL VARIABLES
    private Canvas canvas;
    private int canvasWidth;
    private int canvasHeight;
    private Context context;
    private View view;
    private Activity activity;
    private RelativeLayout relativeLayout;

    //HERE ARE OUR GAME VARIABLES

    //PAINT VARIABLES
    private Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//When we say ' = new Paint()' the Paint class accepts specifications like 'Paint.ANTI_ALIAS_FLAG' which smooths out our drawings.
    private Paint bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint yellowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //RECTANGLE VARIABLES
    private Rect smallRect;
    private Rect bigRect;
    private Rect longRect;
    private Rect tallRect;

    //RELATIVE VARIABLES
    private int longRectX;//This will change as we run the game.

    Game(Canvas myCanvas, int myCanvasWidth, int myCanvasHeight, Context myContext, View myView, Activity myActivity, RelativeLayout myRelativeLayout) {//Remember when we use the line of code 'game = new Game(canvas, canvas.getWidth(), canvas.getHeight(), Main.this, this, Main.this, layout);', this is the function it calls.
        //Set up our special variables so that we can use them throughout this whole class.
        canvas = myCanvas;
        canvasWidth = myCanvasWidth;
        canvasHeight = myCanvasHeight;
        context = myContext;
        view = myView;
        activity = myActivity;
        relativeLayout = myRelativeLayout;

        //MISCELLANEOUS ACTIONS
        view.setOnTouchListener(this);//Touches won't register unless you set up a listener to intercept touch actions.

        //SETUP OUR VARIABLES

        //PAINT VARIABLES
        redPaint.setColor(Color.RED);
        redPaint.setAlpha(255 / 2);//This is the transparency setting. 255 is max transparency, we want half transparency.
        bluePaint.setColor(Color.rgb(0, 255, 255));//We could use 'Color.BLUE' value, but I want to make a custom light blue.
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStrokeWidth(scaledXInt(20));//Scaling it with 'scaledXInt' will make it have the same thickness across all devices.
        yellowPaint.setColor(Color.argb(50, 255, 200, 0));//We are setting a custom color with transparency.

        //RELATIVE VARIABLES
        longRectX = percentXInt(40);

        //RECTANGLE VARIABLES
        smallRect = new Rect(percentXInt(10), percentXInt(10), percentXInt(15), percentXInt(15));//Its not bad to use percentXInt instead of percentYInt in the y measurements. I did it so that we could get the rect in the corner with the same amount of distance from the left of the screen as the top.
        bigRect = new Rect(percentXInt(25), percentXInt(5), percentXInt(50), percentXInt(30));//We need to set Rect(left, top, right, bottom)
        longRect = new Rect(longRectX, percentXInt(40), longRectX + percentXInt(40), percentXInt(50));//Lets use our variables to set up longRect.
        tallRect = new Rect(percentXInt(75), percentXInt(5), percentXInt(85), percentXInt(45));
    }

    void Run(Canvas myCanvas) {//Remember we passed canvas to 'Run', so now we need to receive it.
        //UPDATE GAME
        longRectX++;//Increase longREctX by 1.
        longRect.left = longRectX;
        longRect.right = longRectX + percentXInt(40);

        //DRAW GAME
        canvas = myCanvas;//Update 'canvas' to equal the new canvas passed to us all the back from 'onDraw'.
        canvas.drawColor(Color.WHITE);//Lets set a background.
        canvas.drawRect(smallRect, redPaint);//'drawRect' needs only 2 parameters. Lets put in a rect and paint.
        canvas.drawRect(bigRect, bluePaint);
        canvas.drawRect(longRect, greenPaint);
        canvas.drawRect(tallRect, yellowPaint);//Type the word 'canvas', look at the different things we draw!
    }

    private int percentXInt(double x) {//These methods will scale any value you pass so that your graphics will look the same across all devices.
        return (int) (x * canvasWidth / 100);
    }

    private int percentYInt(double y) {
        return (int) (y * canvasHeight / 100);
    }

    private int scaledXInt(int x) {
        return (int) (canvasWidth / (1500 / (float) x));
    }

    private int scaledYInt(int y) {
        return (int) (canvasHeight / (1000 / (float) y));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {//Now we can handle user touches!
        int x = (int) motionEvent.getX();//get the coordinates of the touch.
        int y = (int) motionEvent.getY();//get the coordinates of the touch.

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                longRect = new Rect(x, y, x + percentXInt(40), y + percentXInt(10));//Set the rect to the coordinates of the touch.
                longRectX = x;//Make sure to set this variable to x or else the x cord will not change when the game keeps running.
            }
        }
        return true;//If true, onTouch will iterate through more touch events. If false, onTouch will not be called for any more actions. (multiple events are triggered in one touch. if you touch the screen, ACTION_DOWN and ACTION_MOVE will be triggered).
    }
}
