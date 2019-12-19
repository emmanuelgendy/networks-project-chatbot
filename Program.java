import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;


public class Program extends Application implements EventHandler{

    Stage window;
    Scene chattingScene,usernameScene;
    TextField messageBox,username;
    Button sendButton = new Button("Send");
    Button nudgeButton = new Button("Nudge");
    Button imageButton = new Button("Attach Image ▨");
    Button usernameConfirmation = new Button("Enter Room");
    Button changeUsername = new Button ("Change Username");
    Label chatHistory;
    String usernameAsString;
    File selectedImage;
    VBox vbox4, vbox5;
    ImageView imageLabel;

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("SBME ChatBot");

        BorderPane chattingLayout = new BorderPane();
        BorderPane usernameLayout = new BorderPane();

        String backgroundStyle = "-fx-background-color: rgba(200, 200, 200);";
        String borderStyle = "-fx-border-color: rgba(20, 20, 20);";

        username = new TextField("Enter username");
        username.setPrefSize(200,20);
        username.setAlignment(Pos.CENTER);
        VBox usernameGrid = new VBox();
        usernameGrid.setAlignment(Pos.CENTER);
        usernameGrid.getChildren().addAll(username,usernameConfirmation);
        usernameGrid.setPrefSize(200,50);
        usernameLayout.setCenter(usernameGrid);

        chatHistory = new Label();
        chatHistory.setPrefSize(600,500);
        chatHistory.setAlignment(Pos.TOP_LEFT);
        chatHistory.setStyle(borderStyle);
        VBox chatHistoryGrid = new VBox();
        chatHistoryGrid.getChildren().add(chatHistory);
        chatHistoryGrid.setStyle(backgroundStyle);
        chatHistoryGrid.setPadding(new Insets(10,10,10,10));
        chattingLayout.setLeft(chatHistoryGrid);

        messageBox = new TextField();
        messageBox.setPrefSize(650,90);
        messageBox.setAlignment(Pos.CENTER_LEFT);

        sendButton.setPrefSize(110, 20);
        nudgeButton.setPrefSize(110, 20);
        imageButton.setPrefSize(110, 20);

        VBox buttonsGrid = new VBox();
        buttonsGrid.getChildren().addAll(sendButton,nudgeButton,imageButton);
        buttonsGrid.setSpacing(10);
        buttonsGrid.setPadding(new Insets(0,10,0,10));

        HBox textAndButtonsGrid = new HBox();
        textAndButtonsGrid.getChildren().addAll(messageBox,buttonsGrid);
        textAndButtonsGrid.setPadding(new Insets(10,0,10,10));
        textAndButtonsGrid.setSpacing(10);
        messageBox.setAlignment(Pos.TOP_LEFT);
        chattingLayout.setBottom(textAndButtonsGrid);

        vbox4 = new VBox();
        vbox4.getChildren().addAll(changeUsername);
        vbox4.setPadding(new Insets(10,10,10,10));
        vbox4.setSpacing(10);
        vbox4.setAlignment(Pos.TOP_RIGHT);


        vbox5 = new VBox();
        vbox5.setPadding(new Insets(50,10,10,10));
        vbox5.setSpacing(10);
        imageLabel = new ImageView();
        imageLabel.setStyle(borderStyle);
        imageLabel.setPreserveRatio(true);
        imageLabel.setFitWidth(150);
        vbox5.getChildren().add(imageLabel);
        vbox5.setAlignment(Pos.CENTER);
        VBox vbox6 = new VBox();
        vbox6.getChildren().addAll(vbox4,vbox5);
        vbox6.setAlignment(Pos.TOP_RIGHT);
        chattingLayout.setRight(vbox6);




        sendButton.setOnAction(this);
        sendButton.setOnKeyPressed(this);
        usernameConfirmation.setOnAction(this);
        changeUsername.setOnAction(this);
        imageButton.setOnAction(this);
        nudgeButton.setOnAction(this);



        messageBox.setStyle(borderStyle);
        buttonsGrid.setStyle(backgroundStyle);
        textAndButtonsGrid.setStyle(backgroundStyle);



        chattingScene = new Scene(chattingLayout, 800, 600);
        usernameScene = new Scene(usernameLayout, 800, 600);
        window.setScene(usernameScene);
        window.show();
    }

    @Override
    public void handle(Event event) {
        if ((event.getSource() == sendButton) ||(event.getSource() == KeyCode.ENTER)) {
            String lastMessage = new String(messageBox.getCharacters().toString());
            messageBox.clear();
            chatHistory.setText(updateChatHistory(lastMessage));
        } else if (event.getSource() == usernameConfirmation) {
            usernameAsString = new String(username.getText().toString());
            window.setScene(chattingScene);
        } else if (event.getSource() == changeUsername) {
            window.setScene(usernameScene);
        } else if (event.getSource() == imageButton) {
            FileChooser openImage = new FileChooser();
            openImage.setTitle("Choose Image");
            openImage.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
            selectedImage = openImage.showOpenDialog(window);
            if(selectedImage != null){
                Image image = new Image(selectedImage.toURI().toString());
                vbox5.getChildren().remove(1);
                imageLabel.setImage(image);
            }
            else{
                Label text = new Label("No file was chosen");
                vbox5.getChildren().add(text);
            }
        } else if (event.getSource() == nudgeButton){
            String nudge = updateChatHistory(">>>>>>> "+usernameAsString + " has nudged you!! <<<<<<<");
            chatHistory.setText(nudge);
        }
    }
    private String updateChatHistory(String newMessage){
        if (newMessage != ""){
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            return (chatHistory.getText()+"\n"+"\t"+timeStamp+"\t  "+ usernameAsString+":\t"+"\t"+newMessage);
        }
        else
            return (chatHistory.getText()+"\n");
    }
}
