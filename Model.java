package com.company;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.System.exit;

/**
 * Created by pc on 22/03/16.
 */
public class Model {
    //===============================МЕТОД АТБАШ===================================================

    public String Atbash(String text)
    {
        char textBefore[] = text.toCharArray();
        int length = text.length();
        char textAfter[] = new char[length];
        for (int i = 0; i < length; i++)
        {
            char letter = textBefore[i];
            int numBefore = (int) letter;
            if ((numBefore <= 96) && (numBefore >= 0))
            {
                textAfter[i] = letter;
                continue;
            }
            int numAfter = 32;
            numAfter = 122 - numBefore + 97;
            char newLetter = (char) numAfter;

            textAfter[i] = newLetter;
        }
        String newText = new String(textAfter);
        return newText;
    }

    //===============================МЕТОД ROT13===================================================

    public String Rot13(String text, boolean isTextEncrypted)
    {
        String newText = "";
        if (!isTextEncrypted)
        {
            newText = Caesar(text, 13, false);
        }
        if (isTextEncrypted)
        {
            newText = Caesar(text, 13, true);
        }
        return newText;
    }

    //===============================МЕТОДЫ ЦЕЗАРЯ И ВИЖЕНЕРА===================================================

    public String Caesar(String text, int key, boolean isTextEncrypted)
    {
        //String text = readFile(nameOfFile);
        //System.out.println(text);
        char textBefore[] = text.toCharArray();
        int length = text.length();
        char textAfter[] = new char[length];
        for (int i = 0; i < length; i++)
        {
            char letter = textBefore[i];
            int numBefore = (int) letter;
            if ((numBefore <= 32) && (numBefore >= 0))
            {
                textAfter[i] = letter;
                continue;
            }
            int numAfter = 32;
            if (!isTextEncrypted)
                numAfter = (numBefore - 33 + key)%90 + 33;
            if (isTextEncrypted)
                numAfter = (numBefore - 33 + 90 - key)%90 + 33;
            char newLetter = (char) numAfter;

            textAfter[i] = newLetter;
        }
        String newText = new String(textAfter);
        //System.out.println("Text after converting: " + newText);
        //writeFile(fileResult, newText);
        return newText;
    }

    //90 - расширенный охват ascii символов, начиная с !(33) и заканчивая z(122).
    //В таком случае формула шифрования будет: numAfter = (numBefore - 33 + keyNum)%90 + 33;
    //Формула дешифрования будет: numAfter = (numBefore - 33 + 90 - keyNum)%90 + 33;

    //Упрощеный охват ascii сиволов - только алфавит от а(97) до z(122)
    //В таком случае формула шифрования будет: numAfter = (numBefore - 97 + keyNum)%26 + 97;
    //Формула дешифрования будет: numAfter = (numBefore - 97 + 26 - keyNum)%26 + 97;
    public String Vigenere(String text, String key, boolean isTextEncrypted)
    {
        //String text = readFile(nameOfFile);
        //System.out.println(text.length());
        char textBefore[] = text.toCharArray();
        char textAfter[] = new char[text.length()];
        char keyArr[] = key.toCharArray();
        int key_counter = 0;

        for (int i = 0; i < text.length(); i++)
        {
            char letter = textBefore[i];
            int numBefore = (int) letter;
            if ((numBefore <= 32) && (numBefore >= 0))
            {
                textAfter[i] = letter;
                continue;
            }
            int numAfter = 32;
            int keyNum = (int) keyArr[(key_counter++)%(key.length())] - 33;     //Тут тоже надо менять нижний порог шифрования(т.е. число 33(!))
            //на число 97(а), например)

            if (!isTextEncrypted)       //Выполняется, если текст не является зашифрованным. Т.е. шифрование
                numAfter = (numBefore - 33 + keyNum)%90 + 33;
            if (isTextEncrypted)        //Выполняется, если текст зашифрован. Т.е. дешифрование
                numAfter = (numBefore - 33 + 90 - keyNum)%90 + 33;
            char newLetter = (char) numAfter;

            textAfter[i] = newLetter;
        }
        String newText = new String(textAfter);
        //System.out.println("Text after converting: " + newText);
        //writeFile(fileResult, newText);
        return newText;
    }

    //===============================МЕТОД КАСИСКИ===================================================

    public void NSD2(String nameOfFile)
    {
        Map<String, ArrayList<Integer>> map1 = SubstringEntrance(nameOfFile);
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        for(String e : map1.keySet())
        {
            ArrayList<Integer> list1 = map1.get(e);
            ArrayList<Integer> list2 = new ArrayList<Integer>();
            for (int i = 0; i < list1.size()-1; i++)
            {
                int diff = list1.get(i+1) - list1.get(i);
                list2.add(diff);
            }
            for (Integer num : list2)           //ТУТ ВЫВОДИТ СПИСОК НСД ДЛЯ ВХОЖДЕНИЙ ОТРЕЗКОВ
                System.out.print(num + " ");
            System.out.println();
            if (list2.size() == 1)
            {
                map2.put(list2.get(0), 1);
                System.out.println("Всего 1 разница между числами");
            }
            for (int i = 0; i < list2.size()-1; i++)
            {
                int nsd = Evklid_algo(list2.get(i), list2.get(i+1));
                System.out.println("Nsd = " + nsd);
                if (nsd == 1)
                {
                    System.out.println("nsd == 1");
                    continue;
                }
                boolean is_e2_in_map2 = false;
                for(Integer e2 : map2.keySet())
                {
                    if (e2 == nsd)
                    {
                        int value = map2.get(e2) + 1;
                        map2.put(e2, value);
                        is_e2_in_map2 = true;
                        System.out.println("nsd == ключу от мап2:" + nsd + " " + e2);
                    }
                }
                if (!is_e2_in_map2)
                {
                    map2.put(nsd, 1);
                    System.out.println("такого нсд в мапе2 еще нет, добавили");
                }
            }
        }
        for(Map.Entry e : map2.entrySet())
        {
            System.out.println(e.getKey()+" "+ e.getValue());
        }
    }


