package Database;

import Business.Musica;
import Business.Video;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlaylistVideoDAO implements Map<Integer, List<Video>>
{
    private Connection c;

    /**
     * Método que retorna o número de entradas na base de dados
     * @return s                       número de entradas
     * @throws NullPointerException    Não há conexão com a base de dados
     */
    @Override
    public int size() {
        int s = -1;
        try {
            c = Connect.connect();
            PreparedStatement stm = c.prepareStatement("SELECT count(*) FROM PlaylistVideo");
            ResultSet rs = stm.executeQuery();
            if(rs.next()) { s = rs.getInt(1); }
        }
        catch (Exception e) { throw new NullPointerException(e.getMessage()); } finally { Connect.close(c); }
        return s;
    }

    /**
     * Método que verifica se a base de dados está vazia
     * @return  True caso a base de dados esteja vazia, false caso contrário
     */
    @Override
    public boolean isEmpty() { return (this.size() == 0); }

    /**
     * Método que verifica se o id de uma playlist existe na base de dados
     * @param o                      Objeto a verficar
     * @return                       True se a playlist existir
     * @throws NullPointerException  Não existe conexão com a base de dados
     */
    @Override
    public boolean containsKey(Object o) {
        boolean res = false;
        try {
            c = Connect.connect();
            String sql = "SELECT idPlaylist FROM PlaylistVideo WHERE idPlaylist = ?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setInt(1, (Integer) o);
            ResultSet rs = stm.executeQuery();
            res = rs.next();
        } catch (Exception e) { throw new NullPointerException(e.getMessage()); } finally { Connect.close(c); }
        return res;
    }

    @Override
    public boolean containsValue(Object o) { throw new UnsupportedOperationException("Not Implemented"); }

    /**
     * Método que verifica se o id de uma determinada playlist existe na base de dados
     * @param o                      Objeto a verficar
     * @return                       True se a playlist existir
     * @throws NullPointerException  Não existe conexão com a base de dados
     */
    @Override
    public List<Video> get(Object o) {
        Video m = new Video();
        ArrayList<Video> array = new ArrayList<>();
        try {
            c = Connect.connect();

            String sql = "SELECT idPlaylist FROM PlaylistVideo WHERE idPlaylist = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, (Integer) o);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    PreparedStatement pss = c.prepareStatement("SELECT * FROM Video WHERE idVideo = ?");
                    pss.setInt(1, rs.getInt("idVideo"));
                    ResultSet rss = ps.executeQuery();
                    m = new Video(rss.getInt("idVideo"), rss.getString("nome"), rss.getDouble("duracao"), rss.getString("formato"), rss.getString("categoria"), rss.getString("realizador"));
                    array.add(m); } catch (SQLException ex) { ex.printStackTrace(); }
            } }
        catch (Exception e) { e.printStackTrace(); } finally { Connect.close(c); }
        return array;
    }

    /**
     * Método que insere uma nova playlist na base de dados
     * @param k      id da playlist
     * @param v      lista de videos
     * @return
     */
    @Override
    public List<Video> put(Integer k, List<Video> v) {
        ArrayList<Video> array = new ArrayList<>();

        try{
            c = Connect.connect();

            PreparedStatement ps = c.prepareStatement("INSERT INTO PlaylistVideo (idPlaylist,idVideo) VALUES (?,?)");
            for(Video vd : v) {
                ps.setInt(1,k);
                ps.setInt(2, vd.getId());
                ps.executeUpdate();
            }
        }
        catch(Exception e){ System.out.printf(e.getMessage()); }
        finally{ try{ Connect.close(c); } catch(Exception e){ System.out.printf(e.getMessage()); } }
        return array;
    }

    @Override
    public List<Video> remove(Object o) { throw new UnsupportedOperationException("Not Implemented"); }

    @Override
    public void putAll(Map<? extends Integer, ? extends List<Video>> map) { throw new UnsupportedOperationException("Not Implemented"); }

    @Override
    public void clear() {throw new UnsupportedOperationException("Not Implemented");}

    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Collection<List<Video>> values() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Set<Entry<Integer, List<Video>>> entrySet() {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
