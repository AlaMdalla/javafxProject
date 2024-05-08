    package Controller;

    import com.jfoenix.controls.JFXButton;
    import com.jfoenix.controls.JFXTextField;
    import emoji.EmojiPicker;
    import javafx.application.Platform;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.control.ScrollPane;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Text;
    import javafx.scene.text.TextFlow;

    import java.awt.*;
    import java.io.*;
    import java.net.Socket;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;

    import javax.sound.sampled.AudioFileFormat;
    import javax.sound.sampled.AudioFormat;
    import javax.sound.sampled.AudioInputStream;
    import javax.sound.sampled.AudioSystem;
    import javax.sound.sampled.DataLine;
    import javax.sound.sampled.LineUnavailableException;
    import javax.sound.sampled.TargetDataLine;
    import javax.sound.sampled.*;
    import javax.sound.sampled.*;
    import javafx.scene.control.Alert;
    import javafx.scene.control.ButtonType;
    import javafx.scene.control.Alert;
    import com.jfoenix.controls.JFXTextField;

    public class ClientFormController {
        public AnchorPane pane;
        public ScrollPane scrollPane;
        public VBox vBox;
        public JFXTextField txtMsg;
        public Text txtLabel;
        private volatile boolean isRecording = false;

        public JFXButton emojiButton;

        private Socket socket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;
        private String clientName = "Client";

        @FXML
        public JFXTextField txtSearch;
        public void initialize() {
            txtLabel.setText(clientName);

            new Thread(() -> {
                try {
                    socket = new Socket("localhost", 3001);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    System.out.println("Client connected");
                    ServerFormController.receiveMessage(clientName + " joined.");

                    // Fetch old messages from the database
                    fetchOldMessages();

                    while (socket.isConnected()) {
                        String receivingMsg = dataInputStream.readUTF();
                        receiveMessage(receivingMsg, this.vBox);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            this.vBox.heightProperty().addListener((observableValue, oldValue, newValue) -> scrollPane.setVvalue((Double) newValue));

            emoji();
        }

        private void fetchOldMessages() {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ahc", "root", "");
                String sql = "SELECT * FROM messages";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String senderName = resultSet.getString("sender_name");
                    String messageContent = resultSet.getString("message_content");
                    String timestamp = resultSet.getString("timestamp");

                    String fullMessage = senderName + "-" + messageContent;
                    // Display old messages
                    receiveMessage(fullMessage, vBox);
                }

                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void recordButtonPressed(ActionEvent event) {
            // Implement logic to start recording voice note
            startRecording();
        }
        private void startRecording() {
            try {
                // Set up audio format
                AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false); // 44100 Hz, 16 bit, stereo

                // Get target data line for recording
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Line not supported");
                    return;
                }

                TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();

                // Set up a thread to capture audio continuously
                Thread thread = new Thread(() -> {
                    AudioInputStream ais = new AudioInputStream(line);

                    // Define the file to save the recorded audio
                    File file = new File("recorded_audio.wav");

                    // Write the recorded audio to the file
                    try {
                        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void searchMessages(javafx.scene.input.KeyEvent event) {
            String searchText = txtSearch.getText().toLowerCase().trim();

            // Iterate through the messages in the vBox
            for (javafx.scene.Node node : vBox.getChildren()) {
                if (node instanceof HBox) {
                    HBox messageBox = (HBox) node;
                    boolean messageContainsSearchText = false;

                    // Iterate through the children of the HBox
                    for (javafx.scene.Node childNode : messageBox.getChildren()) {
                        if (childNode instanceof Text) {
                            Text messageText = (Text) childNode;
                            String text = messageText.getText().toLowerCase();

                            // Check if the text contains the search text
                            if (text.contains(searchText)) {
                                messageContainsSearchText = true;
                                // Show the individual Text node
                                messageText.setVisible(true);
                            } else {
                                // Hide the individual Text node
                                messageText.setVisible(false);
                            }
                        }
                    }

                    // Show or hide the entire HBox based on search results
                    messageBox.setVisible(messageContainsSearchText);
                }
            }
        }


        public void shutdown() {
            // cleanup code here...
            ServerFormController.receiveMessage(clientName+" left.");
        }

        private void emoji() {
            // Create the EmojiPicker
            EmojiPicker emojiPicker = new EmojiPicker();

            VBox vBox = new VBox(emojiPicker);
            vBox.setPrefSize(150, 300);
            vBox.setLayoutX(400);
            vBox.setLayoutY(175);
            vBox.setStyle("-fx-font-size: 30");

            pane.getChildren().add(vBox);

            // Set the emoji picker as hidden initially
            emojiPicker.setVisible(false);

            // Show the emoji picker when the button is clicked
            emojiButton.setOnAction(event -> {
                if (emojiPicker.isVisible()){
                    emojiPicker.setVisible(false);
                } else {
                    emojiPicker.setVisible(true);
                }
            });

            // Set the selected emoji from the picker to the text field
            emojiPicker.getEmojiListView().setOnMouseClicked(event -> {
                String selectedEmoji = emojiPicker.getEmojiListView().getSelectionModel().getSelectedItem();
                if (selectedEmoji != null) {
                    txtMsg.setText(txtMsg.getText() + selectedEmoji);
                }
                emojiPicker.setVisible(false);
            });
        }

        @FXML
        public void txtMsgOnAction(ActionEvent actionEvent) {
            sendButtonOnAction(actionEvent);
        }

        @FXML
        public void sendButtonOnAction(ActionEvent actionEvent) {
            sendMsg(txtMsg.getText());
        }

        @FXML
        private void sendMsg(String msgToSend) {
            if (!msgToSend.isEmpty()) {
                saveMessageToDatabase(clientName, msgToSend); // Save message to database

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 0, 10));

                Text text = new Text(msgToSend);
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(1, 1, 1));

                hBox.getChildren().add(textFlow);

                HBox hBoxTime = new HBox();
                hBoxTime.setAlignment(Pos.CENTER_RIGHT);
                hBoxTime.setPadding(new Insets(0, 5, 5, 10));
                String stringTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                Text time = new Text(stringTime);
                time.setStyle("-fx-font-size: 8");

                hBoxTime.getChildren().add(time);

                vBox.getChildren().add(hBox);
                vBox.getChildren().add(hBoxTime);

                try {
                    dataOutputStream.writeUTF(clientName + "-" + msgToSend);
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                txtMsg.clear();
            }
        }

        @FXML
        private void saveMessageToDatabase(String senderName, String messageContent) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ahc", "root", "");

                String sql = "INSERT INTO messages (sender_name, message_content, timestamp) VALUES (?, ?, NOW())";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, senderName);
                preparedStatement.setString(2, messageContent);

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @FXML
        public static void receiveMessage(String msg, VBox vBox) {
            if (msg.matches(".*\\.(png|jpe?g|gif)$")){
                HBox hBoxName = new HBox();
                hBoxName.setAlignment(Pos.CENTER_LEFT);
                Text textName = new Text(msg.split("[-]")[0]);
                TextFlow textFlowName = new TextFlow(textName);
                hBoxName.getChildren().add(textFlowName);

                Image image = new Image(msg.split("[-]")[1]);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(200);
                imageView.setFitWidth(200);
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5,5,5,10));
                hBox.getChildren().add(imageView);
                Platform.runLater(() -> {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBox);
                });

            } else {
                String name = msg.split("-")[0];
                String msgFromServer = msg.split("-")[1];

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5,5,5,10));

                HBox hBoxName = new HBox();
                hBoxName.setAlignment(Pos.CENTER_LEFT);
                Text textName = new Text(name);
                TextFlow textFlowName = new TextFlow(textName);
                hBoxName.getChildren().add(textFlowName);

                Text text = new Text(msgFromServer);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5,10,5,10));
                text.setFill(Color.color(0,0,0));

                hBox.getChildren().add(textFlow);

                Platform.runLater(() -> {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBox);
                });
            }

        }

        public void attachedButtonOnAction(ActionEvent actionEvent) {
            FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            String file = dialog.getDirectory() + dialog.getFile();
            dialog.dispose();
            sendImage(file);
            System.out.println(file + " chosen.");

        }

        private void sendImage(String msgToSend) {
            Image image = new Image(msgToSend);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(5,5,5,10));
            hBox.getChildren().add(imageView);
            hBox.setAlignment(Pos.CENTER_RIGHT);

            vBox.getChildren().add(hBox);

            try {
                dataOutputStream.writeUTF(clientName + "-" +msgToSend);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void setClientName(String name) {
            clientName = name;
        }
    }
