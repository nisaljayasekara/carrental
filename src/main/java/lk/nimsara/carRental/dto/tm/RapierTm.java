package lk.nimsara.carRental.dto.tm;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/
public class RapierTm {
    private String Rapier_id;
    private String Rapier_date;
    private String Rapier_desc;
    private String Rapier_returnDate;
    private String Rapier_price;
    private String Car_id;
}


