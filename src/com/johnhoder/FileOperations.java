package com.johnhoder;

import java.io.*;
import java.util.*;

/**
 * Created by John on 3/24/2016.
 */
public class FileOperations {

    static String fileName = "zamestnanci.dat";
    static String fileName2 = "prace.dat";
    static String fileName3 = "zamByType.dat";
    static String fileName4 = "maxUvazek.dat";

    public static void saveToFile(HashMap<String, Employee> map, TreeMap<String, Integer> prace, HashMap<String, Integer> zamByType, Integer maxUvazek) {

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
            //System.out.printf("Zamestnanci serializovani.\n");

            FileOutputStream fos2 = new FileOutputStream(fileName2);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(prace);
            oos2.close();
            fos2.close();
            //System.out.printf("Prace serializovana.\n");

            FileOutputStream fos3 = new FileOutputStream(fileName3);
            ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
            oos3.writeObject(zamByType);
            oos3.close();
            fos3.close();
            //System.out.printf("ZamByType serializovano.\n");

            FileOutputStream fos4 = new FileOutputStream(fileName4);
            ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
            oos4.writeObject(maxUvazek);
            oos4.close();
            fos4.close();
            //System.out.printf("MaxUvazek serializovan.\n");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static SerializedObjectsAbstraction loadFromFile(HashMap<String, Employee> map, TreeMap<String, Integer> prace, HashMap<String, Integer> zamByType, Integer maxUvazek) {

        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap) ois.readObject();
            ois.close();
            fis.close();

            FileInputStream fis2 = new FileInputStream(fileName2);
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            prace = (TreeMap) ois2.readObject();
            ois2.close();
            fis2.close();

            FileInputStream fis3 = new FileInputStream(fileName3);
            ObjectInputStream ois3 = new ObjectInputStream(fis3);
            zamByType = (HashMap) ois3.readObject();
            ois3.close();
            fis3.close();

            FileInputStream fis4 = new FileInputStream(fileName4);
            ObjectInputStream ois4 = new ObjectInputStream(fis4);
            maxUvazek = (Integer) ois4.readObject();
            ois4.close();
            fis4.close();
        }catch (FileNotFoundException e){
            System.out.println("Nebyl nalezen soubor s ulozenymi daty.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }

        for (Map.Entry<String, Employee> m : map.entrySet()) {
            System.out.println(m.getKey() + " : " + m.getValue());
        }

        for (Map.Entry<String, Integer> prc : prace.entrySet()) {
            System.out.println(prc.getKey() + " : " + prc.getValue());
        }

        System.out.println("Maximalni uvzek: " + maxUvazek);

        SerializedObjectsAbstraction abstraction = new SerializedObjectsAbstraction(map, prace, zamByType, maxUvazek);
        return abstraction;
    }

    public static class SerializedObjectsAbstraction {
        public final HashMap<String, Employee> map;
        public final TreeMap<String, Integer> prace;
        public final HashMap<String, Integer> zamByType;
        public final Integer maxUvazek;

        public SerializedObjectsAbstraction(HashMap<String, Employee> map, TreeMap<String, Integer> prace, HashMap<String, Integer> zamByType, Integer maxUvazek) {
            this.map = map;
            this.prace = prace;
            this.zamByType = zamByType;
            this.maxUvazek = maxUvazek;
        }

        public HashMap<String, Employee> getEmployeeMap() {
            return map;
        }

        public TreeMap<String, Integer> getPraceMap() {
            return prace;
        }

        public HashMap<String, Integer> getZamByTypeMap() {
            return zamByType;
        }

        public Integer getMaxUvazek() {
            return maxUvazek;
        }
    }
}
