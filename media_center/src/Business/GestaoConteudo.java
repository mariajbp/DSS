package Business;

import Database.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GestaoConteudo {
    /**
     * Biblioteca geral de Musicas
     **/
    private Map<Integer, Musica> musicas = new MusicaDAO();
    /**
     * Biblioteca geral de Videos
     **/
    private Map<Integer, Video> videos = new VideoDAO();
    /**
     * Proprietarios das Musicas
     **/
    private Map<Integer, List<UtilizadorRegistado>> proprietariosMusica = new ProprietariosMusicaDAO();
    /**
     * Proprietarios dos Videos
     **/
    private Map<Integer, List<UtilizadorRegistado>> proprietariosVideo = new ProprietariosVideoDAO();


    /**
     * Método que verifica duplicacoes de conteudo
     *
     * @param c    Conteudo a comparar
     * @param type Tipo do conteudo
     * @return id do conteudo caso seja repetido, -1 caso não hajam repetiçoes
     */
    public int verificaDuplicacoes(Conteudo c, char type) {
        int ret = -2;
        if (type == 'm') {
            ret = (musicas.containsValue(c) ? (this.getValueM((Musica) c)) : -1);
        }
        if (type == 'v') {
            ret = (videos.containsValue(c) ? (this.getValueV((Video) c)) : -1);
        }
        return ret;
    }
    /**
     * Método para obter um conteudo da biblioteca geral
     *
     * @param idC   id do conteudo
     * @param type tipo do conteudo a obter
     * @return Conteudo
     */
   public Conteudo getConteudo(int idC, char type){
       Conteudo ret= null;
       if(type=='m') ret=musicas.get(idC);
       
       if(type=='v') ret=videos.get(idC);
       return ret;
   } 
     /**
     * Método para unificar id de Musica repetido
     *
     * @param c    Conteudo a comparar
     * @return id do conteudo
     */
    int getValueM(Musica c){
       Collection<Musica> ms = musicas.values();
       for(Musica m : ms){
           if(m.getArtista(). equals(c.getArtista()) &&
            m.getCategoria().equals(c.getCategoria()) &&
            m.getNome().equals(c.getNome()))
                return m.getId();
           
       }
       return -1;
    }
/**
     * Método para unificar id de Video repetido
     *
     * @param c    Conteudo a comparar
     * @return id do conteudo
     */
    int getValueV(Video c){
        Collection<Video> ms = videos.values();
        for(Video m : ms){
            if(m.getRealizador(). equals(c.getRealizador()) &&
             m.getCategoria().equals(c.getCategoria()) &&
             m.getNome().equals(c.getNome()))
                 return m.getId();
            
        }
        return -1;
     }

    /**
     * Método que adiciona o conteudo à biblioteca geral e atualiza o seu proprietario
     *
     * @param c    Conteudo a adicionar
     * @param tipo Tipo do conteudo a adicionar
     * @param u    Utilizador que está a carregar o conteudo
     **/
    public void uploadConteudo(Conteudo c, char tipo, UtilizadorRegistado u, int dupId) {
        Map<Integer, List<UtilizadorRegistado>> props = null;
        if (tipo == 'm') {
            if(dupId == -1) musicas.put(c.getId(), (Musica) c);
            props = proprietariosMusica;
        } else {
            if (tipo == 'v') {
                
            if(dupId == -1) videos.put(c.getId(), (Video) c);
                props = proprietariosVideo;
                System.out.println("2");
            }
        }
        List<UtilizadorRegistado> propList = new ArrayList<>();
        propList.add(u);
        props.put(c.getId(), propList);
    }
    /**Método para apresentar a Biblioteca geral dos Videos
     * @return Set com todas as instancias de video da biblioteca geral
     * **/
    public Set<Video> getBibliotecaVideo() {
        return ((Set) videos.values());
    }

    /**Método para apresentar a Biblioteca geral das Musicas
     * @return Set com todas as instancias de musica da biblioteca geral
     * **/
    public Set<Musica> getBibliotecaMusica() { return ((Set) musicas.values()); }
}