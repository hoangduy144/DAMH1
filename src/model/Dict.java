package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class Dict {
	///////////////Tham khảo trên Internet nhưng tự code lại theo tìm hiểu của bản thân////////////////
    private HashMap<String, LinkedList<String>> dictionary;
    private LinkedList<String> historyList;

    public Dict() {
        dictionary = new HashMap<>();
        historyList = new LinkedList<>();

        loadMap(dictionary, "slang.txt");
        loadLinkedList(historyList, "history.txt");
    }

    private void loadMap(Map<String, LinkedList<String>> map, String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)))) {
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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
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
    }

    public void addNew(String slang, String definition) {
        LinkedList<String> definitions = new LinkedList<>();
        definitions.add(definition);
        dictionary.put(slang, definitions);
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
                return true;
            }
        }
        return false;
    }

    public void deleteSlang(String slang) {
        dictionary.remove(slang);
    }

    ///////////////Tham khảo trên Internet nhưng tự code lại theo tìm hiểu của bản thân////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public String randomSlang() {
        Random random = new Random();
        ArrayList<String> slangs = new ArrayList<>(dictionary.keySet());
        return slangs.get(random.nextInt(slangs.size()));
    }

    ///////////////Tham khảo trên Internet nhưng tự code lại theo tìm hiểu của bản thân////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public void reset() {
        dictionary.clear();
        historyList.clear();
        loadMap(dictionary, "slang.txt");
    }
}
