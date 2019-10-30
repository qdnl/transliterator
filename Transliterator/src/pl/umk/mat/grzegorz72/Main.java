package pl.umk.mat.grzegorz72;

import javafx.event.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JFrame {


    private static String convertToLatin(String inputText) {
        StringBuilder outputText = new StringBuilder();
        char previousLetter, currentLetter = ' ';
        String convertedLetter = ""; //wynik konwersji nie musi być jedną literą
        String standardLettersSetCyr = "абвгджзийклмнопрстуфхцчшщъыьэАБВГДЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭ";
        String standardLettersSetLat = "abvgdžzijklmnoprstufhcčšșūyīeABVGDŽZIJKLMNOPRSTUFHCČŠȘŪYĪE";
        String iotifiedLettersSetCyr = "еёюяЕЁЮЯ";
        String[] iotifiedLettersSetLatVarJ = {"je", "jo", "ju", "ja", "Je", "Jo", "Ju", "Ja"};
        String iotifiedLettersSetLatVar = "èòùàÈÒÙÀ";
        String iotifiedLettersTest = " аиоуъьэеёюяАИОУЪЬЭЕЁЮЯ";
        int readLetters = 0;
        while (readLetters < inputText.length()) {
            if (readLetters == 0) {//przy wczytaniu pierwszego znaku
                previousLetter = ' ';
            } else {//przy wczytaniu kolejnych znaków
                previousLetter = currentLetter;
            }
            currentLetter = inputText.charAt(readLetters);

            /* konwersja liter */
            if (iotifiedLettersSetCyr.indexOf(currentLetter) >= 0) {
                if (iotifiedLettersTest.indexOf(previousLetter) >= 0) {
                    convertedLetter = iotifiedLettersSetLatVarJ[iotifiedLettersSetCyr.indexOf(currentLetter)];
                }
                else {
                    convertedLetter = Character.toString(iotifiedLettersSetLatVar.charAt(iotifiedLettersSetCyr.indexOf(currentLetter)));
                }
            }
            else {
                if (standardLettersSetCyr.indexOf(currentLetter) >= 0) {
                    convertedLetter = Character.toString(standardLettersSetLat.charAt(standardLettersSetCyr.indexOf(currentLetter)));
                } else {
                    convertedLetter = Character.toString(currentLetter);
                }
            }
            outputText.append(convertedLetter); //systematyczne dopisywanie kolejnych znaków do wyniku
            readLetters++;
        }
        return outputText.toString();
    }

    private String temp_inputText;
    private String temp_outputText;
    private ArrayList<String> inputText = new ArrayList<>();
    private ArrayList<String> outputText = new ArrayList<>();

    private JLabel inputDesc;
    private JTextArea input;
    private JLabel outputDesc;
    private JTextArea output;
    private JScrollPane inputScroll;
    private JScrollPane outputScroll;
    private JButton convertButton;
    private JButton clearButton;

    private void buildWindow() {
        inputDesc = new JLabel();
        input = new JTextArea();
        outputDesc = new JLabel();
        output = new JTextArea();
        inputScroll = new JScrollPane();
        outputScroll = new JScrollPane();
        convertButton = new JButton();
        clearButton = new JButton();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Транслитerator");
        setName("convertWindow");
        setResizable(false);

        input.setColumns(35);
        input.setRows(15);
        input.setLineWrap(true);

        output.setColumns(35);
        output.setRows(15);
        output.setEditable(false);
        output.setLineWrap(true);
        // dlaczego JTextarea zmienia rozmiar i nie obsługuje zawijania tekstu?

        inputScroll.setViewportView(input);
        outputScroll.setViewportView(output);

        //setPreferredSize(new Dimension(800,950));

        inputDesc.setText("Wprowadź tekst, który chcesz skonwertować:");
        outputDesc.setText("Wynik:");

        convertButton.setText("Konwertuj");
        convertButton.addActionListener(this::convertAction);

        clearButton.setText("Wyczyść");
        clearButton.addActionListener(this::clearAction);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        //tu mamy ustawić elementy
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(inputDesc)
                        .addComponent(inputScroll)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(outputDesc)
                        .addComponent(outputScroll)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(convertButton)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(clearButton)
                                )
                        )
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(inputDesc)
                        .addComponent(outputDesc)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(inputScroll)
                        .addComponent(outputScroll)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(convertButton)
                        .addComponent(clearButton)
                )
        );

        pack();

    }

    public Main() {
        buildWindow();
    }

    private void convertAction(ActionEvent ev) {
        temp_inputText = input.getText();
        String[] inputLines = input.getText().split("\\r?\\n");
        String convertedLine;
        inputText = new ArrayList<>(Arrays.asList(inputLines));
        output.setText("");
        for(String s : inputText) {
            convertedLine = convertToLatin(s);
            output.append(convertedLine + "\n");
        }
    }

    private void clearAction(ActionEvent ev) {
        input.setText("");
        output.setText("");
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if (laf.getName().equals("Nimbus")) {
                UIManager.setLookAndFeel(laf.getClassName());
            }
        }
        UIManager.getInstalledLookAndFeels();
        EventQueue.invokeLater(() -> new Window().setVisible(true));
    }
}
