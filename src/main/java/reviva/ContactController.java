package reviva;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import reviva.data.DataService;
import reviva.data.entity.*;

@Controller
public class ContactController {

    DataService dataService = new DataService();
    String lang, langDisplay, pageTitle;

    @GetMapping("/{lang}/contact")
    public String contact(@PathVariable("lang") String lang, Model model) {
        if (lang.equals("en")) {
            langDisplay = "en";
            pageTitle = "Contact Us";
        } else {
            langDisplay = "中文";
            pageTitle = "聯繫我們";
        }

        model.addAttribute("lang", lang);
        model.addAttribute("langDisplay", langDisplay);
        model.addAttribute("pageTitle", pageTitle);

        Header header = dataService.getHeader(lang);
        model.addAttribute("header", header);

        Footer footer = dataService.getFooter(lang);
        model.addAttribute("footer", footer);

        JSONObject obj = dataService.getContent(lang);
        JSONObject contact = obj.getJSONObject("contact");

        String title, book, phone, book2, orEmail, form, book3, inquiry, respond, pla1, pla2, pla3, pla4, enter, submit, name, h1, h2, h3, h4, h5, h6, h7, h8, h9, t1, t2, t3;

        title = contact.getString("title");
        book = contact.getString("book");
        phone = contact.getString("phone");
        book2 = contact.getString("book2");
        orEmail = contact.getString("orEmail");
        form = contact.getString("form");
        book3 = contact.getString("book3");
        inquiry = contact.getString("inquiry");
        respond = contact.getString("respond");
        pla1 = contact.getString("pla1");
        pla2 = contact.getString("pla2");
        pla3 = contact.getString("pla3");
        pla4 = contact.getString("pla4");
        enter = contact.getString("enter");
        submit = contact.getString("submit");
        name = contact.getString("name");
        h1 = contact.getString("h1");
        h2 = contact.getString("h2");
        h3 = contact.getString("h3");
        h4 = contact.getString("h4");
        h5 = contact.getString("h5");
        h6 = contact.getString("h6");
        h7 = contact.getString("h7");
        h8 = contact.getString("h8");
        h9 = contact.getString("h9");
        t1 = contact.getString("t1");
        t2 = contact.getString("t2");
        t3 = contact.getString("t3");

        model.addAttribute("title", title);
        model.addAttribute("book", book);
        model.addAttribute("phone", phone);
        model.addAttribute("book2", book2);
        model.addAttribute("orEmail", orEmail);
        model.addAttribute("form", form);
        model.addAttribute("book3", book3);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("respond", respond);
        model.addAttribute("pla1", pla1);
        model.addAttribute("pla2", pla2);
        model.addAttribute("pla3", pla3);
        model.addAttribute("pla4", pla4);
        model.addAttribute("enter", enter);
        model.addAttribute("submit", submit);
        model.addAttribute("name", name);
        model.addAttribute("h1", h1);
        model.addAttribute("h2", h2);
        model.addAttribute("h3", h3);
        model.addAttribute("h4", h4);
        model.addAttribute("h5", h5);
        model.addAttribute("h6", h6);
        model.addAttribute("h7", h7);
        model.addAttribute("h8", h8);
        model.addAttribute("h9", h9);
        model.addAttribute("t1", t1);
        model.addAttribute("t2", t2);
        model.addAttribute("t3", t3);

        return "contact/contact";
    }
}