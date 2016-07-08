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

    private boolean      isLarge;
    private ImageView    icon;
    private ImageView    iconLarge;
    private LinearLayout footer;
    private LinearLayout largeFooter;

    private final String TAG = this.toString();

    public FooterOnClickListener(boolean isLarge,
                                 LinearLayout footer, LinearLayout largeFooter,
                                 ImageView icon, ImageView iconLarge) {
        this.isLarge     = isLarge;
        this.footer      = footer;
        this.largeFooter = largeFooter;
        this.icon        = icon;
        this.iconLarge   = iconLarge;
    }

    @Override
    public void onClick(View v) {
        if(isLarge) {
            Log.i(TAG, "Ficou pequeno e visivel");
            largeFooter.setVisibility(View.INVISIBLE);
            footer.setVisibility(View.VISIBLE);
            icon.setVisibility(View.VISIBLE);
            iconLarge.setVisibility(View.INVISIBLE);

        } else {
            Log.i(TAG, "Ficou grande, birl, e borrado");
            largeFooter.setVisibility(View.VISIBLE);
            footer.setVisibility(View.INVISIBLE);
            icon.setVisibility(View.INVISIBLE);
            iconLarge.setVisibility(View.VISIBLE);
        }
        isLarge = !isLarge;
    }
}
