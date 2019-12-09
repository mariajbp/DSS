package Business;

import javafx.collections.MapChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.StringTokenizer;


public class MC
{
    /** Instancia da gestao de conteudo**/
    private GestaoConteudo gc = new GestaoConteudo();
    /** Instancia da gestao de utilizador**/
    private GestaoUtilizador gu = new GestaoUtilizador();
    /** Id do utilizador atual**/
    private int idUtilizadorAtual;
    /** Identificador do tipo de utilizador             Administrador : 1                Utilizador Registado : 2   **/
    private int idType;
    /** Metadados de conteudo a fazer upload **/
    private volatile String album;
    private volatile String artist;
    private volatile String title;
    private volatile String categoria;
    private volatile double duracao ;

    /** Construtor da classe MC sem parâmetros **/
    public MC() { }

    /** Método que inicia uma sessão
     * @param mail    Email do utilizador a autenticar
     * @param pass    Password do utilizador a autenticar
     */
    public void iniciarSessao(String mail, String pass) throws CredenciaisInvalidasException {
        if(mail == null||pass == null || mail.isEmpty() || pass.isEmpty()) throw new CredenciaisInvalidasException();
        gu.iniciarSessao(mail, pass, idType);
    }

    /** Método que  termina uma sessão **/
    public void terminarSessao() {
        idUtilizadorAtual = -1;
        idType = -1;
    }

    /** Método que faz o upload de conteudo
     * @param p   Path do conteudo a fazer upload
     */
    public void uploadConteudo(String p) throws FormatoDesconhecidoException, IOException, ConteudoDuplicadoException, URISyntaxException {
        System.out.println(p);
        StringTokenizer tokens = new StringTokenizer( p,".");
        tokens.nextToken();
        String type = tokens.nextToken();
        UtilizadorRegistado u =(UtilizadorRegistado) gu.getUser(idUtilizadorAtual,idType);
        char t;
        Conteudo c;



        //Extrair metadados
        Media media = new Media(p);
        MediaPlayer player = new MediaPlayer(media);
        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                    if (change.wasAdded()) listnerHandle(change.getKey(),change.getValueAdded());
                }
        );

        //Salvaguardar metadados
        if(title == null) title= "N/D";
        if(artist == null)artist = "N/D";
        if(categoria == null) categoria = "N/D";
        if(album == null) album = "N/D";
        //Verificar formato
        if (type.equals("mp3")){
            t = 'm';
            c = new Musica(p.hashCode(), title, duracao, "mp3", categoria, artist);
        }
        else if (type.equals("mp4")){
            t='v';
            c = new Video();
            c.setId(p.hashCode());
        }
        else throw new FormatoDesconhecidoException();

        //Verificar duplicaçoes
        if(gc.verificaDuplicacoes(c,t)) throw new ConteudoDuplicadoException();
        //Adicionar a bibliotecas
        gc.uploadConteudo(c,t,u);
        gu.uploadConteudo(c, t,u);
        System.out.println("NAO");
        //Path building
        String path=(new File("").getAbsolutePath())+"/Biblioteca/"+c.getId()+ (t=='m'? ".mp3":".mp4" );
        File newFile = new File(path);
        File origin = new File((new URI(p)).getPath());
        newFile.createNewFile();
        origin.renameTo(newFile);

    }

    /** Método de extração de metadados
     * @param key     chave que descreve do tipo de metadado
     * @param value   valor do metadado
     */
    private void listnerHandle(String key, Object value){
        switch (key) {
            case "album":
                album = (String) value;
                break;

            case "artist":
                artist = (String) value;
                break;

            case "title":
                title = (String) value;
                break;
            case "genre":
                categoria = (String) value;
                break;
            case "duration":
                duracao = (double) ((Duration) value ).toMillis();
                break;
        }
    }

    /** Método que altera o tipo do utilizador que está a usar o sistema
     * @param idT    Tipo do utilizador
     */
    public void setUserT(int idT) {
        idType = idT;
    }

    /** Método que altera o tipo do utilizador a usar o sistema
     * @return      Tipo do utilizador
     */
    public int getUserT() {
        return idType;
    }
}


