package com.kubesocial.modules.study;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Calculator {

    public double calculate(double numberA, double numberB, String method) {
        if (numberB <= 0 && method.equals("/")) {
            throw new ArithmeticException("canot split with 0");
        }

        switch (method) {
            case "+":
                return numberA + numberB;
            case "-":
                return numberA - numberB;
            case "*":
                return numberA * numberB;
            case "/":
                return numberA / numberB;
            default:
                return 0;
        }
    }

    public List<Integer> displayUoc(int number) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                result.add(i);
            }
        }

        return result;
    }

    public int getCount(List<Integer> list) {

        return list.size();
    }

    public String getReverseString(String text) {
        List<Character> reverseTextList =  new ArrayList<>();
        for (int i = text.length() -1; i >= 0; i--) {
            reverseTextList.add(text.charAt(i));
        }
        StringBuilder convert = new StringBuilder();
        for (char ch: reverseTextList) {
            convert.append(ch);
        }

        return convert.toString();
    }


    public static void main(String[] args) {
        Sach sach1 = new Sach("S1", "tuoi tre", 5, "lamvb", 100);
        Sach sach2 = new Sach("S2", "tuoi tre", 6, "lamvb", 200);
        QuanLyThuVienService quanli = new QuanLyThuVienService();
        quanli.addTaiLieu(sach1);
        quanli.addTaiLieu(sach2);
        List<TaiLieu> listTaiLieu = quanli.getTaiLieuByType(Sach.class);
        // listTaiLieu.forEach(sach -> System.out.println("danh sach cac tai lieu sach: " + sach.getMaTaiLieu()));
        // Order order1 = new Order(1, "Lamvb", new PaypalPayment());
        // order1.processOrder(100.0);
        HocSinh hocSinh1 = new HocSinh(1, "Lamvb", 9.0);
        HocSinh hocSinh2 = new HocSinh(2, "Son", 9.0);
        HocSinh hocSinh3 = new HocSinh(3, "Hieu", 8.0);
        HocSinh hocSinh4 = new HocSinh(4, "Hieu", 10.0);
        QuanLiHocSinh quanliHocSinh = new QuanLiHocSinh();
        quanliHocSinh.addHocSinh(hocSinh1);
        quanliHocSinh.addHocSinh(hocSinh2);
        quanliHocSinh.addHocSinh(hocSinh3);
        quanliHocSinh.addHocSinh(hocSinh4);
        System.out.println("ten hoc sinh co mhs = 1: " + quanliHocSinh.getHocSinhNameById(2));
        // quanliHocSinh.getListTenHocSinh(quanliHocSinh.getListHocSinh()).forEach(hs -> System.out.println("ten hs: " + hs));

    }

}
