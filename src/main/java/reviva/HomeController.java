package reviva;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import reviva.data.DataService;
import reviva.data.entity.*;

import java.util.ArrayList;

@Controller
public class HomeController {

    DataService dataService = new DataService();
    String lang, langDisplay, pageTitle;

    @GetMapping("/{lang}")
    public String home (@PathVariable("lang") String lang, Model model) {
        if (lang.equals("ch")) {
            langDisplay = "中文";
            pageTitle = "主頁";
        } else if (lang.equals("en")){
            lang = "en";
            langDisplay = "en";
            pageTitle = "Home";
        } else {
            lang = "en";
            langDisplay = "en";
            model.addAttribute("lang", lang);
            model.addAttribute("langDisplay", langDisplay);
            Header header = dataService.getHeader(lang);
            model.addAttribute("header", header);

            Footer footer = dataService.getFooter(lang);
            model.addAttribute("footer", footer);

            return "404";
        }

        model.addAttribute("lang", lang);
        model.addAttribute("langDisplay", langDisplay);
        model.addAttribute("pageTitle", pageTitle);

        Header header = dataService.getHeader(lang);
        model.addAttribute("header", header);

        Footer footer = dataService.getFooter(lang);
        model.addAttribute("footer", footer);

        JSONObject obj = dataService.getContent(lang);
        JSONObject home = obj.getJSONObject("home");
        JSONArray slides_arr = home.getJSONArray("slides");

        ArrayList<Slide> slides = new ArrayList<>();
        for (int i = 0; i < slides_arr.length(); i ++) {
            Slide sli = new Slide();
            JSONObject sli_obj = slides_arr.getJSONObject(i);
            sli.text = sli_obj.getString("text");
            sli.img = sli_obj.getString("img");
            slides.add(sli);
        }

        model.addAttribute("slides", slides);
        model.addAttribute("counter", 1);
        model.addAttribute("leng", slides.size());

        model.addAttribute("title1", home.getString("title1"));
        model.addAttribute("text1", home.getString("text1"));
        model.addAttribute("linktext", home.getString("linktext"));
        model.addAttribute("for", home.getString("for"));
        model.addAttribute("rest1", home.getString("rest1"));
        model.addAttribute("link2", home.getString("link2"));
        model.addAttribute("rest2", home.getString("rest2"));
        model.addAttribute("rest3", home.getString("rest3"));
        model.addAttribute("title2", home.getString("title2"));
        model.addAttribute("big", home.getString("big"));

        JSONArray concerns_arr = obj.getJSONArray("concerns");
        ArrayList<Concern> concerns = new ArrayList<>();

        for (int i = 0; i < concerns_arr.length(); i ++) {
            Concern crn = new Concern();
            JSONObject crn_obj = concerns_arr.getJSONObject(i);

            crn.name = crn_obj.getString("name");
            crn.cLink = crn_obj.getString("cLink");
            crn.imgUrl = crn_obj.getString("imgUrl");
            concerns.add(crn);
        }

        model.addAttribute("concerns", concerns);

        return "home/home";
    }

    @GetMapping("/")
    public String home_en(Model model) {
        lang = "en";
        langDisplay = "en";
        pageTitle = "Home";

        model.addAttribute("lang", lang);
        model.addAttribute("langDisplay", langDisplay);
        model.addAttribute("pageTitle", pageTitle);

        Header header = dataService.getHeader(lang);
        model.addAttribute("header", header);

        Footer footer = dataService.getFooter(lang);
        model.addAttribute("footer", footer);

        JSONObject obj = dataService.getContent(lang);
        JSONObject home = obj.getJSONObject("home");
        JSONArray slides_arr = home.getJSONArray("slides");

        ArrayList<Slide> slides = new ArrayList<>();
        for (int i = 0; i < slides_arr.length(); i ++) {
            Slide sli = new Slide();
            JSONObject sli_obj = slides_arr.getJSONObject(i);
            sli.text = sli_obj.getString("text");
            sli.img = sli_obj.getString("img");
            slides.add(sli);
        }

        model.addAttribute("slides", slides);
        model.addAttribute("counter", 1);
        model.addAttribute("leng", slides.size());

        model.addAttribute("title1", home.getString("title1"));
        model.addAttribute("text1", home.getString("text1"));
        model.addAttribute("linktext", home.getString("linktext"));
        model.addAttribute("for", home.getString("for"));
        model.addAttribute("rest1", home.getString("rest1"));
        model.addAttribute("link2", home.getString("link2"));
        model.addAttribute("rest2", home.getString("rest2"));
        model.addAttribute("rest3", home.getString("rest3"));
        model.addAttribute("title2", home.getString("title2"));
        model.addAttribute("big", home.getString("big"));

        JSONArray concerns_arr = obj.getJSONArray("concerns");
        ArrayList<Concern> concerns = new ArrayList<>();

        for (int i = 0; i < concerns_arr.length(); i ++) {
            Concern crn = new Concern();
            JSONObject crn_obj = concerns_arr.getJSONObject(i);

            crn.name = crn_obj.getString("name");
            crn.cLink = crn_obj.getString("cLink");
            crn.imgUrl = crn_obj.getString("imgUrl");
            concerns.add(crn);
        }

        model.addAttribute("concerns", concerns);

        return "home/home";
    }
}