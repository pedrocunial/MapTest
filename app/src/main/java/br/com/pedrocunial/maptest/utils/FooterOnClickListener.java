package br.com.pedrocunial.maptest.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by summerjob on 07/07/16.
 */
public class FooterOnClickListener implements View.OnClickListener {

    private float        y;
    private boolean      isLarge;
    private ImageView    icon;
    private LinearLayout footer;
    private LinearLayout largeFooter;

    private final String TAG   = this.toString();

    public FooterOnClickListener(Context context, boolean isLarge,
                                 LinearLayout footer, LinearLayout largeFooter,
                                 ImageView icon) {
        this.isLarge     = isLarge;
        this.footer      = footer;
        this.largeFooter = largeFooter;
        this.icon        = icon;
        this.y           = Converter.pxFromDp(context, 140);
    }

    @Override
    public void onClick(View v) {
        if(isLarge) {
            largeFooter.setVisibility(View.INVISIBLE);
            footer.setVisibility(View.VISIBLE);
            icon.setY(y);
        } else {
            largeFooter.setVisibility(View.VISIBLE);
            footer.setVisibility(View.INVISIBLE);
            icon.setY(0);
        }
        isLarge = !isLarge;
    }
}
