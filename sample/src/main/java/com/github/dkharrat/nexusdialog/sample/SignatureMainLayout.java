package com.github.dkharrat.nexusdialog.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Nourhan on 9/20/2016.
 */

public class SignatureMainLayout extends LinearLayout implements View.OnClickListener {

    LinearLayout buttonsLayout;
    SignatureView signatureView;
    public String fileName;
    Button clearBtn;

    public SignatureMainLayout(Context context) {
        super(context);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.white_transperent_color));

        this.signatureView = new SignatureView(context, Environment.getExternalStorageDirectory().getAbsolutePath());
        this.buttonsLayout = this.buttonsLayout();

        // add the buttons and signature views
        this.addView(signatureView);
        this.addView(this.buttonsLayout);
    }

    public Button getClearButton() {
        return clearBtn;
    }

    public SignatureMainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);

        this.buttonsLayout = this.buttonsLayout();
        this.signatureView = new SignatureView(context, Environment.getExternalStorageDirectory().getAbsolutePath());

        // add the buttons and signature views
        this.addView(signatureView);
        this.addView(this.buttonsLayout);
    }

    private void init(final AttributeSet attrs) {
        if (attrs != null) {
            String packageName = "http://schemas.android.com/apk/res-auto";
            fileName = attrs.getAttributeValue(packageName, "fileName");
        }
    }

    private LinearLayout buttonsLayout() {

        // create the UI programatically
        LinearLayout linearLayout = new LinearLayout(this.getContext());
//        Button saveBtn = new Button(this.getContext());
        clearBtn = new Button(this.getContext());

        // set orientation
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        this.setBackgroundColor
                (ContextCompat.getColor(this.getContext(), R.color.white_transperent_color));
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 15);
        linearLayout.setLayoutParams(params);

        clearBtn.setText(this.getContext().getResources().getString(R.string.cancel));
        clearBtn.setTextSize(20);
        clearBtn.setTag("cancel");
        clearBtn.setTextColor(Color.WHITE);
        clearBtn.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));
        clearBtn.setOnClickListener(this);

        linearLayout.addView(clearBtn);

        // return the whoe layout
        return linearLayout;
    }

    // the on click listener of 'save' and 'clear' buttons
    @Override
    public void onClick(View v) {
        this.signatureView.clearSignature();
    }

    /**
     * save the signature to an sd card directory
     */
    public void saveImage(String fname, String dirPath) {

        Bitmap signature = this.signatureView.getSignature();
//        int taskID = PME_Application.getInstance().getCurrentTaskId();
        // set the file name of your choice
//        String fname = "sign.png";
        File directory = new File(dirPath);
        saveBitMapToFile(signature, directory, fname);

//        if (this.getVisibility() == VISIBLE) {
//            fname = "sign" + PME_Application.getInstance().getCurrentTaskId() + ".png";
//            File imgPrintReportDirectory = new File(PME_Application.getInstance().printReportDirPath() + "/images");
//            saveBitMapToFile(signature, imgPrintReportDirectory, fname);
//        }
    }

    public void saveBitMapToFile(Bitmap signature, File directory, String fname) {
        if (!directory.exists())
            directory.mkdirs();


        // in our case, we delete the previous file, you can remove this
        File file = new File(directory, fname);
        if (file.exists()) {
            file.delete();
        }
        try {

            // save the signature
            FileOutputStream out = new FileOutputStream(file);
            signature.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The View where the signature will be drawn
     */
    public class SignatureView extends View {

        // set the stroke width
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public SignatureView(Context context, String dirPath) {

            super(context);

            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);

            Bitmap bMap = BitmapFactory.decodeFile(dirPath + fileName);
            if (bMap != null) {
                Drawable d = new BitmapDrawable(bMap);
                this.setBackground(d);
            } else {
                // set the bg color as white
                this.setBackgroundColor(Color.WHITE);
            }

            // width and height should cover the screen
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    400);
            params.setMargins(0, 15, 0, 0);
            this.setLayoutParams(params);
//            this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        }

        /**
         * Get signature
         *
         * @return
         */
        protected Bitmap getSignature() {

            Bitmap signatureBitmap = null;

            // set the signature bitmap
            if (signatureBitmap == null) {
                signatureBitmap =
                        Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.RGB_565);
            }

            // important for saving signature
            final Canvas canvas = new Canvas(signatureBitmap);
            this.draw(canvas);

            return signatureBitmap;
        }

        /**
         * clear signature canvas
         */
        private void clearSignature() {
            path.reset();
            this.setBackgroundColor(Color.WHITE);
            this.invalidate();

        }

        // all touch events during the drawing
        @Override
        protected void onDraw(Canvas canvas) {
            getParent().requestDisallowInterceptTouchEvent(true);
            canvas.drawPath(this.path, this.paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
//            hideKeyboard();
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    path.moveTo(eventX, eventY);

                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);

                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:

                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }

    }


}
