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
public class ClinicController {

    DataService dataService = new DataService();
    String lang, langDisplay, pageTitle;

    @GetMapping("/{lang}/our-clinic")
    public String clinic(@PathVariable("lang") String lang, Model model) {
        if (lang.equals("en")) {
            langDisplay = "en";
            pageTitle = "Our Clinic";
        } else {
            langDisplay = "中文";
            pageTitle = "關於我們";
        }

        model.addAttribute("lang", lang);
        model.addAttribute("langDisplay", langDisplay);
        model.addAttribute("pageTitle", pageTitle);

        Header header = dataService.getHeader(lang);
        model.addAttribute("header", header);

        Footer footer = dataService.getFooter(lang);
        model.addAttribute("footer", footer);

        JSONObject obj = dataService.getContent(lang);
        JSONObject clinic = obj.getJSONObject("clinic");
        JSONArray slides_arr = clinic.getJSONArray("slides");

        ArrayList<Slide> slides = new ArrayList<>();
        for (int i = 0; i < slides_arr.length(); i ++) {
            Slide sli = new Slide();
            JSONObject sli_obj = slides_arr.getJSONObject(i);

            sli.title = sli_obj.getString("title");
            sli.text = sli_obj.getString("text");
            sli.img = sli_obj.getString("img");

            slides.add(sli);
        }

        model.addAttribute("slides", slides);
        model.addAttribute("counter", 1);
        model.addAttribute("leng", slides.size());

        model.addAttribute("our", clinic.getString("our"));
        model.addAttribute("facility", clinic.getString("facility"));
        model.addAttribute("dr", clinic.getString("dr"));
        model.addAttribute("henry", clinic.getString("henry"));
        model.addAttribute("title", clinic.getString("title"));
        model.addAttribute("text1", clinic.getString("text1"));
        model.addAttribute("p1", clinic.getString("p1"));
        model.addAttribute("p2", clinic.getString("p2"));
        model.addAttribute("listTitle", clinic.getString("listTitle"));
        model.addAttribute("find", clinic.getString("find"));
        model.addAttribute("link", clinic.getString("link"));

        JSONArray list_arr = clinic.getJSONArray("list");
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < list_arr.length(); i ++) {
            String lst = (String) list_arr.get(i);
            list.add(lst);
        }

        model.addAttribute("list", list);
        return "clinic/clinic";
    }
}