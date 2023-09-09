package sldevs.cdo.orokalimpyo.functions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class other_functions {

    List<String> establishments = new ArrayList<String>();
    List<String> user_types = new ArrayList<String>();

    List<String> barangay = new ArrayList<String>();


    public List<String> populateEstablishmentList(){
        establishments.add("School");
        establishments.add("Mall");
        establishments.add("Bakery");
        establishments.add("Hotel");
        establishments.add("Restaurant");
        establishments.add("Food Chain");
        establishments.add("Pharmacy");
        establishments.add("Clinic");
        establishments.add("Hospital");
        Collections.sort(establishments);
        for(String str: establishments);
        establishments.add("Others...");
        return establishments;
    }


    public List<String> populateUserTypeCollector(){
        user_types.add("Barangay Collector");
        user_types.add("City Collector");
        user_types.add("Private Collector");

        return user_types;
    }

    public List<String> populateUserTypeConsolidator(){
        user_types.add("Barangay MRF");
        user_types.add("MRF Cooperative");
        user_types.add("Junk Shop");

        return user_types;
    }


    public List<String> populateUserTypeBarangay(){
        barangay.add("Agusan");
        barangay.add("Baikingon");
        barangay.add("Balubal");
        barangay.add("Balulang");
        barangay.add("Barangay 1");
        barangay.add("Barangay 10");
        barangay.add("Barangay 11");
        barangay.add("Barangay 12");
        barangay.add("Barangay 13");
        barangay.add("Barangay 14");
        barangay.add("Barangay 15");
        barangay.add("Barangay 16");
        barangay.add("Barangay 17");
        barangay.add("Barangay 18");
        barangay.add("Barangay 19");
        barangay.add("Barangay 2");
        barangay.add("Barangay 20");
        barangay.add("Barangay 21");
        barangay.add("Barangay 22");
        barangay.add("Barangay 23");
        barangay.add("Barangay 24");
        barangay.add("Barangay 25");
        barangay.add("Barangay 26");
        barangay.add("Barangay 27");
        barangay.add("Barangay 28");
        barangay.add("Barangay 29");
        barangay.add("Barangay 3");
        barangay.add("Barangay 30");
        barangay.add("Barangay 31");
        barangay.add("Barangay 32");
        barangay.add("Barangay 33");
        barangay.add("Barangay 34");
        barangay.add("Barangay 35");
        barangay.add("Barangay 36");
        barangay.add("Barangay 37");
        barangay.add("Barangay 38");
        barangay.add("Barangay 39");
        barangay.add("Barangay 4");
        barangay.add("Barangay 40");
        barangay.add("Barangay 5");
        barangay.add("Barangay 6");
        barangay.add("Barangay 7");
        barangay.add("Barangay 8");
        barangay.add("Barangay 9");
        barangay.add("Bayabas");
        barangay.add("Bayanga");
        barangay.add("Besigan");
        barangay.add("Bonbon");
        barangay.add("Bugo");
        barangay.add("Bulua");
        barangay.add("Camaman-an");
        barangay.add("Canito-an");
        barangay.add("Carmen");
        barangay.add("Consolacion");
        barangay.add("Cugman");
        barangay.add("Dansolihon");
        barangay.add("F. S. Catanico");
        barangay.add("Gusa");
        barangay.add("Indahag");
        barangay.add("Iponan");
        barangay.add("Kauswagan");
        barangay.add("Lapasan");
        barangay.add("Lumbia");
        barangay.add("Macabalan");
        barangay.add("Macasandig");
        barangay.add("Mambuaya");
        barangay.add("Nazareth");
        barangay.add("Pagalungan");
        barangay.add("Pagatpat");
        barangay.add("Patag");
        barangay.add("Pigsag-an");
        barangay.add("Puerto");
        barangay.add("Puntod");
        barangay.add("San Simon");
        barangay.add("Tablon");
        barangay.add("Taglimao");
        barangay.add("Tagpangi");
        barangay.add("Tignapoloan");
        barangay.add("Tuburan");
        barangay.add("Tumpagon");
//        Collections.sort(establishments);
//        for(String str: establishments);
//        establishments.add("Others...");
        return barangay;
    }


}
