package sample;

public class ListChannel {

    private final int id;
    private final String name;
    private final String authorization;
    private final String cookie;
    private final String referer;
    private final String authuser;
    private final String pageid;
    private final String delegatcontext;
    private final String idChanel;


    public ListChannel(int id,
                       String name,
                       String authorization,
                       String cookie,
                       String referer,
                       String authuser,
                       String pageid,
                       String delegatcontext,
                       String idChanel) {

        this.id = id;
        this.name = name;
        this.authorization = authorization;
        this.cookie = cookie;
        this.referer = referer;
        this.authuser = authuser;
        this.pageid = pageid;
        this.delegatcontext = delegatcontext;
        this.idChanel = idChanel;

    }

    public String getName() {
        return name;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getCookie() {
        return cookie;
    }

    public String getReferer() {
        return referer;
    }

    public String getAuthuser() {
        return authuser;
    }

    public String getPageid() {
        return pageid;
    }

    public String getDelegatcontext() {
        return delegatcontext;
    }

    public String getIdChanel() {
        return idChanel;
    }

    public int getId() {
        return id;
    }


}
