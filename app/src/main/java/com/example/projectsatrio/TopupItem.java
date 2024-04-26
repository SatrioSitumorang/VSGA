package com.example.projectsatrio;

public class TopupItem {
    private String mataUang;
    private String keterangan;

    public TopupItem(String mataUang, String keterangan) {
        this.mataUang = mataUang;
        this.keterangan = keterangan;
    }

    public String getMataUang() {
        return mataUang;
    }

    public String getKeterangan() {
        return keterangan;
    }
}

