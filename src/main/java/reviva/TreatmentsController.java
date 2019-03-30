package reviva;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import reviva.data.DataService;
import reviva.data.entity.*;

import java.util.ArrayList;

@Controller
public class TreatmentsController {

    DataService dataService = new DataService();
    String lang, langDisplay, pageTitle;

    @GetMapping("/{lang}/treatments")
    public String listTreatment(@PathVariable("lang") String lang, Model model) {
        if (lang.equals("en")) {
            langDisplay = "en";
            pageTitle = "Treatments";
        } else {
            langDisplay = "中文";
            pageTitle = "療程";
        }

        model.addAttribute("lang", lang);
        model.addAttribute("langDisplay", langDisplay);
        model.addAttribute("pageTitle", pageTitle);

        Header header = dataService.getHeader(lang);
        model.addAttribute("header", header);

        Footer footer = dataService.getFooter(lang);
        model.addAttribute("footer", footer);

        JSONObject obj = dataService.getContent(lang);
        JSONObject treat = obj.getJSONObject("treat");
        JSONArray slides_arr = treat.getJSONArray("slides");

        ArrayList<Slide> slides = new ArrayList<>();
        for (int i = 0; i < slides_arr.length(); i ++) {
            Slide sli = new Slide();
            JSONObject sli_obj = slides_arr.getJSONObject(i);
            sli.title = sli_obj.getString("title");
            sli.top = sli_obj.getString("top");
            sli.text = sli_obj.getString("text");
            sli.img = sli_obj.getString("img");
            slides.add(sli);
        }

        model.addAttribute("slides", slides);
        model.addAttribute("counter", 1);
        model.addAttribute("leng", slides.size());

        model.addAttribute("conc", treat.getString("conc"));
        model.addAttribute("ques", treat.getString("ques"));
        model.addAttribute("contact", treat.getString("contact"));
        model.addAttribute("link", treat.getString("link"));

        JSONArray concerns_arr = obj.getJSONArray("concerns");
        ArrayList<Concern> concerns = new ArrayList<>();

        ArrayList<Treatment> array1 = new ArrayList<>();
        ArrayList<Treatment> array2 = new ArrayList<>();
        ArrayList<Treatment> array3 = new ArrayList<>();
        ArrayList<Treatment> array4 = new ArrayList<>();

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

        return "treatments/index";
    }

