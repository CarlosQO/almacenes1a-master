package modelo.crudDocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Conexion;

public class DocumentoDao implements CrudDocumento<Documento> {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Documento> listar() {
        List<Documento> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_doc";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Documento doc = new Documento();
                doc.setId(rs.getInt(1));
                doc.setNombre(rs.getString(2));
                lista.add(doc);
            }
        } catch (Exception e) {
            System.out.println("Error al listar documentos: " + e);
        }
        return lista;
    }
}
