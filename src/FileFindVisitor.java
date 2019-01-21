import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

class FileFindVisitor extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    private List<String> foundXmlFiles = new ArrayList<>();

    public FileFindVisitor(String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher(pattern);
    }

    public List<String> getFoundXmlFiles() {
        return foundXmlFiles;
    }

    public String getFoundXmlFile(int i) {
        return foundXmlFiles.get(i);
    }

    public int getFoundXmlListSize() {
        return foundXmlFiles.size();
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
        Path name = path.getFileName();
        if (matcher.matches(name)) {
            foundXmlFiles.add(path.toAbsolutePath().toString());
        }
        return FileVisitResult.CONTINUE;
    }

    public List findXmls (Path path, FileFindVisitor ffVisitor){
        try {
            Files.walkFileTree(path, ffVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foundXmlFiles;
    }
}
