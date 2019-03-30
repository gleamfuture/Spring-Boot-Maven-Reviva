package reviva.data;

import org.json.JSONObject;
import org.springframework.util.ResourceUtils;
import reviva.data.entity.Header;
import reviva.data.entity.Footer;

import java.io.*;

public class DataService {

    private static final String HEADER_CH = "classpath:json/ch/header.json";
    private static final String HEADER_EN = "classpath:json/en/header.json";
    private static final String FOOTER_CH = "classpath:json/ch/footer.json";
    private static final String FOOTER_EN = "classpath:json/en/footer.json";
    private static final String CONTENT_CH = "classpath:json/ch/content.json";
    private static final String CONTENT_EN = "classpath:json/en/content.json";

    public Header getHeader(String lang)
    {
        try {
            InputStream in;

            if (lang.equals("ch")) {
                in = ResourceUtils.getURL(HEADER_CH).openStream();
            } else {
                in = ResourceUtils.getURL(HEADER_EN).openStream();
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            final JSONObject obj = new JSONObject(responseStrBuilder.toString());
            JSONObject navbar = obj.getJSONObject("navbar");

            Header header = new Header();
            header.t1 = navbar.getString("t1");
            header.t2 = navbar.getString("t2");
            header.t3 = navbar.getString("t3");
            header.t4 = navbar.getString("t4");
            header.blog = navbar.getString("blog");

            return header;

        } catch (IOException e) {
            System.out.println("Cant parse header");
        }

        return null;
    }

    public JSONObject getContent(String lang) {
        try {
            InputStream in;
            if (lang.equals("ch")) {
                in = ResourceUtils.getURL(CONTENT_CH).openStream();
            } else {
                in = ResourceUtils.getURL(CONTENT_EN).openStream();
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            final JSONObject obj = new JSONObject(responseStrBuilder.toString());

            return obj;
        } catch (IOException e) {
            System.out.println("Cant parse header");
        }

        return null;
    }

    public Footer getFooter(String lang) {
        try {
            InputStream in;

            if (lang.equals("ch")) {
                in = ResourceUtils.getURL(FOOTER_CH).openStream();
            } else {
                in = ResourceUtils.getURL(FOOTER_EN).openStream();
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            final JSONObject obj = new JSONObject(responseStrBuilder.toString());
            JSONObject footer_obj = obj.getJSONObject("footer");

            Footer footer = new Footer();

            footer.s1 = footer_obj.getString("s1");
            footer.enterEmail = footer_obj.getString("enterEmail");
            footer.priv = footer_obj.getString("priv");
            footer.term = footer_obj.getString("term");
            footer.q1 = footer_obj.getString("q1");
            footer.q2 = footer_obj.getString("q2");
            footer.q3 = footer_obj.getString("q3");
            footer.q4 = footer_obj.getString("q4");
            footer.q5 = footer_obj.getString("q5");
            footer.q6 = footer_obj.getString("q6");
            footer.email = footer_obj.getString("email");
            footer.copy = footer_obj.getString("copy");

            return footer;

        } catch (IOException e) {
            System.out.println("Cant parse header");
        }

        return null;
    }
}
