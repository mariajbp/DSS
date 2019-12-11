package Presentation;

import Business.MC;
import Business.Musica;
import Business.Video;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.Set;

public class Controller_geral {

    private MC model=new MC();
    private View view= new View();



    public void setM(MC m){
        this.model=m;

    }

    public void setV(View v){
        this.view=v;

    }

    @FXML
    private TableView<Musica> table1;


    @FXML
    private TableColumn<Musica, String> nome_m;

    @FXML
    private TableColumn<Musica, String> artista;

    @FXML
    private TableColumn<Musica, String> cat_m;

    @FXML
    private TableView<Video> table2;


    @FXML
    private TableColumn<Video, String> nome_v;

    @FXML
    private TableColumn<Video, String > realizador;

    @FXML
    private TableColumn<Video, String> cat_v;


    /**
     * método que trata do evento: clique no botão de uma música na listView do convidado
     * este método inicia o player
     * @param event
     */
    @FXML
    private void handleButtonAction_Musica(MouseEvent event) throws IOException {

        Musica m =table1.getSelectionModel().selectedItemProperty().getValue();

        FXMLLoader l=new FXMLLoader(getClass().getResource( "opcoes.fxml"));
        Parent root = l.load();
        this.view.printPage((Node) event.getSource(),root);


        Controller_opt pl = l.getController();
        pl.setV(view);
        pl.setM(model);
        pl.setIdd(1);
        pl.setMusica(m);


    }

    /**
     * método que trata do evento: clique no botão de um video na listView do convidado
     * este método inicia o player
     * @param event
     */
    @FXML
    private void handleButtonAction_Video(MouseEvent event) throws IOException {

        Video v =table2.getSelectionModel().selectedItemProperty().getValue();

        FXMLLoader l=new FXMLLoader(getClass().getResource( "opcoes.fxml"));
        Parent root = l.load();
        this.view.printPage((Node) event.getSource(),root);


        Controller_opt pl = l.getController();
        pl.setV(view);
        pl.setM(model);
        pl.setIdd(2);
        pl.setVideo(v);


    }


    /**
     * método que trata do evento: clique no botão logout na página inicial do convidado
     * @param event
     */
    @FXML
    private void handleButtonAction_goback(ActionEvent event) throws IOException {
        FXMLLoader l=new FXMLLoader(getClass().getResource( "Utilizador_Registado.fxml"));
        Parent root = l.load();
        this.view.printPage((Node) event.getSource(),root);

        Controller_Regist pl=l.getController();
        pl.setV(view);
        pl.setM(model);

    }




    /**
     * inicializa a biblioteca geral já com a lista das músicas e vídeos
     */
    @FXML private void initialize () {

        nome_m.setCellValueFactory( new PropertyValueFactory<>("nome"));
        artista.setCellValueFactory( new PropertyValueFactory<>("artista"));
        cat_m.setCellValueFactory( new PropertyValueFactory<>("categoria"));



        nome_v.setCellValueFactory( new PropertyValueFactory<>("nome"));
        realizador.setCellValueFactory( new PropertyValueFactory<>("realizador"));
        cat_v.setCellValueFactory( new PropertyValueFactory<>("categoria"));

        Set<Musica> mus=  model.showMusicas();
        Set<Video> vid = model.showVideos();


        ObservableList<Musica> m = FXCollections.observableArrayList();
        m.addAll(mus);
        table1.getItems().setAll(m);


        ObservableList<Video> v = FXCollections.observableArrayList();
        v.addAll(vid);
        table2.getItems().setAll(v);


    }


}
