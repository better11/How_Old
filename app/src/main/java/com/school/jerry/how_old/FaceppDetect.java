package com.school.jerry.how_old;

import android.graphics.Bitmap;
import android.util.Log;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by jerry on 2015/11/9.
 */
public class FaceppDetect {
    public interface CallBack
    {
        void success(JSONObject results);
        void error(FaceppParseException exception);
    }
    public static void detect(final Bitmap bm,final CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpRequests requests=new HttpRequests(Content.KEY,Content.SECRET,true,true);
                    Bitmap bmSmall=Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bmSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] arrays=stream.toByteArray();

                    PostParameters params=new PostParameters();
                    params.setImg(arrays);
                    JSONObject jsonObject = requests.detectionDetect(params);

                    //Log.e("TAG",jsonObject.toString());
                    if(callBack!=null){
                        callBack.success(jsonObject);
                    }
                } catch (FaceppParseException e) {
                    e.printStackTrace();
                    if(callBack!=null){
                        callBack.error(e);
                    }
                }
            }
        }).start();
    }
}
