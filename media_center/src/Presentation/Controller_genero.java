package Presentation;

import Business.CategoriaIgualException;
import Business.MC;
import Business.Musica;
import Business.Video;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller_genero {
    private int idd; // música=1; video=2;
    private MC model;
    private View view;
    private Musica m;
    private Video v;

    @FXML
    private TextField g_atual;


    @FXML
    private ChoiceBox<String> n_gen;






public void setText(String a){
        g_atual.setText(a);
    }

    public void setIdd(int a){
        this.idd=a;
    }

    public void setM(MC m){
        this.model=m;

    }

    public void setV(View v){
        this.view=v;

    }

    public void setMusica(Musica mm){
        this.m=mm;
    }

    public void setVideo(Video vv){
        this.v=vv;
    }

    public void setChoice(ObservableList<String> av){
      n_gen.setItems(av);
    }

    @FXML
    private void handleButtonAction_alterar(ActionEvent event) throws IOException {
    if(idd==1) {
        String neww = n_gen.getSelectionModel().getSelectedItem();
        try {
            model.alterarCategoria(neww, m.getId(), 'm');
            FXMLLoader l = new FXMLLoader(getClass().getResource("Alt_gen_sucesso.fxml"));
            Parent root = l.load();
            Controller_upload up = l.getController();
            up.setM(this.model);
            up.setV(view);
            this.view.printPage((Node) event.getSource(), root);
        } catch (CategoriaIgualException e) {
            FXMLLoader l=new FXMLLoader(getClass().getResource( "Erro_Género.fxml"));
            Parent root = l.load();
            Controller_upload up = l.getController();
            up.setM(this.model);
            up.setV(view);
            this.view.printPage((Node) event.getSource(), root);
        }
    }
    if(idd==2){
        String neww = n_gen.getSelectionModel().getSelectedItem();
        try {
            model.alterarCategoria(neww, v.getId(), 'v');
            FXMLLoader l = new FXMLLoader(getClass().getResource("Alt_gen_sucesso.fxml"));
            Parent root = l.load();
            Controller_upload up = l.getController();
            up.setM(this.model);
            up.setV(view);
            this.view.printPage((Node) event.getSource(), root);
        } catch (CategoriaIgualException e) {
            FXMLLoader l=new FXMLLoader(getClass().getResource( "Erro_Género.fxml"));
            Parent root = l.load();
            Controller_upload up = l.getController();
            up.setM(this.model);
            up.setV(view);
            this.view.printPage((Node) event.getSource(), root);
        }
    }
    }



    @FXML
    private void handleButtonAction_goback(ActionEvent event) throws IOException {
        FXMLLoader l=new FXMLLoader(getClass().getResource( "geral.fxml"));
        Parent root = l.load();
        this.view.printPage((Node) event.getSource(),root);

        Controller_geral control = l.getController();
        control.setM(model);
        control.setV(view);

    }

    @FXML private void initialize () {

    }
}