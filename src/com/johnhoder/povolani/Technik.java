package com.johnhoder.povolani;

import com.johnhoder.Employee;
import com.johnhoder.JeSchopny;

/**
 * Created by John on 3/23/2016.
 */
public class Technik extends Employee implements JeSchopny{

    public Technik(String id, String prijmeni, String jmeno, Integer maxUvazek) {
        super(id, prijmeni, jmeno, maxUvazek);
    }

    public static Integer tarif = 200;

    @Override
    public boolean muzeDelatAdministrativu() {
        return true;
    }

    @Override
    public boolean muzeDelatDokumentaci() {
        return true;
    }

    @Override
    public boolean muzeDelatVyvoj() {
        return false;
    }
}
