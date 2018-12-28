class IObject {
    private String uid, name;

    public IObject(String uid, String name) {
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }
}
