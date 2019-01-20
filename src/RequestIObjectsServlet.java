import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/api/spxtags")
public class RequestIObjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter printWriter = resp.getWriter();

        String pathSheme = System.getenv().get("SPF_SCHEMA");

        if (pathSheme == null) {
            printWriter.write("{\"Error\" : \"Не задана переменная окружения SPF_SCHEMA\"}");
        } else {
            Path startPath = Paths.get(pathSheme);
            FileFindVisitor visitor = new FileFindVisitor("glob:*.xml");

            try {
                Files.walkFileTree(startPath, visitor);
            } catch (IOException e) {
                e.printStackTrace();
            }

            IObjectsParser parser = new IObjectsParser();
            JsonConverter iObjJson = new JsonConverter();

            try {
                printWriter.write(iObjJson.convertIObjectsToJson(parser.startParsing(visitor.getFoundXmlFiles())));
            } catch (SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }
}







