package cn.toby.imagemaskoutputor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final static int PICK_IMAGE_ORIG_REQUEST = 1;
    private final static int PICK_IMAGE_MASK_REQUEST = 2;

    private ImageView resultImageView;
    private Uri lastOriginalUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultImageView = (ImageView) findViewById(R.id.result_image_view);
    }

    private void selectImage(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"选择图像..."), requestCode);
    }

    public void selectOrigImage(View view) {
        selectImage(PICK_IMAGE_ORIG_REQUEST);
    }

    public void selectMaskImage(View view) {
        selectImage(PICK_IMAGE_MASK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK == resultCode && null != data && null != data.getData()) {
            Uri uri = data.getData();
            try {
                if(PICK_IMAGE_ORIG_REQUEST == requestCode) {
                    lastOriginalUri = uri;
                    Bitmap origImage = ImageMaskOutput.getBitmapFromUri(this, uri);
                    resultImageView.setImageBitmap(origImage);
                } else if (PICK_IMAGE_MASK_REQUEST == requestCode && null != lastOriginalUri) {
                    Bitmap resultImage = ImageMaskOutput.createBasedOnOriginalSize(this,
                            lastOriginalUri , uri);
                    resultImageView.setImageBitmap(resultImage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
