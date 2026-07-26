package com.kubesocial.modules.study;

abstract class TaiLieu {

    protected String maTaiLieu;

    protected String nhaXuatBan;

    protected Integer soBanPhatHanh;

    protected TaiLieu(String maTaiLieu, String nhaXuatBan, Integer soBanPhatHanh) {
        this.maTaiLieu = maTaiLieu;
        this.nhaXuatBan = nhaXuatBan;
        this.soBanPhatHanh = soBanPhatHanh;
    }


    public String getMaTaiLieu() {
        return this.maTaiLieu;
    }

    public TaiLieu setMaTaiLieu(String maTailieu) {
        this.maTaiLieu = maTailieu;
        return this;
    }

    public String getNhaXuatBan() {
        return this.nhaXuatBan;
    }

    public TaiLieu setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
        return this;
    }

    public Integer getSobanphathanh() {
        return this.soBanPhatHanh;
    }

    public TaiLieu setSobanPhatHanh(Integer soBanPhatHanh) {
        this.soBanPhatHanh = soBanPhatHanh;
        return this;
    }

    public abstract String loaiGiay();

}
