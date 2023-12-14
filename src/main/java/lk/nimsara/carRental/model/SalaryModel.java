package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public List<SalaryDto> getAllSalaryes() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM salary";

        PreparedStatement pstm = connection.prepareStatement(sql);
        List<SalaryDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String Amount = resultSet.getString(3);
            String EmpId = resultSet.getString(4);


            var dto = new SalaryDto(id,date,Amount,EmpId);
            dtoList.add(dto);
        }

        return dtoList;
    }
    public boolean saveSalary(final SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO salary VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSalary_id());
        pstm.setString(2, dto.getSalary_date());
        pstm.setString(3, dto.getSalaryAmount());
        pstm.setString(4, dto.getEmployee_id());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
    public boolean deleteSalary(String Salary_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM salary WHERE Salary_id= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Salary_id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateSalary(final SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE salary SET Date =?,Amount =?,Employee_id =? WHERE Salary_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSalary_date());
        pstm.setString(2, dto.getSalaryAmount());
        pstm.setString(3, dto.getEmployee_id());
        pstm.setString(4, dto.getSalary_id());





        return pstm.executeUpdate() > 0;
    }
}