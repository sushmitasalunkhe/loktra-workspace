package BasePage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;



public class tojsonmap {

    public static void main(String args[]) {
        ObjectMapper mapper = new ObjectMapper();
 
        Map<String, Object> carMap = new HashMap<String, Object>();
 
        carMap.put("car", "Audi");
        carMap.put("price", "30000");
        carMap.put("model", "2010");
 
        List<String> colors = new ArrayList<String>();
        colors.add("Grey");
        colors.add("White");
        colors.add("Black");
 
        carMap.put("colors", colors);
 
        /**
         * Convert Map to JSON and write to a file
         */
        try {
            mapper.writeValue(new File("result.json"), carMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }

}
