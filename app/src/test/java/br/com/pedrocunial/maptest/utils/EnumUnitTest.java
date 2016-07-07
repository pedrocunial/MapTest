package br.com.pedrocunial.maptest.utils;

import android.media.Image;

import org.junit.*;

import br.com.pedrocunial.maptest.R;

/**
 * Created by summerjob on 07/07/16.
 */
public class EnumUnitTest {

    @Test
    public void isAudioDelay() {
        Assert.assertEquals(R.drawable.audio_delay, ImageOptions.AUDIO_DELAY.getImage());
    }

    @Test
    public void isAutoShutdown() {
        Assert.assertEquals(R.drawable.autoshutdown, ImageOptions.AUTO_SHUTDOWN.getImage());
    }

    @Test
    public void isBlueScreen() {
        Assert.assertEquals(R.drawable.blue_screen, ImageOptions.BLUE_SCREEN.getImage());
    }

    @Test
    public void isBWScreen() {
        Assert.assertEquals(R.drawable.bw_screen, ImageOptions.BW_SCREEN.getImage());
    }

    @Test
    public void isCC() {
        Assert.assertEquals(R.drawable.cc, ImageOptions.CC.getImage());
    }

    @Test
    public void isChannelLocked() {
        Assert.assertEquals(R.drawable.channel_locked, ImageOptions.CHANNEL_LOCKED.getImage());
    }

    @Test
    public void isClearPWD() {
        Assert.assertEquals(R.drawable.clearpwd, ImageOptions.CLEAR_PWD.getImage());
    }

    @Test
    public void isCode4() {
        Assert.assertEquals(R.drawable.code4, ImageOptions.CODE4.getImage());
    }

    @Test
    public void isCode6() {
        Assert.assertEquals(R.drawable.code6, ImageOptions.CODE6.getImage());
    }

    @Test
    public void isCode77() {
        Assert.assertEquals(R.drawable.code77, ImageOptions.CODE77.getImage());
    }

    @Test
    public void isControl() {
        Assert.assertEquals(R.drawable.control, ImageOptions.CONTROL.getImage());
    }

    @Test
    public void isCantTurnOn() {
        Assert.assertEquals(R.drawable.dont_turn_on, ImageOptions.CANT_TURN_ON.getImage());
    }

    @Test
    public void isImgFreezing() {
        Assert.assertEquals(R.drawable.imgfreezing, ImageOptions.IMAGE_FREEZING.getImage());
    }

    @Test
    public void isManyCodes() {
        Assert.assertEquals(R.drawable.manycodes, ImageOptions.MANY_CODES.getImage());
    }

    @Test
    public void isNoAudio() {
        Assert.assertEquals(R.drawable.noaudio, ImageOptions.NO_AUDIO.getImage());
    }

    @Test
    public void isNoChannel() {
        Assert.assertEquals(R.drawable.nochannel, ImageOptions.NO_CHANNEL.getImage());
    }

    @Test
    public void isNoSignal() {
        Assert.assertEquals(R.drawable.nosignal, ImageOptions.NO_SIGNAL.getImage());
    }

    @Test
    public void isPassword() {
        Assert.assertEquals(R.drawable.password, ImageOptions.PASSWORD.getImage());
    }
}
