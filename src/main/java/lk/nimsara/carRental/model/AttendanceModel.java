package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.AttendanceDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AttendanceModel {

    public boolean saveAttendance(AttendanceDto attendanceDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO  attendance VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, attendanceDto.getAttendanceId());
        pstm.setDate(2, Date.valueOf(attendanceDto.getAttendanceDate()));
        pstm.setString(3, attendanceDto.getAttendanceTime());
        pstm.setString(4,attendanceDto.getEmployeeId());

        int i = pstm.executeUpdate();
        return i > 0;

    }

    public boolean updateAttendance(AttendanceDto attendanceDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE attendance SET Attendance_date = ?, Attendance_time = ?  WHERE Attendance_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, String.valueOf(attendanceDto.getAttendanceDate()));
        pstm.setString(2,attendanceDto.getAttendanceTime());
        pstm.setString(3,attendanceDto.getAttendanceId());


        return pstm.executeUpdate() > 0;
    }

    public boolean deleteAttendance(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM attendance WHERE Attendance_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public List<AttendanceDto> getAllAttendance() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="SELECT * FROM attendance";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<AttendanceDto> attendanceList =new ArrayList<>();

        while (resultSet.next()){
            AttendanceDto attendanceDto =new AttendanceDto(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getString(4));

            attendanceList.add(attendanceDto);
        }
        return attendanceList;
    }

    public AttendanceDto searchAttendance(String atdID) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance WHERE Attendance_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, atdID);

        ResultSet resultSet = pstm.executeQuery();

        AttendanceDto  attendanceDto = null;

        if (resultSet.next()){
            String attendanceId =resultSet.getString(1);
            LocalDate attendanceDate = LocalDate.parse(resultSet.getString(2));
            String attendanceTime =resultSet.getString(3);
            String employeeId =resultSet.getString(4);

            attendanceDto =new AttendanceDto(attendanceId,attendanceDate,attendanceTime,employeeId);

        }
        return attendanceDto;

    }
}

