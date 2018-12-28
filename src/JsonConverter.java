import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class JsonConverter {
    private ArrayList<IObject> iObjectsForJson = new ArrayList<>();
    private String jsonResponseString;

    public void setiObjectsForJson (ArrayList<IObject> iObjectsForJson){
        this.iObjectsForJson = iObjectsForJson;
    }

    public String getJsonResponseString (){
        return this.jsonResponseString;
    }

    public void convertIObjectsToJson (){
        JsonObjectBuilder root = Json.createObjectBuilder();
        JsonArrayBuilder array = Json.createArrayBuilder();

        for (IObject obj:iObjectsForJson) {
            JsonObjectBuilder bilder = Json.createObjectBuilder();
            JsonObject listJson = bilder.add("uid", obj.getUid()).add("name", obj.getName()).build();
            array.add(listJson);
        }
        JsonObject jsonResponse = root.add("response", array).build();

        StringWriter sw = new StringWriter();
        JsonReader jr = Json.createReader(new StringReader(jsonResponse.toString()));
        JsonObject jobj = jr.readObject();
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(jobj);
        jsonWriter.close();
        jsonResponseString = sw.toString();
    }
}
