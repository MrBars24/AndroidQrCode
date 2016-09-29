package com.example.geraldmichael.qrcodeproj;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

public class MainActivity extends AppCompatActivity {
    ImageView qrCodeImgView;
    String qcode;
    public final static int WIDTH=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                qcode = "My first QR code";
                try{
                    synchronized (this){
                        wait(3000);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Bitmap bitmap = null;
                                bitmap = encodeAsBitmap(qcode);
                                qrCodeImgView.setImageBitmap(bitmap);
                            } catch (WriterException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void getID() {
        qrCodeImgView = (ImageView) findViewById(R.id.img_qr_code_image);
    }

    Bitmap encodeAsBitmap(String str) throws WriterException{
        BitMatrix result;
        try{
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        }catch (IllegalArgumentException iae){
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w*h];

        for(int y=0;y<h;y++){
            int offset = y*w;
            for(int x=0; x<w; x++){
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return  bitmap;
    }
}
