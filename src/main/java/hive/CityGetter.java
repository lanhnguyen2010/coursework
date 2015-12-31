package hive;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by lanhnguyen on 12/31/2015.
 */
public class CityGetter extends UDF {
    public Text evaluate(Text input) throws Exception {
        int cityNumber;
        String districtID = input.toString().substring(2, 3);
        try {
            cityNumber = Integer.parseInt(input.toString().substring(0, 2));
        } catch (Exception e){
            throw new Exception(input + "is not a Car ID");
        }
        String city;
        String district;
        switch (cityNumber/10){
            case 2 : city = "Ha Noi";
                break;
            case 5 : city = "Ho Chi Minh";
                break;
            case 7 : city = "Khanh Hoa";
                break;
            default: city = "New City";
                break;
        }
        switch (districtID){
            case "A" : district = "Quan 1";
                break;
            case "B" : district = "Quan 2";
                break;
            case "C" : district = "Quan 3";
                break;
            case "D" : district = "Quan 4";
                break;
            case "E" : district = "Quan 5";
                break;
            case "F" : district = "Quan 6";
                break;
            case "G" : district = "Quan 7";
                break;
            case "H" : district = "Quan 8";
                break;
            default: district = "Quan 10";
                break;

        }
        return new Text(city + "_" + district);
    }
}
