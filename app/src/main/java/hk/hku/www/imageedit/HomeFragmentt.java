package hk.hku.www.imageedit;

import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class HomeFragmentt extends TabFragment {
    private View view;
    //图片选择器
    private final int REQUEST_CODE_GALLERY = 1001;
    private Button openButton,uploadButton;
    private ImageView selectResult;
    private Uri uri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        view=inflater.inflate(R.layout.activity_imageuplading,container,false);
        Log.i("wangshulin", "onCreateView: "+getView());
        selectResult=view.findViewById(R.id.iv_select_image_result);
        openButton = (Button)view.findViewById(R.id.btn_open_gallery);
        uploadButton=(Button)view.findViewById(R.id.btn_upload_image);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
            }
        });
        return view;
    }
    /**
     * 选取图片后的回调
     */
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        File file;
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                Log.e("onHanlderSuccess: ", resultList.get(0).getPhotoPath());
                file=new File(resultList.get(0).getPhotoPath());
                uri=Uri.fromFile(file);
                selectResult.setImageURI(uri);
                uploadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ImageUpload.run(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Log.e("onHanlderSuccess: ", errorMsg);
        }
    };
}

