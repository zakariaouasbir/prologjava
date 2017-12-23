/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import service.PlFacade;

/**
 * FXML Controller class
 *
 * @author zack
 */
public class MakequeryController implements Initializable {

    @FXML
    private JFXButton addPredicate;
    @FXML
    private JFXTextField inputPredicate;
    @FXML
    private JFXTextArea plPredicates;

    //write on file
    @FXML
    private JFXTextField checkPredicate;
    @FXML
    private JFXTextArea results;

    //side menu
    @FXML
    private Pane addView;
    @FXML
    private Pane consultView;
    @FXML
    private Pane editView;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger burger;

    @FXML
    private JFXTextArea editArea;

    @FXML
    private JFXButton saveBtn;

    PlFacade plFacade = new PlFacade();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initpredicates();
            initSidemenu();
            initEdit();
            onEdit();
            moveHamburger();
        } catch (Exception ex) {

        }
    }

    HamburgerSlideCloseTransition transition;

    public void moveHamburger() {
        transition = new HamburgerSlideCloseTransition(burger);
        transition.setRate(-1);
        burger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transBurger();
        });
    }

    public void transBurger() {
        transition.setRate(transition.getRate() * (-1));
        transition.play();
        moveDrawer();
    }

    public void initSidemenu() throws IOException {
        VBox box = FXMLLoader.load(getClass().getResource("SideMenu.fxml"));
        drawer.setSidePane(box);

        for (Node node : box.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "M1":
                            initpredicates();
                            addView.setVisible(true);
                            consultView.setVisible(false);
                            editView.setVisible(false);
                            transBurger();
                            break;

                        case "M2":
                            addView.setVisible(false);
                            consultView.setVisible(true);
                            editView.setVisible(false);
                            transBurger();
                            break;

                        case "M3":
                            initEdit();
                            addView.setVisible(false);
                            consultView.setVisible(false);
                            editView.setVisible(true);
                            transBurger();

                            break;

                        case "M0":
                            System.exit(0);

                    }

                });
            }

        }
    }

    public void moveDrawer() {
        if (drawer.isShown()) {
            drawer.close();
        } else {
            drawer.open();
        }
    }

    public void makerequest(ActionEvent actionEvent) {
        if (!checkPredicate.getText().equals("")) {
            results.setText("");
            String t = checkPredicate.getText();
            String solution = plFacade.makeRequest(t);
            results.setText(results.getText() + solution);
        } else {
            System.out.println("error");
        }

    }//fin makerequest

    public void onEdit() {
        
        editArea.textProperty().addListener((obs, old, niu) -> {
            
            if(!old.equals(niu)){
                saveBtn.setDisable(false);
            }
            
            
        });
    }

    public void writepredicate(ActionEvent actionEvent) {
        if (!inputPredicate.getText().equals("")) {
            plFacade.save(inputPredicate.getText());
            refresh();
        } else {
            System.out.println("error");
        }
    }

    public void initpredicates() {
        plPredicates.setText("");
        String content = plFacade.readFile();
        plPredicates.setText(content);

    }

    private void initEdit() {
        editArea.setText(plFacade.readFile());
        saveBtn.setDisable(true);
    }

    @FXML
    void cancelEdit(ActionEvent event) {
        editArea.setText("");
    }

    @FXML
    void saveEdit(ActionEvent event) {
        plFacade.saveFile(editArea.getText());
        saveBtn.setDisable(true);
    }

    //refresh
    public void refresh() {
        initpredicates();
    }

    public JFXTextField getCheckPredicate() {
        return checkPredicate;
    }

    public void setCheckPredicate(JFXTextField checkPredicate) {
        this.checkPredicate = checkPredicate;
    }

    public JFXTextArea getResults() {
        return results;
    }

    public void setResults(JFXTextArea results) {
        this.results = results;
    }

    public Button getAddPredicate() {
        return addPredicate;
    }

    public void setAddPredicate(JFXButton addPredicate) {
        this.addPredicate = addPredicate;
    }

    public TextField getInputPredicate() {
        return inputPredicate;
    }

    public void setInputPredicate(JFXTextField inputPredicate) {
        this.inputPredicate = inputPredicate;
    }

    public MakequeryController() {
    }

    public JFXTextArea getPlPredicates() {
        return plPredicates;
    }

    public void setPlPredicates(JFXTextArea plPredicates) {
        this.plPredicates = plPredicates;
    }

    public PlFacade getPlFacade() {
        return plFacade;
    }

    public void setPlFacade(PlFacade plFacade) {
        this.plFacade = plFacade;
    }

}
