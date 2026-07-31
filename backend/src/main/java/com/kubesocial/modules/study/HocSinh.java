package com.kubesocial.modules.study;

public class HocSinh {

    private Integer maHocSinh;

    private String tenHocSinh;

    private Double diemTrungBinh;

    public HocSinh(Integer maHocSinh, String tenHocSinh, Double diemTrungBinh) {
        this.maHocSinh = maHocSinh;
        this.tenHocSinh = tenHocSinh;
        this.diemTrungBinh = diemTrungBinh;
    }

    public Integer getMaHocSinh() {
        return maHocSinh;
    }

    public String getTenHocSinh() {
        return tenHocSinh;
    }

    public Double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setMaHocSinh(Integer maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public void setTenHocSinh(String tenHocSinh) {
        this.tenHocSinh = tenHocSinh;
    }

    public void setDiemTrungBinh(Double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

}
