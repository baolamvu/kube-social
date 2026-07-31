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

    public List<TaiLieu> getTaiLieuByType(Class<? extends TaiLieu> classType) {
        ArrayList<TaiLieu> files = new ArrayList<>();
        for(TaiLieu tailieu: this.listTaiLieu) {
            if (classType.isInstance(tailieu)) {
                files.add(tailieu);
            }
        }
        return files;
    }

    public List<TaiLieu> getTaiLieuByTypeNewVer(Class<? extends TaiLieu> classType) {
        return this.listTaiLieu.stream()
            .filter(classType::isInstance)
            .toList();
    }

}
