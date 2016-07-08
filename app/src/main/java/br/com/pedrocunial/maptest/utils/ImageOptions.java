package br.com.pedrocunial.maptest.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.com.pedrocunial.maptest.R;

/**
 * Created by summerjob on 07/07/16.
 */

public enum ImageOptions {

    AUDIO_DELAY(R.drawable.audio_delay),
    AUTO_SHUTDOWN(R.drawable.autoshutdown),
    BLUE_SCREEN(R.drawable.blue_screen),
    BW_SCREEN(R.drawable.bw_screen),
    CC(R.drawable.cc),
    CHANNEL_LOCKED(R.drawable.channel_locked),
    CLEAR_PWD(R.drawable.clearpwd),
    CODE4(R.drawable.code4),
    CODE6(R.drawable.code6),
    CODE77(R.drawable.code77),
    CONTROL(R.drawable.control),
    CANT_TURN_ON(R.drawable.dont_turn_on),
    IMAGE_FREEZING(R.drawable.imgfreezing),
    MANY_CODES(R.drawable.manycodes),
    NO_AUDIO(R.drawable.noaudio),
    NO_CHANNEL(R.drawable.nochannel),
    NO_SIGNAL(R.drawable.nosignal),
    PASSWORD(R.drawable.password);

    private final int image;

    private static final List<ImageOptions> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int    SIZE   = VALUES.size();
    private static final Random RANDOM = new Random();

    ImageOptions(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public static int getRandomImage() {
        return VALUES.get(RANDOM.nextInt(SIZE)).getImage();
    }
}
