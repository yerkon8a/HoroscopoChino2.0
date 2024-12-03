package dao;

import modelo.Horoscopo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexionBd.Conexion;

public class HoroscopoDAOImp extends Conexion implements HoroscopoDAO {

    @Override
    public List<Horoscopo> obtenerHoroscopo() {
        List<Horoscopo> horoscopo = new ArrayList<>();
        String consultaSql = "SELECT * FROM horoscopo ORDER BY fecha_inicio";

        try (Connection conn = generaPoolConexion();
             PreparedStatement pstm = conn.prepareStatement(consultaSql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Horoscopo h = new Horoscopo(
                        rs.getString("animal"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin")
                );
                horoscopo.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horoscopo;
    }
}