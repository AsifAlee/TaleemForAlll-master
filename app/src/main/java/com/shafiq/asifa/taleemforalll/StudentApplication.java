package com.shafiq.asifa.taleemforalll;

/**
 * Created by asifa on 6/26/2019.
 */

public class StudentApplication {


    String id;
    String name;
    String  fname;
    String cnic;
    String phone;
    String email;
    String domicile;
    String   applyText;

    public StudentApplication(String id, String name, String fname, String cnic, String phone, String email, String domicile, String applyText) {
        this.id = id;
        this.name = name;
        this.fname = fname;
        this.cnic = cnic;
        this.phone = phone;
        this.email = email;
        this.domicile = domicile;
        this.applyText = applyText;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFname() {
        return fname;
    }

    public String getCnic() {
        return cnic;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDomicile() {
        return domicile;
    }

    public String getApplyText() {
        return applyText;
    }

    public  StudentApplication(){

    }
}
