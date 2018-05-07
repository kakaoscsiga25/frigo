package test.frigo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.renderscript.Allocation;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.util.Log;
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


/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private static final String TAG = "MyTAG";
    private Context mContext;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mContext = context;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            call();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
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

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    void call()
    {
        this.setDrawingCacheEnabled(true); //CamView OR THE NAME OF YOUR LAYOUR
        this.buildDrawingCache(true);
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false); // clear drawing cache

//        ImageView iv = (ImageView)this.findViewById(R.id.imageView);
//        iv.setImageBitmap(bmp);

        MultiFormatReader reader = new MultiFormatReader();


//        Bitmap bMap = Bitmap.createBitmap(mTwod.width(), mTwod.height(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(mTwod, bMap);
        int[] intArray = new int[bmp.getWidth()*bmp.getHeight()];
//copy pixel data from the Bitmap into the 'intArray' array
        bmp.getPixels(intArray, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bmp.getWidth(), bmp.getHeight(),intArray);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//....doing the actually reading

        Log.d(TAG, "************ TEST *************");
        try {
            Result result = reader.decode(bitmap);
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