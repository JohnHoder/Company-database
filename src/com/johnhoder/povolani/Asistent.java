package com.johnhoder.povolani;

import com.johnhoder.Employee;
import com.johnhoder.JeSchopny;

/**
 * Created by John on 3/23/2016.
 */
public class Asistent extends Employee implements JeSchopny {

    public Asistent(String id, String prijmeni, String jmeno, Integer maxUvazek) {
        super(id, prijmeni, jmeno, maxUvazek);
    }

    public static Integer getTarif() {
        return tarif;
    }

    public static Integer tarif = 150;

    @Override
    public boolean muzeDelatAdministrativu() {
        return true;
    }

    @Override
    public boolean muzeDelatDokumentaci() {
        return false;
    }

    @Override
    public boolean muzeDelatVyvoj() {
        return false;
    }
}
