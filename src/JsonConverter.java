import javax.json.*;
import java.util.ArrayList;
import java.util.List;

class JsonConverter {
    private List<IObject> iObjectsForJson = new ArrayList<>();
    private String jsonResponseString;

    public void setIObjectsForJson (List<IObject> iObjectsForJson){
        this.iObjectsForJson = iObjectsForJson;
    }

    public String getJsonResponseString (){
        return this.jsonResponseString;
    }

    public String convertIObjectsToJson(List<IObject> iObjects) {

        setIObjectsForJson(iObjects);

        JsonObjectBuilder root = Json.createObjectBuilder();
        JsonArrayBuilder array = Json.createArrayBuilder();

        for (IObject obj:iObjectsForJson) {
            JsonObjectBuilder bilder = Json.createObjectBuilder();
            JsonObject listJson = bilder.add("uid", obj.getUid()).add("name", obj.getName()).build();
            array.add(listJson);
        }
        JsonObject jsonResponse = root.add("response", array).build();
        jsonResponseString = jsonResponse.toString();

        return jsonResponseString;
    }
}
