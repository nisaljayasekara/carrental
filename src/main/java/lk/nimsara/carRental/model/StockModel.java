package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.StockDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {

        public boolean saveStock(StockDto stockDto) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO  stock VALUES(?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, stockDto.getStockId());
            pstm.setString(2, stockDto.getDescription());
            pstm.setString(3, String.valueOf(stockDto.getQty_on_hand()));

            int i = pstm.executeUpdate();
            return i > 0;
        }

        public boolean deleteStock(String stockId) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "DELETE FROM stock WHERE Stock_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, stockId);

            return pstm.executeUpdate() > 0;

        }

        public boolean updateStock(StockDto stockDto) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "UPDATE stock SET  description = ?, qty_On_hand = ? WHERE Stock_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,stockDto.getDescription());
            pstm.setString(2,String.valueOf(stockDto.getQty_on_hand()));
            pstm.setString(3,stockDto.getStockId());

            return pstm.executeUpdate() > 0;
        }

        public List<StockDto> getAllStock() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql ="SELECT * FROM stock";

            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            List<StockDto> stockList =new ArrayList<>();

            while (resultSet.next()){
                StockDto stockDto =new StockDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));

                stockList.add(stockDto);
            }
            return  stockList;
        }

        public StockDto searchStock(String stockId) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM stock WHERE Stock_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, stockId);

            ResultSet resultSet = pstm.executeQuery();

            StockDto stockDto = null;

            if (resultSet.next()){
                String  Stock_id =resultSet.getString(1);
                String  description =resultSet.getString(2);
                int qty = Integer.parseInt(String.valueOf(resultSet.getInt(3)));

                stockDto = new StockDto(Stock_id,description,qty);
            }
            return stockDto;

        }
    }


