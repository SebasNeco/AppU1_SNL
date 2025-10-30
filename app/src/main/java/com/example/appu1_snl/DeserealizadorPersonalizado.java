package com.example.appu1_snl;

import com.example.appu1_snl.model.RptaGeneral;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeserealizadorPersonalizado implements JsonDeserializer<RptaGeneral> {

    @Override
    public RptaGeneral deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        RptaGeneral rptaGeneral = new RptaGeneral();
        rptaGeneral.setCode(jsonObject.get("code").getAsInt());
        rptaGeneral.setMessage(jsonObject.get("message").getAsString());
        if (((JsonObject) json).get("data") instanceof JsonArray){
            JsonElement itemsElement = jsonObject.get("data");
            if (itemsElement != null && itemsElement.isJsonArray()) {
                JsonArray jsonArray = itemsElement.getAsJsonArray();
                List<PersonaEntry> items = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    items.add(context.deserialize(element, PersonaEntry.class));
                }
                rptaGeneral.setData(items);
            }
            return rptaGeneral;
        }
        else{
            return null;
        }
    }
}
