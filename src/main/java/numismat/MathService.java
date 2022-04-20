package numismat;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathService {

    @GetMapping("/")
    public String index(){
        String message;
        JSONObject json = new JSONObject();
        json.put("title", "student");
        json.put("description", "sdfsdfsdfs");

//        JSONArray array = new JSONArray();
//        JSONObject item = new JSONObject();
//        item.put("information", "test");
//        item.put("id", 3);
//        item.put("name", "course1");
//        array.put(item);
//
//        json.put("course", array);
//
        message = json.toString();

        return message;

    }



//    @RequestMethod("GET")
//    @ResourcePath("sum")
//    public double getSum(double a, double b){
//        return a + b;
//    }
//
//    @RequestMethod("GET")
//    @ResourcePath("sum")
//    public double getSum(List<Double> values){
//        double total = 0;
//
//        for (double value : values) {
//            total += value;
//        }
//
//        return total;
//    }



}