    public void NSD(String nameOfFile)   //Найбільший Спільний Дільник
    {
        Map<String, ArrayList<Integer>> map1 = SubstringEntrance(nameOfFile);
        ArrayList<Integer> list3 = new ArrayList<Integer>();
        for(String e : map1.keySet())
        {
            ArrayList<Integer> list1 = map1.get(e);
            ArrayList<Integer> list4 = new ArrayList<Integer>();
            for (int i = 0; i < list1.size()-1; i++)
            {
                int diff = list1.get(i+1) - list1.get(i);
                list4.add(diff);
            }
            while (list4.size() > 1)
            {
                int nsd = Evklid_algo(list4.get(0), list4.get(1));
                list4.remove(1);
                list4.remove(0);
                list4.add(nsd);
            }
            list3.add(list4.get(0));
        }
        for (Integer num : list3)           //ТУТ ВЫВОДИТ СПИСОК НСД ДЛЯ ВХОЖДЕНИЙ ОТРЕЗКОВ
            System.out.print(num + " ");
        System.out.println();
        while (list3.size() > 100)          //ОСТАВИТЬ В СПИСКЕ НЕ МЕНЬШЕ 100 НСД
        {
            int nsd = Evklid_algo(list3.get(0), list3.get(1));
            list3.remove(1);
            list3.remove(0);
            list3.add(nsd);
        }
        System.out.print("nsd = ");                     //ТУТ ВЫВЕДЕТ НСД ОТ НСД ВСЕХ ВХОЖДЕНИЙ ОТРЕЗКОВ
        for (Integer num : list3)
            System.out.print(num + " ");
    }

    public int Evklid_algo(int a, int b)
    {
        //System.out.println("a is " + a + ", b is " + b);
        while ((a!=0) && (b!=0))
        {
            if (a > b)
                a = a % b;
            else
                b = b % a;
        }
        //System.out.println("their nsd = " + (a + b));
        return a+b;
    }

    public Map<String, ArrayList<Integer>> SubstringEntrance(String nameOfFile)
    {
        Map<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
        String text = readFile(nameOfFile);
        for (int i = 0; i < text.length()-2; i++)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(i);
            String substr = text.substring(i, i+3);
            int j = i;
            while (j < text.length())
            {
                int vhojdenie_podstroki = text.indexOf(substr, j + 1);
                if (vhojdenie_podstroki == -1)
                {
                    break;
                }
                else
                {
                    list.add(vhojdenie_podstroki);
                    j = vhojdenie_podstroki;
                }
            }
            if ((list.size() > 1) && (!map.containsKey(substr)))
                map.put(substr, list);
        }
        for(Map.Entry e : map.entrySet())                 //ТУТ ВЫВОДИТ НАБОР ВХОЖДЕНИЙ ОТРЕЗКОВ ДЛИНОЙ 3 СИМВОЛА
        {
            System.out.println(e.getKey()+" "+ e.getValue());
        }
        //System.out.println(text);
        return map;
    }

    public String Histogram(String text)
    {
        //String text = readFile(nameOfFile);
        Map<Character, Integer> treeMap = new TreeMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++)
        {
            char new_symbol = text.charAt(i);
            if (treeMap.containsKey(new_symbol))
            {
                int count = treeMap.get(new_symbol);
                count++;
                treeMap.put(new_symbol, count);
            }
            else if (!treeMap.containsKey(new_symbol))
            {
                treeMap.put(new_symbol, 1);
            }
        }
        String newText = "";
        for(Map.Entry e : treeMap.entrySet())
        {
            //System.out.println(e.getKey()+" "+ e.getValue());
            newText = newText + e.getKey()+" "+ e.getValue() + "\n";
        }
        return newText;
    }


//===============================ЧТЕНИЕ И ЗАПИСЬ ФАЙЛОВ===================================================

    public String readFile(String file)
    {
        String str = "Null";
        File f=new File(file);
        try
        {
            FileReader reader = new FileReader(f);
            char[] buffer = new char[(int)f.length()];
            // считаем файл полностью
            reader.read(buffer);
            str = new String(buffer);
            //System.out.println(str);
            reader.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
            exit(1);
        }
        return str;
    }

    public void writeFile(String file, String text)
    {
        try
        {
            FileWriter writer = new FileWriter(file, false);
            // запись всей строки
            writer.write(text);
            writer.flush();
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }
}
