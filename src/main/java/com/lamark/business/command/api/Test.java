package com.lamark.business.command.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Test {

    public static void main(String[] args) {
        String line = "15.94;16.00";


        String[] tx = line.split(";");
        Double PP = Double.valueOf(tx[0]);
        Double CH = Double.valueOf(tx[1]);

        if(PP==CH)
            System.out.println("ZERO");
        else if(PP>CH)
            System.out.println("ERROR");
        else
            System.out.println(getChange(PP,CH));

    }

    private static String getChange(Double pp, Double ch) {
        LinkedHashMap<String,Double> coins =  new LinkedHashMap<>();
        coins.put("PENNY",0.01);
        coins.put("NICKEL",0.05);
        coins.put("DIME",0.10);
        coins.put("QUARTER",0.25);
        coins.put("HALF DOLLAR",0.50);
        coins.put("ONE",1.00);
        coins.put("TWO",2.0);
        coins.put("FIVE",5.0);
        coins.put("TEN",10.0);
        coins.put("TWENTY",20.0);
        coins.put("FIFTY",50.0);
        coins.put("ONE HUNDRED",100.0);

        AtomicReference<String> resultStr = new AtomicReference<>("");
        Double change =new BigDecimal(ch-pp).setScale(2, RoundingMode.HALF_UP).doubleValue();

        AtomicReference<Double> sumD= new AtomicReference<>(0.0);
        AtomicReference<Double> changeAtomic= new AtomicReference<>(change);
        coins.forEach((k,d) -> {
            sumD.updateAndGet(v -> v + d);
            double v = new BigDecimal(sumD.get()).setScale(2, RoundingMode.HALF_UP).doubleValue();

            resultStr.set(resultStr + k + ",");
            if(v == change)
                System.out.println(resultStr.get().substring(0, resultStr.get().length()-1));
        });


        return "";
    }
}
