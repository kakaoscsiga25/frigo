package test.frigo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.renderscript.Allocation;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentResult;

import static android.content.Context.WINDOW_SERVICE;


/** A basic Camera preview class */
public class CameraPreview_obs extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private static final String TAG = "MyTAG";
    private Context mContext;

    public CameraPreview_obs(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        Camera.Parameters params = mCamera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        mCamera.setDisplayOrientation(90);

        mCamera.setParameters(params);


        mContext = context;


        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


//        try {
//            mCamera.setPreviewDisplay(mHolder);
//        } catch (IOException e) {
//            Log.d(TAG, "Error at preview setting up: " + e.getMessage());
//        }
//        mCamera.setPreviewCallback(this);


        Log.d(TAG, "test");
    }
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        Log.d(TAG, "surfaceCreated called");
        mCamera.startPreview();
        try {
//            mHolder = holder;
//            holder.addCallback(this);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
//            Log.d(TAG, "started preview");
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.d(TAG, "surfaceChanged called");
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        Camera.Parameters params = mCamera.getParameters();
        params.setPictureSize(w,h);
        mCamera.setParameters(params);

        // start preview with new settings
        try {
//            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera cam)
    {
        Log.d(TAG, "call");
        Camera.Parameters parameters = cam.getParameters();
        int width = parameters.getPreviewSize().width;
        int height = parameters.getPreviewSize().height;

        YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);

        byte[] bytes = out.toByteArray();
        final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        /*
        this.setDrawingCacheEnabled(true); //CamView OR THE NAME OF YOUR LAYOUR
        this.buildDrawingCache(true);
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false); // clear drawing cache

//        ImageView iv = (ImageView)this.findViewById(R.id.imageView);
//        iv.setImageBitmap(bmp);


//        Bitmap bMap = Bitmap.createBitmap(mTwod.width(), mTwod.height(), Bitmap.Config.ARGB_8888);
*/
//        Utils.matToBitmap(mTwod, bMap);
        int[] intArray = new int[bmp.getWidth()*bmp.getHeight()];
//copy pixel data from the Bitmap into the 'intArray' array
        bmp.getPixels(intArray, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bmp.getWidth(), bmp.getHeight(),intArray);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//....doing the actually reading

        MultiFormatReader reader = new MultiFormatReader();
        Log.d(TAG, "************ TEST *************");
        try {
            Result result = reader.decode(bitmap);
            Toast.makeText(mContext, result.getText(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, result.getText());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

    /*
    @Override
    public void OnSurfacholder(byte[] data, Camera camera) {
        byte[] jpegData = ConvertYuvToJpeg (data, camera);
//        tesseractApi.SetImage(jpegData);
//
//        displayDEBUG.SetImageBitmap(bytesToBitmap(jpegData));
//        if (tesseractApi.Text != null) {
//            Log.Debug (TAG, tesseractApi.Text);
//        }
    }

    private byte[] ConvertYuvToJpeg(byte[] yuvData, Camera camera)
    {
        Camera.Parameters cameraParameters = camera.getParameters();
        int width = cameraParameters.getPreviewSize().width;
        int height = cameraParameters.getPreviewSize().height;
        YuvImage yuv = new YuvImage(yuvData, cameraParameters.getPreviewFormat(), width, height, null);
        ByteArrayOutputStream ms = new ByteArrayOutputStream();
        int quality = 80;   // adjust this as needed
        yuv.compressToJpeg(new Rect(0, 0, width, height), quality, ms);
        byte[] jpegData = ms.toByteArray();

        return jpegData;
    }

    public static Bitmap bytesToBitmap (byte[] imageBytes)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        return bitmap;
    }*/
}