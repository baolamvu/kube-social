package com.kubesocial.modules.study;
import com.kubesocial.modules.study.TaiLieu;

public class Sach extends TaiLieu {

    protected String tenTacGia;

    protected Integer soTrang;

    public Sach(String maTaiLieu, String nhaXuatBan, Integer soBanPhatHanh, String tenTacGia, Integer soTrang) {
        super(maTaiLieu, nhaXuatBan, soBanPhatHanh);
        this.tenTacGia = tenTacGia;
        this.soTrang = soTrang;
    }

    public String getTenTacGia() {
        return this.tenTacGia;
    }

    public Sach setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
        return this;
    }

    public Integer getSoTrang() {
        return this.soTrang;
    }

    public Sach setSoTrang(Integer soTrang) {
        this.soTrang = soTrang;
        return this;
    }

    public String loaiGiay() {
        return "giay sach";
    }

}
