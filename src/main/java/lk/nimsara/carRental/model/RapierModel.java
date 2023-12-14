package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.EmployeeDto;
import lk.nimsara.carRental.dto.RapierDto;
import lk.nimsara.carRental.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RapierModel {

    public boolean saveRapier(final RapierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO rapier VALUES(?, ?, ?, ?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getRapier_id());
        pstm.setString(2, dto.getRapier_date());
        pstm.setString(3, dto.getRapier_desc());
        pstm.setString(4, dto.getRapier_returnDate());
        pstm.setString(5, dto.getRapier_price());
        pstm.setString(6, dto.getCar_id());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
    public List<RapierDto> loadAllRapiers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM rapier";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<RapierDto> reList = new ArrayList<>();

        while (resultSet.next()){
            reList.add(new RapierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return reList;
    }
    public List<RapierDto> getAllRapiers() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM rapier";

        PreparedStatement pstm = connection.prepareStatement(sql);
        List<RapierDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            String Rapier_id= resultSet.getString(1);
            String Rapier_date = resultSet.getString(2);
            String Rapier_returnDate = resultSet.getString(3);
            String Rapier_desc = resultSet.getString(4);
            String Rapier_price= resultSet.getString(5);
            String Car_id= resultSet.getString(6);

            var dto = new RapierDto(Rapier_id, Rapier_date, Rapier_returnDate, Rapier_desc,Rapier_price,Car_id);
            dtoList.add(dto);
        }

        return dtoList;
    }
    public boolean updateRapier(final RapierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="UPDATE rapier SET Rapier_date =?,Rapier_desc =?,Rapier_returnDate = ?,Rapier_price =?  WHERE Rapier_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getRapier_date());
        pstm.setString(2, dto.getRapier_desc());
        pstm.setString(3, dto.getRapier_returnDate());
        pstm.setString(4, dto.getRapier_price());
        pstm.setString(5, dto.getRapier_id());




        return pstm.executeUpdate() > 0;
    }
    public boolean deleteRapier(String Rapier_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM rapier WHERE Rapier_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Rapier_id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateCarstatus2(String carId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = " update car set Car_Availability = ? where Car_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1 ,"No");
        preparedStatement.setString(2,carId);

        return preparedStatement.executeUpdate() > 0;

    }

}
