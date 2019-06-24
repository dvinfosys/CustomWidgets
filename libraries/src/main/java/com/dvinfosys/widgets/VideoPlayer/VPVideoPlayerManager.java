package com.dvinfosys.widgets.VideoPlayer;

public class VPVideoPlayerManager {
    public static VPVideoPlayer FIRST_FLOOR_JCVD;
    public static VPVideoPlayer SECOND_FLOOR_JCVD;

    public static void setFirstFloor(VPVideoPlayer jcVideoPlayer) {
        FIRST_FLOOR_JCVD = jcVideoPlayer;
    }

    public static void setSecondFloor(VPVideoPlayer jcVideoPlayer) {
        SECOND_FLOOR_JCVD = jcVideoPlayer;
    }

    public static VPVideoPlayer getFirstFloor() {
        return FIRST_FLOOR_JCVD;
    }

    public static VPVideoPlayer getSecondFloor() {
        return SECOND_FLOOR_JCVD;
    }

    public static VPVideoPlayer getCurrentJcvd() {
        if (getSecondFloor() != null) {
            return getSecondFloor();
        }
        return getFirstFloor();
    }

    public static void completeAll() {
        if (SECOND_FLOOR_JCVD != null) {
            SECOND_FLOOR_JCVD.onCompletion();
            SECOND_FLOOR_JCVD = null;
        }
        if (FIRST_FLOOR_JCVD != null) {
            FIRST_FLOOR_JCVD.onCompletion();
            FIRST_FLOOR_JCVD = null;
        }
    }
}
