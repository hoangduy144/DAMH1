package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import java.io.*;
import java.util.*;

public class Dict {
    private HashMap<String, LinkedList<String>> dictionary;
    private LinkedList<String> historyList, deleteList;
    private LinkedHashMap<String, LinkedList<String>> modifiedList;

    public Dict() {
        dictionary = new HashMap<>();
        historyList = new LinkedList<>();
        deleteList = new LinkedList<>();
        modifiedList = new LinkedHashMap<>();

        loadMap(dictionary, "slang.txt");
        loadLinkedList(historyList, "history.txt");
        loadLinkedList(deleteList, "delete.txt");

        for (String slang : deleteList) {
            dictionary.remove(slang);
        }

        loadMap(modifiedList, "modified.txt");

        for (Map.Entry<String, LinkedList<String>> entry : modifiedList.entrySet()) {
            String key = entry.getKey();
            LinkedList<String> val = entry.getValue();
            dictionary.put(key, val);
        }
    }

    private void loadMap(Map<String, LinkedList<String>> map, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("`");
                if (parts.length == 2) {
                    String slang = parts[0].trim();
                    String[] definitions = parts[1].split("\\|");
                    LinkedList<String> definitionList = new LinkedList<>(Arrays.asList(definitions));
                    map.put(slang, definitionList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLinkedList(LinkedList<String> list, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLinkedList(LinkedList<String> list, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String item : list) {
                bw.write(item);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveModified() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("modified.txt"))) {
            for (Map.Entry<String, LinkedList<String>> entry : modifiedList.entrySet()) {
                String key = entry.getKey();
                LinkedList<String> val = entry.getValue();
                StringBuilder sb = new StringBuilder();
                for (String definition : val) {
                    sb.append(definition).append("|");
                }
                sb.deleteCharAt(sb.length() - 1);
                bw.write(key + "`" + sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        saveLinkedList(deleteList, "delete.txt");
        saveLinkedList(historyList, "history.txt");
        saveModified();
    }

    public void addHistory(String history) {
        historyList.add(history);
    }

    public LinkedList<String> getHistory() {
        return historyList;
    }

    public LinkedList<String> searchSlang(String slang) {
        return dictionary.get(slang);
    }

    public void addDefinition(String slang, String definition) {
        LinkedList<String> definitions = dictionary.getOrDefault(slang, new LinkedList<>());
        definitions.add(definition);
        dictionary.put(slang, definitions);
        modifiedList.put(slang, definitions);
    }

    public void addNew(String slang, String definition) {
        LinkedList<String> definitions = new LinkedList<>();
        definitions.add(definition);
        dictionary.put(slang, definitions);
        modifiedList.put(slang, definitions);
    }

    public boolean hasSlang(String slang) {
        return dictionary.containsKey(slang);
    }

    public boolean editSlang(String slang, String oldDefinition, String newDefinition) {
        LinkedList<String> definitions = dictionary.get(slang);
        if (definitions != null) {
            int index = definitions.indexOf(oldDefinition);
            if (index != -1) {
                definitions.set(index, newDefinition);
                modifiedList.put(slang, definitions);
                return true;
            }
        }
        return false;
    }

    public void deleteSlang(String slang) {
        deleteList.add(slang);
        dictionary.remove(slang);
    }

    public ArrayList<String> searchDefinition(String definition) {
        ArrayList<String> slangs = new ArrayList<>();
        for (Map.Entry<String, LinkedList<String>> entry : dictionary.entrySet()) {
            String slang = entry.getKey();
            LinkedList<String> definitions = entry.getValue();
            for (String def : definitions) {
                if (def.contains(definition)) {
                    slangs.add(slang);
                    break;
                }
            }
        }
        return slangs;
    }

    public String randomSlang() {
        Random random = new Random();
        ArrayList<String> slangs = new ArrayList<>(dictionary.keySet());
        return slangs.get(random.nextInt(slangs.size()));
    }

    public HashMap<String, LinkedList<String>> slangGame() {
        HashMap<String, LinkedList<String>> game = new HashMap<>();
        Random random = new Random();
        ArrayList<String> slangs = new ArrayList<>(dictionary.keySet());
        while (game.size() < 4) {
            String slang = slangs.get(random.nextInt(slangs.size()));
            game.put(slang, dictionary.get(slang));
        }
        return game;
    }

    public HashMap<String, ArrayList<String>> definitionGame() {
        HashMap<String, ArrayList<String>> game = new HashMap<>();
        Random random = new Random();
        ArrayList<String> slangs = new ArrayList<>(dictionary.keySet());
        while (game.size() < 4) {
            String slang = slangs.get(random.nextInt(slangs.size()));
            LinkedList<String> definitions = dictionary.get(slang);
            String definition = definitions.get(random.nextInt(definitions.size()));
            ArrayList<String> slangsContainingDef = searchDefinition(definition);
            game.put(definition, slangsContainingDef);
        }
        return game;
    }

    public void reset() {
        dictionary.clear();
        modifiedList.clear();
        historyList.clear();
        deleteList.clear();
        loadMap(dictionary, "slang.txt");
        loadMap(modifiedList, "modified.txt");
    }
}
