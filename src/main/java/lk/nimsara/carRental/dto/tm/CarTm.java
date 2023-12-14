package lk.nimsara.carRental.dto.tm;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/
public class CarTm {
    private String id;
    private String name;
    private String availability;
    private String price;
}
