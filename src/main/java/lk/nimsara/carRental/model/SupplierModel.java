package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public boolean saveSupplier(final SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Supplier VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSupplier_id());
        pstm.setString(2, dto.getSupplier_name());
        pstm.setString(3, dto.getSupplier_email());
        pstm.setString(4, dto.getTel());
        pstm.setString(5, dto.getUser_id());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
   public boolean deleteSupplier(String supId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE Supplier_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, supId);

        return pstm.executeUpdate() > 0;


    }



    public List<SupplierDto> getAllSuppliers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<SupplierDto> supList = new ArrayList<>();

        while (resultSet.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));

            supList.add(supplierDto);
        }
        return supList;
    }
    public boolean updateSupplier(final SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="UPDATE supplier SET Supplier_name =?,Supplier_email =?,Tel =?  WHERE Supplier_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSupplier_name());
        pstm.setString(2, dto.getSupplier_email());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getSupplier_id());




        return pstm.executeUpdate() > 0;
    }
}
