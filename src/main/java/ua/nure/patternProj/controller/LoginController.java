package ua.nure.patternProj.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.patternProj.dao.DAOFactory;
import ua.nure.patternProj.dao.IAutoDao;
import ua.nure.patternProj.dao.IUserDao;
import ua.nure.patternProj.dao.mysql.MysqlDaoFactory;
import ua.nure.patternProj.dao.mysql.entity.Auto;
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.form.LoginForm;
import ua.nure.patternProj.observer.LogFileListener;
import ua.nure.patternProj.observer.NotificationListener;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class LoginController {

    private IUserDao<User> userDao;
    private IAutoDao autoDao;

    @PostConstruct
    public void init(){
        DAOFactory factory = MysqlDaoFactory.getInstance();
        userDao = factory.getUserDao();
        autoDao = factory.getAutoDao();
        userDao.events.subscribe("create", new NotificationListener());
        userDao.events.subscribe("read", new NotificationListener());
        userDao.events.subscribe("read", new LogFileListener("sigin_in_log.txt"));
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String register(@ModelAttribute("logForm") LoginForm loginForm
            , BindingResult result, ModelMap model, HttpServletRequest request) {

        User user = User.builder()
                .addLogin(loginForm.getLogin())
                .addPassword(loginForm.getPassword())
                .build();

        if (userDao.read(user) != null) {
            User user2 = userDao.read(user);
            if(user2.getPassword().equals(user.getPassword())){
                List<Integer> shoppCart = new ArrayList<>();
                request.getSession().setAttribute("user",user2);
                request.getSession().setAttribute("shoppCart", shoppCart);
                return "index";
            }
        } else {
            log.warn("User doesn't exist!!!");
        }
        request.getSession().setAttribute("user", user);
        List<Auto> list = autoDao.getAllAuto();
        request.getSession().setAttribute("catalog", list);
        List<Auto> shoppcart = new ArrayList<>();
        request.getSession().setAttribute("shoppCart", shoppcart);

        return "redirect:/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView registerUser(Model model) {
        return new ModelAndView("login", "logForm", new LoginForm());
    }


}
