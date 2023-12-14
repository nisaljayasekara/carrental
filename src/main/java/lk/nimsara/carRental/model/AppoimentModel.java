package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.AppoimentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppoimentModel {

    public boolean saveAppoiment(final AppoimentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO appoiment VALUES(?, ?, ?, ?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setDate(2, Date.valueOf(dto.getDate()));
        pstm.setString(3, dto.getTime());
        pstm.setString(4, dto.getReturndate());
        pstm.setString(5, dto.getAppoiment_returnTime());
        pstm.setString(6, dto.getCustomer_id());
        pstm.setString(7, dto.getCar_id());
        pstm.setString(8, dto.getCar_name());
        pstm.setString(9, dto.getCar_price());
        pstm.setString(10, dto.getPayment());



        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
    public boolean deleteAppoiment(String Appoiment_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM appoiment WHERE Appoiment_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Appoiment_id);

        return pstm.executeUpdate() > 0;
    }
   public boolean updateAppoiment(final AppoimentDto appoimentDto) throws SQLException {
         Connection connection = DbConnection.getInstance().getConnection();
       System.out.println(appoimentDto.getDate());
        String sql = "UPDATE appoiment SET Appoiment_date=?,Appoiment_time=?,Return_date =?,Customer_id=?,Appoiment_returnTime=?,Car_id=?,Car_name =?,Car_price =?,payment =? WHERE Appoiment_id=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, appoimentDto.getTime());
        pstm.setString(2, appoimentDto.getDate());
        pstm.setString(3, appoimentDto.getReturndate());
        pstm.setString(4, appoimentDto.getCustomer_id());
        pstm.setString(5, appoimentDto.getAppoiment_returnTime());
        pstm.setString(6, appoimentDto.getCar_id());
        pstm.setString(7, appoimentDto.getCar_name());
        pstm.setString(8, appoimentDto.getCar_price());
        pstm.setString(9, appoimentDto.getPayment());
        pstm.setString(10, appoimentDto.getId());

        return pstm.executeUpdate() > 0;


    }
    public List<AppoimentDto> getAllAppoiment() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM appoiment";

        PreparedStatement pstm = connection.prepareStatement(sql);
        List<AppoimentDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            String id= resultSet.getString(1);
            String date = resultSet.getString(2);
            String time = resultSet.getString(3);
            String Returndate = resultSet.getString(4);
            String Appoiment_returnTime= resultSet.getString(5);
            String Customer_id= resultSet.getString(6);
            String Car_id= resultSet.getString(7);
            String Car_name= resultSet.getString(8);
            String Car_price= resultSet.getString(9);
            String payment= resultSet.getString(10);


            var dto = new AppoimentDto(id,date,time,Returndate,Appoiment_returnTime,Customer_id,Car_id,Car_name,Car_price,payment);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
