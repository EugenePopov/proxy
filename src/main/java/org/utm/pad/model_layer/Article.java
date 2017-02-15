package org.utm.pad.model_layer;


public class Article {

    private String head;
    private String author;
    private String content;
    private String date;
    private String link;
    private int id;


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setDate(String date){ this.date = date; }

    public void setId(int id){
        this.id = id;
    }

    public void setLink(String link){this.link = link; }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getHead() {
        return head;
    }

    public String getDate() {
        return date;
    }

    public String getLink(){
        return link;
    }

    public int getId(){
        return id;
    }

    public String toXml() {
        return "\t<article>\n" +
                "\t\t<id>"+this.getId()+"</id>\n" +
                "\t\t<head>"+this.getHead()+"</head>\n" +
                "\t\t<author>"+this.getAuthor()+"</author>\n" +
                "\t\t<date>"+this.getDate()+"</date>\n" +
                "\t\t<content>"+this.getContent()+"</content>\n" +
                "\t\t<link>" + this.getLink() + "</link>\n" +
                "\t</article>" + "\n\n";
    }

    public String toJson() {
        return "\n{\n" +
                "\t\"article\": {\n" +
                "\t\t\"id\":\"" + this.getId() + "\",\n" +
                "\t\t\"head\":\"" + this.getHead() + "\",\n" +
                "\t\t\"author\":\"" + this.getAuthor() + "\",\n" +
                "\t\t\"date\":\"" + this.getDate() + "\",\n" +
                "\t\t\"content\":\"" +this.getContent() + "\",\n" +
                "\t\t\"link\":\"" + this.getLink() + "\"\n" +
                "\t}\n" +
                "}\n";
    }
}
