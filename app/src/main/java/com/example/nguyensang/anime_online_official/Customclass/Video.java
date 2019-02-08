package com.example.nguyensang.anime_online_official.Customclass;

/**
 * Created by NguyenSang on 04/27/2018.
 */

public class Video {
    private String tapSo;
    private String linkPlay;

    public Video(String tapSo, String linkPlay) {
        this.tapSo = tapSo;
        this.linkPlay = linkPlay;
    }

    public Video() {
    }

    public String getTapSo() {
        return tapSo;
    }

    public String getLinkPlay() {
        return linkPlay;
    }

    public void setTapSo(String tapSo) {
        this.tapSo = tapSo;
    }

    public void setLinkPlay(String linkPlay) {
        this.linkPlay = linkPlay;
    }
}
