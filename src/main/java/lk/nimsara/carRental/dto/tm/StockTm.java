package lk.nimsara.carRental.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockTm {
    private String stockId;
    private String description;
    private int qty_on_hand;
}

