package Presentation;
import Business.MC;
import Business.Musica;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Player {

    private MC model;
    private View view;
    private MediaPlayer player;
    private Set<Musica> mus;
    private int id;

    @FXML
    private Slider time;

    @FXML
    private Slider vol;

    @FXML
    private ListView<Musica> lv;

    @FXML
    private MediaView v;


    @FXML
    private Text t;

    public void setId(int x){
        this.id=x;
    }

    public void setModel(MC m){
        this.model=m;
    }

    public void setMus(Set<Musica> mus){
        this.mus = mus;
    }


    public void setM(MC m) {
        this.model = m;

    }

    public void setV(View v) {
        this.view = v;

    }
    public void setMPlayer(MediaPlayer pl) {
        this.player = pl;
        player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov)
            {
                updatesValues();
            }
        });
    }

    public void sett() {
        v.setMediaPlayer(player);
    }

    public void setText(String a){
        t.setText(a);
    }

    public void inic(){
        List<Musica> ml= new ArrayList<>();
        for(Musica m2: mus) {
            ml.add(m2);
        }

        ObservableList<Musica> l =FXCollections.observableArrayList();
        l.addAll(ml);
        lv.getItems().setAll(l);
        lv.setCellFactory(new Callback<ListView<Musica>, ListCell<Musica>>() {
            @Override
            public ListCell<Musica> call(ListView<Musica> lv) {
                return new ListCell<Musica>() {
                    @Override
                    public void updateItem(Musica item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.getNome());
                        }
                    }
                };
            }
        });

    }

    @FXML
    private void handleButtonAction_buttonreproduz(ActionEvent e) {
        Button b = (Button) e.getSource();
        MediaPlayer.Status status = player.getStatus(); //get status of player

        if (status == MediaPlayer.Status.PLAYING) {
            //If the status is Video playing
            player.pause();
        }

        if (status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.PAUSED) {
            player.play(); //Start
        }


    }

    @FXML
    private void handleButtonAction_Reproduzirr(MouseEvent event) throws IOException {
        player.pause();
       Musica m = lv.getSelectionModel().selectedItemProperty().getValue();

        //inicialização do player
        Media media = new Media(model.getPath('m', m.getId()));
        MediaPlayer player = new MediaPlayer(media);
        player.play();


        FXMLLoader l=new FXMLLoader(getClass().getResource( "player.fxml"));
        Parent root = l.load();
        this.view.printPage((Node) event.getSource(),root);


        //set model e view do Player
        Player pl = l.getController();
        pl.setV(view);
        pl.sett();
        pl.setMPlayer(player);
        pl.setText(m.getNome());
        pl.setModel(model);
        pl.setMus(model.showMusicas());
        pl.setId(m.getId());
        pl.inic();


    }

    @FXML
    private void handleButtonAction_proxima (ActionEvent e) throws IOException {
        player.pause();
        int i=0;
        List<Musica> ml= new ArrayList<>();
        for(Musica m2: mus) {
            ml.add(m2);
        }
        for(; i<ml.size();i++){
            if(ml.get(i).getId()==id) i=ml.size();
        }
        Musica m = ml.get((i%ml.size())+1);


        //inicialização do player
        Media media = new Media(model.getPath('m',m.getId()));
        MediaPlayer player = new MediaPlayer(media);
        player.play();

        FXMLLoader l=new FXMLLoader(getClass().getResource( "player.fxml"));
        Parent root = l.load();
        this.view.printPage((Node) e.getSource(),root);


        //set model e view do Player
        Player pl = l.getController();
        pl.setModel(model);
        pl.setV(view);
        pl.sett();
        pl.setMPlayer(player);
        pl.setText(m.getNome());
        pl.setMus(model.showMusicas());
        pl.setId(m.getId());
        pl.inic();

    }

    @FXML
    private void handleButtonAction_anterior (ActionEvent e) throws IOException {
        player.pause();
        int i=0;
        List<Musica> ml= new ArrayList<>();
        for(Musica m2: mus) {
            ml.add(m2);
        }
        for(; i<ml.size();i++){
            if(ml.get(i).getId()==id) i=ml.size();
        }
        Musica m = ml.get((i%ml.size())-1);


        //inicialização do player
        Media media = new Media(model.getPath('m',m.getId()));
        MediaPlayer player = new MediaPlayer(media);
        player.play();

        FXMLLoader l=new FXMLLoader(getClass().getResource( "player.fxml"));
        Parent root = l.load();
        this.view.printPage((Node) e.getSource(),root);


        //set model e view do Player
        Player pl = l.getController();
        pl.setModel(model);
        pl.setV(view);
        pl.sett();
        pl.setMPlayer(player);
        pl.setText(m.getNome());
        pl.setMus(model.showMusicas());
        pl.setId(m.getId());
        pl.inic();

    }

    @FXML
    //Providing functionality to time slider
    private void handleButtonAction_timeslider(MouseEvent e) {
        player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov)
            {
                updatesValues();
            }
        });
        player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));

    }


    // providing functionality to volume slider
    @FXML
    private void handleButtonAction_volume(MouseEvent e) {
        player.setVolume(vol.getValue() / 100); // It would set the volume

    }

    @FXML
    private void handleButton_soundoff(ActionEvent e) {
        if(player.getVolume()==0) {
            player.setVolume(vol.getValue() / 100);
        }
        else {
            player.setVolume(0);
        }

    }

    private void updatesValues() {
        Platform.runLater(new Runnable() {
            public void run() {
                time.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
            }
        });
    }


    /**
     * método que trata do evento: clique no botão "retroceder" na página do player
     *
     * @param event
     */
    @FXML
    private void handleButtonAction_goback_player(ActionEvent event) throws IOException {
        MediaPlayer.Status status = player.getStatus(); //get status of player
        if (status == MediaPlayer.Status.PLAYING) {
            //If the status is Video playing
            player.pause();
        }

        if (this.model.getUserT()==2) {
            FXMLLoader l = new FXMLLoader(getClass().getResource("Utilizador_Registado.fxml"));
            Parent root = l.load();

            // set model e view do Controller_Reg
            Controller_Regist control = l.getController();
            control.setM(model);
            control.setV(view);
            this.view.printPage((Node) event.getSource(), root);
        }
        else {
            //retorna página inicial do convidado: não necessitamos do admin uma vez que este nao acessa o player
            FXMLLoader l = new FXMLLoader(getClass().getResource("convidado.fxml"));
            Parent root = l.load();
            this.view.printPage((Node) event.getSource(), root);
        }
    }




    @FXML private void initialize () {

    }


}




