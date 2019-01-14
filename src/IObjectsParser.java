import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class IObjectsParser {
    private List<IObject> iObjects = new ArrayList<>();
    private List<String> linksToXmlFiles = new ArrayList<>();

    public List<IObject> getIObjects() {
        return iObjects;
    }

    public int getIObjectsSize (){
        return iObjects.size();
    }

    public IObject getFoundIObject (int i){
        return iObjects.get(i);
    }

    public void setLinksToXmlFiles(List<String> linksToXmlFiles) {
        this.linksToXmlFiles = linksToXmlFiles;
    }

    public void startParsing() throws IOException, SAXException, ParserConfigurationException {
        for (int i = 0; i < linksToXmlFiles.size(); i++) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(linksToXmlFiles.get(i)));
            NodeList iObjectElements = document.getDocumentElement().getElementsByTagName("IObject");

            for (int j = 0; j < iObjectElements.getLength(); j++) {
                Node iObject = iObjectElements.item(j);
                if (iObject.getParentNode().getNodeName().equals("EnumEnum")) {
                    NamedNodeMap attributes = iObject.getAttributes();
                    iObjects.add(new IObject(attributes.getNamedItem("UID").getNodeValue(), attributes.getNamedItem("Name").getNodeValue()));
                }
            }
        }
    }
}

