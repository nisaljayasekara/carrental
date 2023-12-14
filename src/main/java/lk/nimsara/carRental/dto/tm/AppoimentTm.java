package lk.nimsara.carRental.dto.tm;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/
public class AppoimentTm {
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


