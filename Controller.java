package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Created by pc on 22/03/16.
*/

public class Controller implements ActionListener {
    //static String text_result = "";
    Model model = new Model();

    public void actionPerformed(ActionEvent event)
    {
        String text = View.textArea1.getText();
        String method = View.comboBox.getSelectedItem().toString();
        String key_string = View.textField.getText();
        int key_int = 0;
        String text_after_converting = "";


        if (method.equals("Шифр Цезаря(шифрование)"))
        {
            if (!key_string.equals("Ключ"))
            {
                //text_result = text;
                key_int = Integer.parseInt(key_string);
                text_after_converting = model.Caesar(text, key_int, false);
            }
            else
            {
                text_after_converting = "Введите ключ шифрования!";
            }
        }
        else if (method.equals("Шифр Цезаря(дешифрование)"))
        {
            if (!key_string.equals("Ключ"))
            {
                key_int = Integer.parseInt(key_string);
                text_after_converting = model.Caesar(text, key_int, true);
            }
            else if (key_string.equals("Ключ"))
            {
                //text_after_converting = text_result;
                text_after_converting = "Введите ключ шифрования!";
            }

        }
        else if (method.equals("Шифр Виженера"))
        {
            if (!key_string.equals("Ключ"))
            {
                //text_result = text;
                text_after_converting = model.Vigenere(text, key_string, false);
            }
            else if (key_string.equals("Ключ"))
            {
                text_after_converting = "Введите ключ шифрования!";
            }

        }
        /*else if (method.equals("Атака Бэббиджа(метод Касиски)"))
        {
            if (!key_string.equals("Ключ"))
            {
                text_after_converting = model.Vigenere(text, key_string, true);
            }
            else if (key_string.equals("Ключ"))
            {
                text_after_converting = text_result;
            }

        }*/
        else if (method.equals("Rot13(шифрование)"))
        {
            text_after_converting = model.Rot13(text, false);
        }
        else if (method.equals("Rot13(дешифрование)"))
        {
            text_after_converting = model.Rot13(text, true);
        }
        else if (method.equals("Шифр Атбаш(шифрование)"))
        {
            text_after_converting = model.Atbash(text);
        }
        else if (method.equals("Шифр Атбаш(дешифрование)"))
        {
            text_after_converting = model.Atbash(text);
        }
        else if (method.equals("Семантический анализ текста"))
        {
            text_after_converting = model.Histogram(text);
        }
        View.textField.setText("Ключ");
        View.textArea2.setText(text_after_converting);      //View1


        /*String address = View.textField2.getText();           //VIEW 2
        if (!address.equals("Адрес"))
        {
            model.writeFile(address, text_after_converting);
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("notepad " + address);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            View.textField2.setText("C:\\Users\\pc\\Desktop\\TZI-encrypt.txt");*/
        //C:\Users\pc\Desktop\TZI-encrypt.txt
    }
}