    @GetMapping("/{lang}/treatments/{treat}")
    public String showTreatment(@PathVariable("lang") String lang, @PathVariable("treat") String treat, Model model) {
        model.addAttribute("lang", lang);

        Header header = dataService.getHeader(lang);
        model.addAttribute("header", header);

        Footer footer = dataService.getFooter(lang);
        model.addAttribute("footer", footer);

        JSONObject obj = dataService.getContent(lang);
        JSONArray concerns_arr = obj.getJSONArray("concerns");

        Treatment treatment = new Treatment();

        ArrayList<Treatment> treatments = new ArrayList<>();

        boolean flag = false;
        for (int i = 0; i < concerns_arr.length(); i ++) {
            JSONArray treatments_arr = concerns_arr.getJSONObject(i).getJSONArray("treatments");
            for (int j = 0; j < treatments_arr.length(); j ++) {
                JSONObject treat_obj = treatments_arr.getJSONObject(j);

                if (treat_obj.getString("link").equals(treat)) {
                    flag = true;

                    for (int k = 0; k < treatments_arr.length(); k ++) {
                        JSONObject treat_k = treatments_arr.getJSONObject(k);

                        Treatment treat_i = new Treatment();
                        treat_i.name = treat_k.getString("name");
                        treat_i.shortDescription = treat_k.getString("shortDescription");
                        treat_i.link = treat_k.getString("link");

                        treatments.add(treat_i);
                    }

                    String cLink = concerns_arr.getJSONObject(i).getString("cLink");
                    String name = concerns_arr.getJSONObject(i).getString("name");
                    model.addAttribute("cLink", cLink);
                    model.addAttribute("name", name);

                    treatment.name = treat_obj.getString("name");
                    treatment.what = treat_obj.getString("what");
                    treatment.shortDescription = treat_obj.getString("shortDescription");
                    treatment.before = treat_obj.getString("before");
                    treatment.benefitText = treat_obj.getString("benefitText");

                    JSONArray slidimg_arr = treat_obj.getJSONArray("slideimg");

                    model.addAttribute("counter", 1);
                    model.addAttribute("leng", slidimg_arr.length());

                    treatment.slideimg = new ArrayList<>();

                    for (int k = 0; k < slidimg_arr.length(); k ++) {
                        String before = slidimg_arr.getJSONObject(k).getString("before");
                        String after = slidimg_arr.getJSONObject(k).getString("after");
                        Slideimg slidimg = new Slideimg(before, after);

                        treatment.slideimg.add(slidimg);
                    }

                    JSONArray qa_arr = treat_obj.getJSONArray("qa");
                    treatment.qa = new ArrayList<>();
                    for (int k = 0; k < qa_arr.length(); k ++) {
                        String question = qa_arr.getJSONObject(k).getString("question");
                        String answer = qa_arr.getJSONObject(k).getString("answer");
                        QA qa = new QA(question, answer);

                        treatment.qa.add(qa);
                    }

                    JSONArray benefit_arr = treat_obj.getJSONArray("benefits");
                    treatment.benefits = new ArrayList<>();
                    for (int k = 0; k < benefit_arr.length(); k ++) {
                        treatment.benefits.add((String) benefit_arr.get(k));
                    }
                }
            }
        }

        if (flag == false) {
            return "404";
        }

        if (lang.equals("en")) {
            langDisplay = "en";
            String uname = treatment.name;
            pageTitle = "";
            String[] splited = uname.split("\\s+");
            for (int i = 0; i < splited.length; i ++) {
                pageTitle = pageTitle + splited[i].toUpperCase().charAt(0) + splited[i].substring(1) + " ";
            }
            langDisplay = "en";
        } else {
            langDisplay = "中文";
            pageTitle = "";
        }

        model.addAttribute("langDisplay", langDisplay);
        model.addAttribute("pageTitle", pageTitle);

        model.addAttribute("treatment", treatment);
        model.addAttribute("treatments", treatments);

        String sin_treat, name, sin_what, sin_both, sin_before, sin_after, sin_ben, sin_other, sin_link, sin_make, sin_contact;
        sin_treat = obj.getJSONObject("single").getString("treat");
        sin_what = obj.getJSONObject("single").getString("what");
        sin_before = obj.getJSONObject("single").getString("before");
        sin_after = obj.getJSONObject("single").getString("after");
        sin_ben = obj.getJSONObject("single").getString("ben");
        sin_other = obj.getJSONObject("single").getString("other");
        sin_make = obj.getJSONObject("single").getString("make");
        sin_contact = obj.getJSONObject("single").getString("contact");
        sin_both = obj.getJSONObject("single").getString("both");
        sin_link = obj.getJSONObject("single").getString("link");

        model.addAttribute("sin_treat", sin_treat);
        model.addAttribute("sin_what", sin_what);
        model.addAttribute("sin_before", sin_before);
        model.addAttribute("sin_after", sin_after);
        model.addAttribute("sin_ben", sin_ben);
        model.addAttribute("sin_other", sin_other);
        model.addAttribute("sin_make", sin_make);
        model.addAttribute("sin_contact", sin_contact);
        model.addAttribute("sin_both", sin_both);
        model.addAttribute("sin_link", sin_link);

        ArrayList<Concern> concerns = new ArrayList<>();
        ArrayList<Treatment> array1 = new ArrayList<>();
        ArrayList<Treatment> array2 = new ArrayList<>();
        ArrayList<Treatment> array3 = new ArrayList<>();
        ArrayList<Treatment> array4 = new ArrayList<>();

        for (int i = 0; i < concerns_arr.length(); i ++) {
            Concern crn = new Concern();
            JSONObject crn_obj = concerns_arr.getJSONObject(i);

            JSONArray treats_arr = crn_obj.getJSONArray("treatments");
            for (int j = 0; j < treats_arr.length(); j ++) {
                JSONObject treat_obj = treats_arr.getJSONObject(j);

                Treatment trt = new Treatment();
                trt.name = treat_obj.getString("name");
                trt.link = treat_obj.getString("link");

                if (treat_obj.getInt("category") == 1){
                    array1.add(trt);
                }
                if (treat_obj.getInt("category") == 2){
                    array2.add(trt);
                }
                if (treat_obj.getInt("category") == 3){
                    array3.add(trt);
                }
                if (treat_obj.getInt("category") == 4){
                    array4.add(trt);
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

        return "treatments/details";
    }
}