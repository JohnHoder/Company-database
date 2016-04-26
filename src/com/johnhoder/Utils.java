package com.johnhoder;

import com.johnhoder.povolani.Asistent;
import com.johnhoder.povolani.Reditel;
import com.johnhoder.povolani.Technik;
import com.johnhoder.povolani.Vyvojar;

import java.util.*;

/**
 * Created by John on 3/25/2016.
 */
public class Utils {

    public static int zadatCislo(Scanner sc, int from, int to) {
        int cislo = 0;
        try {
            cislo = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Nastala vyjimka typu " + e.toString());
            System.out.printf("Zadejte prosim cele cislo v rozsahu od %s do %s.\n", from, to);
            sc.nextLine();
            cislo = zadatCislo(sc, from, to);
        }
        if (cislo < from || cislo > to) {
            System.out.printf("Zadejte prosim cele cislo v rozsahu od %s do %s.\n", from, to);
            sc.nextLine();
            cislo = zadatCislo(sc, from, to);
        } else {
            sc.nextLine();
        }
        return cislo;
    }

    public static Map<String, Integer> sortByJobType(Map<String, Integer> unsortedMap) {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortedMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());
                //return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static Map<String, Employee> sortByPrijmeni(Map<String, Employee> unsortedMap) {

        List<Map.Entry<String, Employee>> list = new LinkedList<Map.Entry<String, Employee>>(unsortedMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Employee>>() {
            @Override
            public int compare(Map.Entry<String, Employee> o1,
                               Map.Entry<String, Employee> o2) {

                return o1.getValue().getPrijmeni().compareToIgnoreCase(o2.getValue().getPrijmeni());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Employee> sortedMap = new LinkedHashMap<String, Employee>();
        for (Map.Entry<String, Employee> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static Map<String, Employee> sortByID(Map<String, Employee> unsortedMap) {

        List<Map.Entry<String, Employee>> list = new LinkedList<Map.Entry<String, Employee>>(unsortedMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Employee>>() {
            @Override
            public int compare(Map.Entry<String, Employee> o1,
                               Map.Entry<String, Employee> o2) {

                return o1.getKey().compareToIgnoreCase(o2.getKey());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Employee> sortedMap = new LinkedHashMap<String, Employee>();
        for (Map.Entry<String, Employee> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void rozdeleniPrace(TreeMap<String, Integer> prace, HashMap<String, Employee> employeesByKey, HashMap<String, Integer> employeesByType) {
        System.out.println("Soucasne efektivni rozdeleni prace mezi zamestnanci: ");
        TreeMap<String, Integer> tmpPrace = prace;

        Integer adm = tmpPrace.get("administrativa");
        Integer dok = tmpPrace.get("dokumentace");
        Integer vyv = tmpPrace.get("vyvoj");

        //Smaz predesle prirazene prace
        for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
            Employee value2 = entry.getValue();
            value2.setVyseUvazkuVAdministrative(0);
            value2.setVyseUvazkuVDokumentaci(0);
            value2.setVyseUvazkuVeVyvoji(0);
        }

        for (Map.Entry<String, Integer> prc : prace.descendingMap().entrySet()) {
            System.out.printf("Prave se rozdeluje %s hodin v %s\n", prc.getValue(), prc.getKey());

            if (prc.getKey().equals("vyvoj")) {
                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (vyv < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = vyv;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Vyvojar) {
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            System.out.printf("Tohle JE VYVOJAR, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                            vyv -= odecti;
                            value2.setVyseUvazkuVeVyvoji(odecti);
                        } else {
                            //preskoc tohoto vyvojare, zkus najit dalsiho vyvojare
                            continue;
                        }
                    }

                    if (vyv == 0) {
                        break;
                    }
                }
                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (vyv < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = vyv;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Reditel) {
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            System.out.printf("Tohle JE REDITEL, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                            vyv -= odecti;
                            value2.setVyseUvazkuVeVyvoji(odecti);
                        } else {
                            //reditel muze byt jen jeden
                            break;
                        }
                    }

                    if (vyv == 0) {
                        break;
                    }
                }

            } else if (prc.getKey().equals("dokumentace")) {
                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (dok < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = dok;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Technik) {
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            System.out.printf("Tohle JE TECHNIK, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                            dok -= odecti;
                            value2.setVyseUvazkuVDokumentaci(odecti);
                        } else {
                            //preskoc tohoto technika, zkus najit dalsiho technika
                            continue;
                        }
                    }

                    if (dok == 0) {
                        break;
                    }
                }

                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (dok < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = dok;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Vyvojar) {
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            System.out.printf("Tohle JE VYVOJAR, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                            dok -= odecti;
                            value2.setVyseUvazkuVDokumentaci(odecti);
                        } else {
                            //preskoc tohoto vyvojare, zkus najit dalsiho vyvojare
                            continue;
                        }
                    }

                    if (dok == 0) {
                        break;
                    }
                }
                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (dok < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = dok;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Reditel) {
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            System.out.printf("Tohle JE REDITEL, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                            dok -= odecti;
                            value2.setVyseUvazkuVDokumentaci(odecti);
                        } else {
                            //reditel muze byt jen jeden
                            break;
                        }
                    }

                    if (dok == 0) {
                        break;
                    }
                }
            } else if (prc.getKey().equals("administrativa")) {
                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (adm < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = adm;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Asistent) {
                        System.out.printf("Tohle JE ASISTENT, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            adm -= odecti;
                            value2.setVyseUvazkuVAdministrative(odecti);
                        } else {
                            //preskoc tohoto asistenta, zkus najit dalsiho asistenta
                            continue;
                        }
                    }

                    if (adm == 0) {
                        break;
                    }
                }

                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (adm < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = adm;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Technik) {
                        System.out.printf("Tohle JE TECHNIK, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            adm -= odecti;
                            value2.setVyseUvazkuVAdministrative(odecti);
                        } else {
                            //preskoc tohoto technika, zkus najit dalsiho technika
                            continue;
                        }
                    }

                    if (adm == 0) {
                        break;
                    }
                }
                for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                    String key2 = entry.getKey();
                    Employee value2 = entry.getValue();

                    if (value2.getIsNemocny() == true)
                        continue;

                    Integer odecti;
                    if (adm < value2.getZbyvajiciVolnyUvazek()) {
                        odecti = adm;
                    } else {
                        odecti = value2.getZbyvajiciVolnyUvazek();
                    }

                    if (value2 instanceof Reditel) {
                        System.out.printf("Tohle JE REDITEL, snazim se mu priradit %s hodin z %s\n", odecti, prc.getKey());
                        if (value2.getZbyvajiciVolnyUvazek() > 0) {
                            adm -= odecti;
                            value2.setVyseUvazkuVAdministrative(odecti);
                        } else {
                            //reditel muze byt jen jeden
                            break;
                        }
                    }
                    if (adm == 0) {
                        break;
                    }
                }
            }
        }
        System.out.printf("Nerozdelena zbyla prace:\nAdministrativa: %s/%s\nDokumentace: %s/%s\nVyvoj: %s/%s\n\n", adm, prace.get("administrativa"), dok, prace.get("dokumentace"), vyv, prace.get("vyvoj"));

        for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
            Employee value2 = entry.getValue();

            if (value2.getIsNemocny() == true)
                continue;
            if ((value2.getCelkovaVyseUvazku() == 0) && !(value2 instanceof Reditel)) {
                System.out.printf("Navrhuji zamestnance %s(%s) propustit.\n", value2, employeesByType.get(value2.getid()));
            }
        }

        if(vyv > 0){
            System.out.printf("Navrhuji najmout %sx %s pro %s.\n", (int)roundUp(vyv)/10, "VYVOJAR", "vyvoj");
        }
        if(dok > 0){
            System.out.printf("Navrhuji najmout %sx %s pro %s.\n", (int)roundUp(dok)/10, "TECHNIK", "dokumentaci");
        }
        if(adm > 0){
            System.out.printf("Navrhuji najmout %sx %s pro %s.\n", (int)roundUp(adm)/10, "ASISTENT", "administraci");
        }

        System.out.printf("\n");
    }

    public static double roundUp(double n) {
        return Math.round((n + 5)/10.0) * 10.0;
    }
}
