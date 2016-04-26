package com.johnhoder;

import java.io.Serializable;

/**
 * Created by John on 3/23/2016.
 */
public class Employee implements Comparable<Employee>, Serializable {
    private String id;
    private String prijmeni;
    private String jmeno;

    private Integer vyseMaxUvazku;
    private Integer zbyvajiciVolnyUvazek;

    private Integer vyseUvazkuVAdministrative = 0;
    private Integer vyseUvazkuVeVyvoji = 0;
    private Integer vyseUvazkuVDokumentaci = 0;

    private boolean isNemocny = false;

    public Employee(String id, String prijmeni, String jmeno, Integer vyseMaxUvazku) {
        this.id = id;
        this.prijmeni = prijmeni;
        this.jmeno = jmeno;
        this.vyseMaxUvazku = vyseMaxUvazku;
    }

    @Override
    public int compareTo(Employee emp) {
        int compLasts = prijmeni.compareTo(emp.prijmeni);
        int compFirsts = jmeno.compareTo(emp.jmeno);
        int compid = id.compareTo(emp.id);

        if ((compid != 0) && (compLasts != 0) && (compFirsts != 0)) {
            return compLasts;
        } else {
            return compLasts;
            //throw new IllegalArgumentException("Empolyee already exists with that id.");
        }
    }


    public Integer getCelkovaVyseUvazku() {
        return vyseUvazkuVAdministrative + vyseUvazkuVDokumentaci + vyseUvazkuVeVyvoji;
    }

    public Integer getVyseUvazkuVeVyvoji() {
        return vyseUvazkuVeVyvoji;
    }

    public void setVyseUvazkuVeVyvoji(Integer vyseUvazkuVeVyvoji) {
        this.vyseUvazkuVeVyvoji = vyseUvazkuVeVyvoji;
    }

    public Integer getVyseUvazkuVDokumentaci() {
        return vyseUvazkuVDokumentaci;
    }

    public void setVyseUvazkuVDokumentaci(Integer vyseUvazkuVDokumentaci) {
        this.vyseUvazkuVDokumentaci = vyseUvazkuVDokumentaci;
    }

    public Integer getVyseUvazkuVAdministrative() {
        return vyseUvazkuVAdministrative;
    }

    public void setVyseUvazkuVAdministrative(Integer vyseUvazkuVAdministrative) {
        this.vyseUvazkuVAdministrative = vyseUvazkuVAdministrative;
    }

    public Integer getZbyvajiciVolnyUvazek() {
        //System.out.printf("%s; %s - %s\n", this.getid(), this.getVyseMaxUvazku(), this.getCelkovaVyseUvazku());
        return this.getVyseMaxUvazku() - this.getCelkovaVyseUvazku();
    }

    public Integer getVyseMaxUvazku() {
        return vyseMaxUvazku;
    }

    public void setVyseMaxUvazku(Integer vyseMaxUvazku) {
        this.vyseMaxUvazku = vyseMaxUvazku;
    }

    public String getPrijmeni() {
        return this.prijmeni;
    }

    public String getJmeno() {
        return this.jmeno;
    }

    public String getid() {
        return this.id;
    }

    public boolean getIsNemocny() {
        return this.isNemocny;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public void setid(String setid) {
        this.id = setid;
    }

    public void setIsNemocny(boolean nemocny) {
        this.isNemocny = nemocny;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Employee) {

            Employee employee = (Employee) o;

            if (id == employee.id) return true;
            else return false;
        }
        else return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("{id: ").append(id);
        sb.append(", jmeno: ").append(jmeno);
        sb.append(", prijmeni: ").append(prijmeni);
        sb.append("}");
        return sb.toString();
    }
}
