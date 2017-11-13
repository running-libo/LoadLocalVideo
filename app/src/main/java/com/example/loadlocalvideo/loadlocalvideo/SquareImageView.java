package com.example.loadlocalvideo.loadlocalvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by libo on 2017/4/17.
 */

public class SquareImageView extends ImageView{

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);  //设置高度始终等于宽度，即为正方形
    }
}
