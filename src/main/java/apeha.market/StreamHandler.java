package apeha.market;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamHandler {

    public List<Item> fillItemList(Reader inReader) {
        String infoAmount = "";
        LineNumberReader reader = new LineNumberReader(inReader);
        List<Item> list = new ArrayList<Item>();
        String line = null;
        try {
            Pattern pInfo = Pattern
                    .compile("(\\t)|(Цена)|(Син.ст.)|(Стоимость удаления мода)|(закл)|(ограненный)|(Время)|(оличество)|(Игрок)|(урон)|(рочность)|([\\[\\]])");
            Pattern pName = Pattern.compile("(\\(закл\\.\\))|(\\(мод\\.\\))|(\\[)");
            Pattern pPrice = Pattern.compile("Цена:");
            Matcher matcher;
            Item item = null;
            int i = -1;
            while ((line = reader.readLine()) != null) {
                matcher = pName.matcher(line);
                if (matcher.find()) {
                    i++;
                    item = new Item();
                    infoAmount = new String("");
                    list.add(item);
                    list.get(i).setNameFromLine(line);
                }
                matcher = pPrice.matcher(line);
                if (matcher.find()) {
                    list.get(i).setPricesFromLine(line);
                }
                matcher = pInfo.matcher(line);
                if (!matcher.find() && !list.isEmpty()) {
                    list.get(i).addInfo(infoAmount.concat(line) + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (line != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        checkAndDeleteIfDateIsNull(list);
        return checkAndDeleteIfDateIsNull(list);
    }

    private List<Item> checkAndDeleteIfDateIsNull(List<Item> list) {
        int i = 0;
        while (i < list.size()) {
            if ((list.get(i).getName() == null) || (list.get(i).getPrice() == null) || (list.get(i).getPriceBlue() == null))
                list.remove(i).getName();
            else
                i++;
        }
        return list;
    }

    public void saveToFile(List<Item> list, File file) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<Item> it = list.iterator();
        while (it.hasNext()) {
            try {
                writer.write(it.next().toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Item> openAndGetListFromReader(Reader fileRead) {
        List<Item> list = new ArrayList<Item>();
        LineNumberReader reader = new LineNumberReader(fileRead);
        String line = null;
        Item item;
        Scanner scanner;
        try {
            while ((line = reader.readLine()) != null) {
                item = new Item();
                scanner = new Scanner(line).useDelimiter("\\,");
                item.setNameFromLine(scanner.next());
                item.setPricesFromLine(scanner.next());
                item.addInfo(scanner.next().replaceAll("\\|", "\n"));
                list.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getTextFromList(List<Item> list) {
        String text = "";
        if (list.size() != 0) {
            Iterator<Item> it = list.iterator();
            while (it.hasNext()) {
                text = text.concat(it.next().toString() + "\n");
            }
        }
        return text;
    }
}
