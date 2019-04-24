package com.ict.delivirko.Activities;

import com.ict.delivirko.Module.pilot.CanceledOrder;
import com.ict.delivirko.Module.pilot.CanceledOrdersDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableCanceledOrdersData {
    public static HashMap<CanceledOrder, List<CanceledOrdersDetails>> getData() {
        HashMap<CanceledOrder, List<CanceledOrdersDetails>> expandableListDetail = new HashMap<>();

        List<CanceledOrdersDetails> cricket = new ArrayList<>();
        cricket.add(new CanceledOrdersDetails());
        cricket.add(new CanceledOrdersDetails());
        cricket.add(new CanceledOrdersDetails());
        cricket.add(new CanceledOrdersDetails());

        List<CanceledOrdersDetails> football = new ArrayList<>();
        football.add(new CanceledOrdersDetails());
        football.add(new CanceledOrdersDetails());
        football.add(new CanceledOrdersDetails());
        football.add(new CanceledOrdersDetails());


        List<CanceledOrdersDetails> basketball = new ArrayList<>();
        basketball.add(new CanceledOrdersDetails());
        basketball.add(new CanceledOrdersDetails());


        expandableListDetail.put(new CanceledOrder(), cricket);
        expandableListDetail.put(new CanceledOrder(), football);
        expandableListDetail.put(new CanceledOrder(), basketball);
        return expandableListDetail;
    }
}
