package com.example.nguyensang.anime_online_official.Customclass;

/**
 * Created by NguyenSang on 04/23/2018.
 */

public class TheLoai {
    private String tenTheLoai;
    private String linkTheLoai;
    private int background;

    public TheLoai(String tenTheLoai, String linkTheLoai) {
        this.tenTheLoai = tenTheLoai;
        this.linkTheLoai = linkTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }
    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
    public String getLinkTheLoai() {
        return linkTheLoai;
    }
    public void setLinkTheLoai(String linkTheLoai) {
        this.linkTheLoai = linkTheLoai;
    }
    public int getBackground() {
        return background;
    }
    public void setBackground(int background) {
        this.background = background;
    }
}
