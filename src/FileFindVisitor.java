import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

class FileFindVisitor extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    private ArrayList<String> foundXmlFiles = new ArrayList();

    public FileFindVisitor(String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher(pattern);
    }

    public ArrayList<String> getFoundXmlFiles() {
        return foundXmlFiles;
    }

    public String getFoundXmlFile(int i) {
        return foundXmlFiles.get(i);
    }

    public int getFoundXmlListSize() {
        return foundXmlFiles.size();
    }

    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
        find(path);
        return FileVisitResult.CONTINUE;
    }

    private void find(Path path) {
        Path name = path.getFileName();
        if (matcher.matches(name)) {
            foundXmlFiles.add(path.toAbsolutePath().toString());
        }
    }
}
