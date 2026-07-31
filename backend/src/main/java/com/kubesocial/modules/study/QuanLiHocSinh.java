package com.kubesocial.modules.study;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

public class QuanLiHocSinh {

    private ArrayList<HocSinh> listHocSinh;

    public QuanLiHocSinh() {
        this.listHocSinh = new ArrayList<>();
    }

    public void addHocSinh(HocSinh hocsinh) {
        this.listHocSinh.add(hocsinh);
    }

    public void removeHocSinh(HocSinh hocsinh) {
        this.listHocSinh.remove(hocsinh);
    }

    public ArrayList<HocSinh> getListHocSinh() {
        return this.listHocSinh;
    }

    public List<HocSinh> sortHocSinhTheoDiemGiamDan() {
        return this.listHocSinh.stream().sorted((hs1, hs2) -> hs2.getDiemTrungBinh().compareTo(hs1.getDiemTrungBinh())).toList();
    }

    public HocSinh getHocSinhDiemCaoNhat() {
        return this.listHocSinh.stream().max(Comparator.comparingDouble(HocSinh::getDiemTrungBinh)).orElse(null);
    }

    public Set<String> getListTenHocSinh(List<HocSinh> listHocSinh) {
        Set<String> listTenHocSinh = new HashSet<>();
        for (HocSinh hocSinh : listHocSinh) {
            listTenHocSinh.add(hocSinh.getTenHocSinh());
        }
        return listTenHocSinh;
    }

    public HashMap<Integer, HocSinh> getHocSinhIndex() {
        HashMap<Integer, HocSinh> hocSinhIndex = new HashMap<>();
        this.listHocSinh.forEach(hs -> hocSinhIndex.put(hs.getMaHocSinh(), hs));

        return hocSinhIndex;
    }

    public String getHocSinhNameById(Integer maHocSinh) {
        HashMap<Integer, HocSinh> hocSinhIndex = this.getHocSinhIndex();
        HocSinh hocSinh = hocSinhIndex.get(maHocSinh);
        if (hocSinh == null) {
            return null;
        }
        return hocSinh.getTenHocSinh();
    }

}
