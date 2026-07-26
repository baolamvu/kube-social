package com.kubesocial.modules.study;
import com.kubesocial.modules.study.TaiLieu;
import java.util.ArrayList;
import java.util.List;

public class QuanLyThuVienService {

    private ArrayList<TaiLieu> listTaiLieu = new ArrayList<>();

    public List<TaiLieu> getListTaiLieu() {
        return this.listTaiLieu;
    }

    public void addTaiLieu(TaiLieu tailieu) {
        this.listTaiLieu.add(tailieu);
    }

    public void removeTaiLieu(TaiLieu tailieu) {
        this.listTaiLieu.remove(tailieu);
    }

    public List<TaiLieu> getTaiLieuByType(String object) {
        ArrayList<TaiLieu> files = new ArrayList<>();
        for(TaiLieu tailieu: this.listTaiLieu) {
            if (object.equals("sach") && tailieu instanceof Sach) {
                files.add(tailieu);
            }
        }
        return files;
    }

}
