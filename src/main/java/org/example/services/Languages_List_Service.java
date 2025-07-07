package org.example.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.scene.control.ComboBox;
import org.example.models.Language_Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Languages_List_Service {
    private static volatile Languages_List_Service instance;
    private ArrayList <Language_Model> languagesList;
    private Map<String, String> languagesCodesMap;
    private String currentLanguageCode = "en";
    private ResourceBundle bundle = ResourceBundle.getBundle("i18n.message", Locale.of(currentLanguageCode));

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(String languageCode) {
        currentLanguageCode = languageCode;
        ResourceBundle.clearCache();
        bundle = ResourceBundle.getBundle("i18n.message", Locale.of(currentLanguageCode));
    }

    private Languages_List_Service() {
        receiveLanguagesFromJSON();
    }

    public static Languages_List_Service getInstance() {
        if (instance == null) {
            synchronized (Languages_List_Service.class) {
                if (instance == null) {
                    instance = new Languages_List_Service();
                }
            }
        }
        return instance;
    }

    public boolean getLanguageCode(String language) {
        String languageCode = languagesCodesMap.get(language);
        if (languageCode != null) {
            setBundle(languageCode);
            return true;
        } else {

            return false;
        }
    }

    public void receiveLanguagesFromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream;
        inputStream = getClass().getResourceAsStream("/languages.json");

        if(inputStream == null) {
            throw new IllegalStateException("Resource '/languages.json' not found on classpath");
        }

        try {
            JsonNode root = mapper.readTree(inputStream);
            JsonNode langsNode = root.path("languages");

            List<Language_Model> list = mapper.convertValue(
                    langsNode,
                    new TypeReference<List<Language_Model>>() {}
            );

            languagesList = new ArrayList<>(list);

            languagesCodesMap = list.stream()
                    .collect(Collectors.toMap(
                            lm -> lm.name,
                            lm -> lm.code
                    ));

        } catch (IOException e) {
            throw new RuntimeException("Failed to read /languages.json", e);
        }
    }

    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(entry.getValue(), value)) {
                return entry.getKey(); // Повертає перший знайдений ключ
            }
        }
        return null; // Якщо ніщо не знайдено
    }


    public void getLanguagesToComboBox (ComboBox<String> languagesComboBox) {
        for (Language_Model language : languagesList) {
            languagesComboBox.getItems().add(language.name);
        }
    }

    public void getCurrentLanguageName(ComboBox<String> languagesComboBox) {
        String currentLanguageName = getKeyByValue(languagesCodesMap, currentLanguageCode);
        languagesComboBox.setValue(currentLanguageName);
    }

}