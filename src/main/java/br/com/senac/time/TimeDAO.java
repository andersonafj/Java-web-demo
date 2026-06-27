package br.com.senac.time;

import br.com.senac.banco.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DAO - Data Acess Object
public class TimeDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/aula-java";
    private static final String USER = "root";
    private static final String PASS = "root";


    public TimeDAO() {
    }

    private Connection conectar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public TimeEntity inserir(TimeEntity time) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO time (nome) values (?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, time.getNome());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    time.setId(rs.getInt(1));
                }
            }
        }
        return time;
    }

    public List<TimeEntity> listarTodos() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM time";
        List<TimeEntity> times = new ArrayList<>();
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                times.add(new TimeEntity(rs.getInt("id"), rs.getString("nome")));
            }
        }
        return times;
    }

}
