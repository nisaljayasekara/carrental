package lk.nimsara.carRental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AppoimentDto {
    private String id;
    private String date;
    private String time;
    private String Returndate;
    private String Customer_id;
    private String Appoiment_returnTime;
    private String Car_id;
    private String Car_name;
    private String Car_price;
    private String Payment;


}


