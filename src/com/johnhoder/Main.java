package com.johnhoder;

import com.johnhoder.povolani.*;

import java.util.*;

import static com.johnhoder.FileOperations.*;

public class Main {

    public static int menu() {
        System.out.println("Vyberte moznost:\n");
        System.out.println("1) Pridat noveho zamestnance.");
        System.out.println("2) Zadavani prace.");
        System.out.println("3) Ruseni prace.");
        System.out.println("4) Propuštění zaměstnance.");
        System.out.println("5) Onemocnění zaměstnance.");
        System.out.println("6) Uzdraveni zaměstnance.");
        System.out.println("7) Nastavení maximálního možného úvazku.");
        System.out.println("8) Výpis počtu zaměstnanců na jednotlivých pozicích a jejich aktuálně volné úvazky.");
        System.out.println("9) Výpis celkových finančních prostředků aktuálně vynakládaných za jeden měsíc.");
        System.out.println("10) Výpis všech zaměstnanců");
        System.out.println("11) Uložení celé databáze do souboru.");
        System.out.println("12) Načtení celé databáze ze souboru");
        System.out.println("13) Konec.");

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }


    public static void main(String[] args) {
        HashMap<String, Employee> employeesByKey = new HashMap<String, Employee>(); //ID,Employee
        HashMap<String, Integer> employeesByType = new HashMap<String, Integer>(); //ID,Type

        //HashMap<String, Integer> prace = new HashMap<>();
        TreeMap<String, Integer> prace = new TreeMap<String, Integer>() {
            {
                put("administrativa", 0);
                put("dokumentace", 0);
                put("vyvoj", 0);
            }
        };

        HashMap<Integer, String> povolani = new HashMap<Integer, String>() {
            {
                put(1, "ASISTENT");
                put(2, "TECHNIK");
                put(3, "VYVOJAR");
                put(4, "REDITEL");
            }
        };

        boolean reditelUzJe = employeesByType.containsValue(4);

        Integer maximalniUvazek = 10;

        //MENU
        int menuStore = menu();
        while (menuStore != 13) {
            System.out.println();
            switch (menuStore) {
                //PRIDANI ZAMESTNANCE
                case 1:
                    Scanner input1 = new Scanner(System.in);

                    System.out.println("Zadej typ povolani: ");
                    System.out.printf("\t1) ASISTENT\n\t2) TECHNIK\n\t3) VYVOJAR\n\t4) REDITEL\n");
                    Integer typPovolani = Utils.zadatCislo(input1, 1, 4);
                    if (typPovolani == 4 && reditelUzJe == true) {
                        System.out.printf("Reditel muze byt jen jeden!");
                        break;
                    }

                    System.out.println("Zadej ID: ");
                    String id = input1.nextLine();
                    System.out.println("Zadej prijmeni: ");
                    String prijmeni = input1.nextLine();
                    System.out.println("Zadej jmeno: ");
                    String jmeno = input1.nextLine();

                    //Employee novyZamestnanec = new Employee(id, prijmeni, jmeno);
                    Employee novyZamestnanec = null;
                    switch (typPovolani) {
                        case 1:
                            Asistent asistent = new Asistent(id, prijmeni, jmeno, maximalniUvazek);
                            novyZamestnanec = asistent;
                            //System.out.printf("%s", (novyZamestnanec instanceof Technik));
                            break;
                        case 2:
                            Technik technik = new Technik(id, prijmeni, jmeno, maximalniUvazek);
                            novyZamestnanec = technik;
                            break;
                        case 3:
                            Vyvojar vyvojar = new Vyvojar(id, prijmeni, jmeno, maximalniUvazek);
                            novyZamestnanec = vyvojar;
                            break;
                        case 4:
                            Reditel reditel = new Reditel(id, prijmeni, jmeno, maximalniUvazek);
                            novyZamestnanec = reditel;
                            reditelUzJe = true;
                            break;
                    }

                    employeesByKey.put(id, novyZamestnanec);
                    employeesByType.put(id, typPovolani);

                    System.out.println();
                    //System.out.printf("%s\n", novyZamestnanec.getZbyvajiciVolnyUvazek());
                    break;

                //Zadavani prace
                case 2:
                    System.out.println("Zadejte typ prace, jejiz hodiny chcete nastavit: ");
                    System.out.printf("\t" +
                            "1) Administrativa (" + prace.get("administrativa") + ")\n\t" +
                            "2) Dokumentace (" + prace.get("dokumentace") + ")\n\t" + "" +
                            "3) Vyvoj (" + prace.get("vyvoj") + ")\n");
                    Scanner input2 = new Scanner(System.in);
                    Integer typPrace = Utils.zadatCislo(input2, 1, 3);
                    System.out.println("Zadejte pocet hodin: ");
                    Integer pocetHodin = Utils.zadatCislo(input2, 0, Integer.MAX_VALUE);

                    switch (typPrace) {
                        case 1:
                            prace.put("administrativa", pocetHodin);
                            System.out.printf("Pocet hodin v administrative byl nastaven na %s\n", pocetHodin);
                            break;
                        case 2:
                            prace.put("dokumentace", pocetHodin);
                            System.out.printf("Pocet hodin v dokumentaci byl nastaven na %s\n", pocetHodin);
                            break;
                        case 3:
                            prace.put("vyvoj", pocetHodin);
                            System.out.printf("Pocet hodin ve vyvoji byl nastaven na %s\n", pocetHodin);
                            break;
                    }
                    /////////////////////////////////
                    Utils.rozdeleniPrace(prace, employeesByKey, employeesByType);
                    break;

                //Ruseni prace
                case 3:
                    System.out.println("Zadejte typ prace, jejiz hodiny chcete snizit: ");
                    System.out.printf("\t" +
                            "1) Administrativa (" + prace.get("administrativa") + ")\n\t" +
                            "2) Dokumentace (" + prace.get("dokumentace") + ")\n\t" + "" +
                            "3) Vyvoj (" + prace.get("vyvoj") + ")\n");
                    Scanner input3 = new Scanner(System.in);
                    Integer typPrace2 = Utils.zadatCislo(input3, 1, 3);
                    System.out.println("Zadejte pocet hodin, o kolik chcete danou praci snizit: ");
                    Integer pocetHodin2 = Utils.zadatCislo(input3, 0, Integer.MAX_VALUE);

                    switch (typPrace2) {
                        case 1:
                            if((prace.get("administrativa") - pocetHodin2) < 0){
                                prace.put("administrativa", 0);
                            }else {
                                prace.put("administrativa", prace.get("administrativa") - pocetHodin2);
                            }
                            System.out.printf("Pocet hodin v administrative byl snizen na %s\n", prace.get("administrativa"));
                            //System.out.printf("%s\n", prace.get("administrativa"));
                            break;
                        case 2:
                            prace.put("dokumentace", prace.get("dokumentace") - pocetHodin2);
                            System.out.printf("Pocet hodin v dokumentaci byl snizen na %s\n", prace.get("dokumentace"));
                            break;
                        case 3:
                            prace.put("vyvoj", prace.get("vyvoj") - pocetHodin2);
                            System.out.printf("Pocet hodin ve vyvoji byl snizen na %s\n", prace.get("vyvoj"));
                            break;
                    }
                    break;

                //PROPUSTENI ZAMESTNANCE
                case 4:
                    Scanner input4 = new Scanner(System.in);

                    System.out.println("Zadejte ID zamestnance, ktereho chcete propustit: ");
                    String delStr = input4.nextLine();

                    if (employeesByKey.containsKey(delStr)) {

                        Integer praceVyvoj = employeesByKey.get(delStr).getVyseUvazkuVeVyvoji();
                        Integer praceAdministrativa = employeesByKey.get(delStr).getVyseUvazkuVAdministrative();
                        Integer praceDokumentace = employeesByKey.get(delStr).getVyseUvazkuVDokumentaci();

                        if (employeesByType.get(delStr) == 4) {
                            reditelUzJe = false;
                        }

                        //REMOVE FROM employeesByType
                        System.out.printf("%s byl propusten.\n", employeesByKey.get(delStr));
                        employeesByType.remove(delStr);

                        //REMOVE FROM employeesByKey
                        employeesByKey.remove(delStr);

                        System.out.printf("Zamestnanec byl propusten.\n\n");

                        ////////
                        Utils.rozdeleniPrace(prace, employeesByKey, employeesByType);
                    } else {
                        System.out.printf("Zadane ID neexistuje.\n\n");
                    }
                    break;

                //Onemocneni zamestnance
                case 5:
                    Scanner input5 = new Scanner(System.in);

                    System.out.println("Zadejte ID zamestnance, ktery onemocnel: ");
                    String idNemoc = input5.nextLine();

                    if (employeesByKey.containsKey(idNemoc)) {
                        employeesByKey.get(idNemoc).setIsNemocny(true);
                        System.out.printf("Zamestnanec %s je nemocen.\n\n", idNemoc);

                        employeesByKey.get(idNemoc).setVyseUvazkuVeVyvoji(0);
                        employeesByKey.get(idNemoc).setVyseUvazkuVAdministrative(0);
                        employeesByKey.get(idNemoc).setVyseUvazkuVDokumentaci(0);

                        Utils.rozdeleniPrace(prace, employeesByKey, employeesByType);
                    } else {
                        System.out.printf("Zadane ID neexistuje.\n\n");
                    }
                    break;

                //Uzdraveni zamestnance
                case 6:
                    Scanner input6 = new Scanner(System.in);

                    System.out.println("Zadejte ID zamestnance, ktery se uzdravil: ");
                    String idNeniNemoc = input6.nextLine();

                    if (employeesByKey.containsKey(idNeniNemoc)) {
                        if (employeesByKey.get(idNeniNemoc).getIsNemocny() == true) {
                            employeesByKey.get(idNeniNemoc).setIsNemocny(false);
                        } else {
                            System.out.printf("Zamestnanec %s je porad zdravy.\n\n", idNeniNemoc);
                            break;
                        }
                        System.out.printf("Zamestnanec %s se uzdravil.\n\n", idNeniNemoc);
                        Utils.rozdeleniPrace(prace, employeesByKey, employeesByType);
                    } else {
                        System.out.printf("Zadane ID neexistuje.\n\n");
                    }
                    break;

                //Nastavit vysi maximalniho mozneho uvazku
                case 7:
                    Scanner input7 = new Scanner(System.in);

                    System.out.println("Zadejte vysi maximalniho mozneho uvazku pro vsechny zamestnance (v hodinach/mesic): ");
                    Integer maxUzavek = input7.nextInt();
                    maximalniUvazek = maxUzavek;

                    for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                        //String key = entry.getKey();
                        Employee emp = entry.getValue();
                        emp.setVyseMaxUvazku(maxUzavek);
                    }

                    Utils.rozdeleniPrace(prace, employeesByKey, employeesByType);
                    break;

                //Výpis počtu zaměstnanců na jednotlivých pozicích a jejich aktuálně volné úvazky
                case 8:
                    Integer pocetAsist = 0;
                    Integer uvazekAsist = 0;
                    Integer pocetTech = 0;
                    Integer uvazekTech = 0;
                    Integer pocetVyv = 0;
                    Integer uvazekVyv = 0;
                    Integer pocetRedit = 0;
                    Integer uvazekRedit = 0;
                    for (Map.Entry<String, Integer> type : employeesByType.entrySet()) {
                        if (type.getValue() == 1) {
                            pocetAsist++;
                            uvazekAsist += employeesByKey.get(type.getKey()).getZbyvajiciVolnyUvazek();
                        } else if (type.getValue() == 2) {
                            pocetTech++;
                            uvazekTech += employeesByKey.get(type.getKey()).getZbyvajiciVolnyUvazek();
                        } else if (type.getValue() == 3) {
                            pocetVyv++;
                            uvazekVyv += employeesByKey.get(type.getKey()).getZbyvajiciVolnyUvazek();
                        } else if (type.getValue() == 4) {
                            pocetRedit++;
                            uvazekRedit += employeesByKey.get(type.getKey()).getZbyvajiciVolnyUvazek();
                        }
                    }

                    System.out.printf("Počet zaměstnanců na pozici Asistent: %s, vyse volneho uvazku: %s\n", pocetAsist, uvazekAsist);
                    System.out.printf("Počet zaměstnanců na pozici Technik: %s, vyse volneho uvazku: %s\n", pocetTech, uvazekTech);
                    System.out.printf("Počet zaměstnanců na pozici Vyvojar: %s, vyse volneho uvazku: %s\n", pocetVyv, uvazekVyv);
                    System.out.printf("Počet zaměstnanců na pozici Reditel: %s, vyse volneho uvazku: %s\n", pocetRedit, uvazekRedit);
                    System.out.printf("\n");
                    break;

                //Vypis vsech vynakladanych prostredku
                case 9:
                    Integer celkoveNaklady = 0;
                    for (Map.Entry<String, Employee> entry : employeesByKey.entrySet()) {
                        //String key = entry.getKey();
                        Employee value = entry.getValue();
                        if(value.getZbyvajiciVolnyUvazek() == maximalniUvazek){
                            celkoveNaklady += 500;
                            continue;
                        }
                        if (value instanceof Asistent) {
                            celkoveNaklady += value.getCelkovaVyseUvazku() * 150;
                        } else if (value instanceof Technik) {
                            celkoveNaklady += value.getCelkovaVyseUvazku() * 200;
                        } else if (value instanceof Vyvojar) {
                            celkoveNaklady += value.getCelkovaVyseUvazku() * 250;
                        } else if (value instanceof Reditel) {
                            celkoveNaklady += value.getCelkovaVyseUvazku() * 350;
                        }
                    }

                    System.out.printf("Vypis vsech vynakladanych prostredku je: %s,- Kč.\n", celkoveNaklady);
                    break;

                //Vypis vsech zamestnancu + razeni podle ID nebo Prijmeni
                case 10:
                    Scanner sc = new Scanner(System.in);
                    System.out.printf("Zadejte kriterium, podle ktereho se bude radit:\n" +
                            "1) Podle prac. pozice\n" +
                            "2) Podle ID\n" +
                            "3) Podle prijmeni\n");
                    Integer razeni = Utils.zadatCislo(sc, 1, 3);

                    switch (razeni) {
                        case 1:
                            HashMap<String, Integer> temp1 = employeesByType;
                            Map<String, Integer> sortedMap = Utils.sortByJobType(temp1);
                            System.out.printf("Povolani\tID\tJmeno\tPrijmeni\n");

                            for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                                String key = entry.getKey();
                                Integer value = entry.getValue();

                                String pov = povolani.get(value);
                                String jm = employeesByKey.get(key).getJmeno();
                                String pr = employeesByKey.get(key).getPrijmeni();

                                System.out.printf(pov + "\t" + key + "\t" + jm + "\t" + pr + "\t" + "\n");
                            }
                            break;
                        case 2:
                            HashMap<String, Employee> temp2 = employeesByKey;
                            Map<String, Employee> sortedMap2 = Utils.sortByID(temp2);
                            System.out.printf("Povolani\tID\tJmeno\tPrijmeni\n");

                            for (Map.Entry<String, Employee> entry : sortedMap2.entrySet()) {
                                String key = entry.getKey();
                                Employee value = entry.getValue();

                                String pov = povolani.get(employeesByType.get(value.getid()));
                                String jm = value.getJmeno();
                                String pr = value.getPrijmeni();

                                System.out.printf(pov + "\t" + key + "\t" + jm + "\t" + pr + "\t" + "\n");
                            }
                            break;
                        case 3:
                            HashMap<String, Employee> temp3 = employeesByKey;
                            Map<String, Employee> sortedMap3 = Utils.sortByPrijmeni(temp3);
                            System.out.printf("Povolani\tID\tJmeno\tPrijmeni\n");

                            for (Map.Entry<String, Employee> entry : sortedMap3.entrySet()) {
                                String key = entry.getKey();
                                Employee value = entry.getValue();

                                String pov = povolani.get(employeesByType.get(value.getid()));
                                String jm = value.getJmeno();
                                String pr = value.getPrijmeni();

                                System.out.printf(pov + "\t" + key + "\t" + jm + "\t" + pr + "\t" + "\n");
                            }

                            break;
                        default:
                            break;
                    }

                    System.out.println("");
                    break;

                //Ulozeni do souboru
                case 11:
                    saveToFile(employeesByKey, prace, employeesByType, maximalniUvazek);
                    break;

                //Nacteni ze souboru
                case 12:
                    SerializedObjectsAbstraction abstraction = loadFromFile(employeesByKey, prace, employeesByType, maximalniUvazek);
                    employeesByKey = abstraction.getEmployeeMap();
                    prace = abstraction.getPraceMap();
                    employeesByType = abstraction.getZamByTypeMap();
                    maximalniUvazek = abstraction.getMaxUvazek();

                    reditelUzJe = employeesByType.containsValue(4);
                    break;

                //KONEC PROGRAMU
                case 13:
                    break;
            }

            menuStore = menu();
        }
    }
}

