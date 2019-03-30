package reviva;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reviva.data.DataService;
import org.springframework.stereotype.Controller;
import reviva.data.entity.*;

import java.util.ArrayList;

@Controller
public class ConcernController {
    DataService dataService = new DataService();
    String lang, langDisplay, pageTitle;

    @GetMapping("/{lang}/concerns/{clink}")
    public String showConcern(@PathVariable("lang") String lang, @PathVariable("clink") String clink, Model model) {
        Header header = dataService.getHeader(lang);
        model.addAttribute("header", header);

        Footer footer = dataService.getFooter(lang);
        model.addAttribute("footer", footer);

        JSONObject obj = dataService.getContent(lang);
        JSONObject conc = obj.getJSONObject("conc");

        model.addAttribute("conc_treat", conc.getString("treat"));
        model.addAttribute("conc_link", conc.getString("link"));
        model.addAttribute("conc_quest", conc.getString("quest"));
        model.addAttribute("conc_contact", conc.getString("contact"));
        model.addAttribute("conc_find", conc.getString("find"));

        JSONArray concerns_arr = obj.getJSONArray("concerns");
        ArrayList<Concern> concerns = new ArrayList<>();

        ArrayList<Treatment> array1 = new ArrayList<>();
        ArrayList<Treatment> array2 = new ArrayList<>();
        ArrayList<Treatment> array3 = new ArrayList<>();
        ArrayList<Treatment> array4 = new ArrayList<>();

        JSONObject use = null;
        for (int i = 0; i < concerns_arr.length(); i ++) {
            JSONObject crn_obj = concerns_arr.getJSONObject(i);
            if (crn_obj.getString("cLink").equals(clink)) {
                use = crn_obj;
                break;
            }
        }

        String use_name, use_description;
        ArrayList<Treatment> use_treatments = new ArrayList<>();

        if (use == null) {
            return "404";
        } else {
            use_name = use.getString("name");
            use_description = use.getString("description");
            JSONArray treats_arr = use.getJSONArray("treatments");

            for (int i = 0; i < treats_arr.length(); i ++) {
                JSONObject treat_obj = treats_arr.getJSONObject(i);
                Treatment treatment = new Treatment();
                treatment.name = treat_obj.getString("name");
                treatment.shortDescription = treat_obj.getString("shortDescription");
                treatment.link = treat_obj.getString("link");
                use_treatments.add(treatment);
            }

            model.addAttribute("use_name", use_name);
            model.addAttribute("use_description", use_description);
            model.addAttribute("use_treatments", use_treatments);
        }

        if (lang.equals("en")) {
            String uname = use_name;
            pageTitle = "";
            String[] splited = uname.split("\\s+");
            for (int i = 0; i < splited.length; i ++) {
                pageTitle = pageTitle + splited[i].toUpperCase().charAt(0) + splited[i].substring(1) + " ";
            }
            pageTitle = pageTitle + "- " + "Treatments";
            langDisplay = "en";
        } else {
            pageTitle = use_name + " - " + "療程";
            langDisplay = "中文";
        }

        model.addAttribute("lang", lang);
        model.addAttribute("langDisplay", langDisplay);
        model.addAttribute("pageTitle", pageTitle);

        for (int i = 0; i < concerns_arr.length(); i ++) {
            Concern crn = new Concern();
            JSONObject crn_obj = concerns_arr.getJSONObject(i);

            JSONArray treats_arr = crn_obj.getJSONArray("treatments");
            for (int j = 0; j < treats_arr.length(); j ++) {
                JSONObject treat_obj = treats_arr.getJSONObject(j);

                Treatment treatment = new Treatment();
                treatment.name = treat_obj.getString("name");
                treatment.link = treat_obj.getString("link");

                if (treat_obj.getInt("category") == 1){
                    array1.add(treatment);
                }
                if (treat_obj.getInt("category") == 2){
                    array2.add(treatment);
                }
                if (treat_obj.getInt("category") == 3){
                    array3.add(treatment);
                }
                if (treat_obj.getInt("category") == 4){
                    array4.add(treatment);
                }
            }

            crn.name = crn_obj.getString("name");
            crn.cLink = crn_obj.getString("cLink");
            crn.imgUrl = crn_obj.getString("imgUrl");
            concerns.add(crn);
        }

        model.addAttribute("concerns", concerns);

        model.addAttribute("array1", array1);
        model.addAttribute("array2", array2);
        model.addAttribute("array3", array3);
        model.addAttribute("array4", array4);

        String menu_list = obj.getJSONObject("menu").getString("list");
        String menu_h1 = obj.getJSONObject("menu").getString("h1");
        String menu_h2 = obj.getJSONObject("menu").getString("h2");
        String menu_h3 = obj.getJSONObject("menu").getString("h3");
        String menu_h4 = obj.getJSONObject("menu").getString("h4");

        model.addAttribute("menu_list", menu_list);
        model.addAttribute("menu_h1", menu_h1);
        model.addAttribute("menu_h2", menu_h2);
        model.addAttribute("menu_h3", menu_h3);
        model.addAttribute("menu_h4", menu_h4);

        return "concern/concern";
    }
}
