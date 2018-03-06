package cn.toby.imagemaskoutputor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;

import java.io.InputStream;

/**
 * Created by toby on 18-3-2.
 */

final public class ImageMaskOutput {

    public static Bitmap createBasedOnOriginalSize(Context context, Uri originalUri, Uri maskUri) {
        return createBasedOnOriginalSize(getBitmapFromUri(context, originalUri),
                getBitmapFromUri(context, maskUri));
    }

    public static Bitmap createBasedOnOriginalSize(String originalPath, String maskPath) {
        return createBasedOnOriginalSize(getBitmapFromFilePath(originalPath),
                getBitmapFromFilePath(maskPath));
    }

    public static Bitmap createBasedOnOriginalSize(Bitmap original, Bitmap mask) {
        Bitmap result = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        return createBased(original, mask, result);
    }

    public static Bitmap createBasedOnMaskSize(Context context, Uri originalUri, Uri maskUri) {
        return createBasedOnMaskSize(getBitmapFromUri(context, originalUri),
                getBitmapFromUri(context, maskUri));
    }

    public static Bitmap createBasedOnMaskSize(String originalPath, String maskPath) {
        return createBasedOnMaskSize(getBitmapFromFilePath(originalPath),
                getBitmapFromFilePath(maskPath));
    }

    public static Bitmap createBasedOnMaskSize(Bitmap original, Bitmap mask) {
        Bitmap result = Bitmap.createBitmap(mask.getWidth(),
                mask.getHeight(), Bitmap.Config.ARGB_8888);
        return createBased(original, mask, result);
    }

    private static Bitmap createBased(Bitmap original, Bitmap mask, Bitmap result) {
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

    private static Bitmap getBitmapFromFilePath(String filePath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(filePath, bmOptions);
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            InputStream input = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeStream(input, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
