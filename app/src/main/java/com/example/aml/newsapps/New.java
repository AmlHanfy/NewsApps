package com.example.aml.newsapps;
public class New
{
    private String webTitle;
    private String sectionName;
    private String webPublicationDate;
    private String webUrl;
    private String authorName;
    public New(String sectionName, String webTitle, String webUrl, String webPublicationDate,String authorName)
    {
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.authorName=authorName;
        this.webUrl = webUrl;
    }
    public String getSectionName()
    {
        return sectionName;
    }
    public String getWebPublicationDate()
    {
        return webPublicationDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getWebUrl()
    {
        return webUrl;
    }public String getWebTitle()
    {
        return webTitle;
    }
}
