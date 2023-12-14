package lk.nimsara.carRental.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceTm {
    private String attendanceId;
    private LocalDate attendanceDate;
    private String attendanceTime;
    private String employeeId;
}

