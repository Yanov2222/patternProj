package ua.nure.patternProj.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.patternProj.dao.DAOFactory;
import ua.nure.patternProj.dao.IUserDao;
import ua.nure.patternProj.dao.mongodb.MongoDaoFactory;
import ua.nure.patternProj.dao.mongodb.MongoUserDao;
import ua.nure.patternProj.dao.mysql.MysqlDaoFactory;
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.form.RegisterForm;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Slf4j
@Controller
public class RegisterController {

    private IUserDao<User> userDao;
    private IUserDao<ua.nure.patternProj.dao.mongodb.entity.User> userMDao;

    @PostConstruct
    public void init() {
//        DAOFactory factory = MysqlDaoFactory.getInstance();
//        userDao = factory.getUserDao();
        DAOFactory factory = MongoDaoFactory.getInstance();
        userMDao = factory.getUserDao();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("regFrom") RegisterForm registerForm
            , BindingResult result, ModelMap model, HttpServletRequest request) {
        /**MySQL*/
//        User user = User.builder()
//                .addLogin(registerForm.getLogin())
//                .addPassword(registerForm.getPassword())
//                .addName(registerForm.getName())
//                .addEmail(registerForm.getEmail())
//                .addTelephone(registerForm.getTelephone()).build();
//        if (userDao.read(user) == null) {
//            userDao.create(user);
//        } else {
//            log.warn("User already exsits!!");
//        }
//        request.getSession().setAttribute("user", user);


        /**MongoDB*/
        ua.nure.patternProj.dao.mongodb.entity.User userExist = userMDao.read(ua.nure.patternProj.dao.mongodb.entity.User.builder()
                .login(registerForm.getLogin()).build());
        ua.nure.patternProj.dao.mongodb.entity.User user1 = ua.nure.patternProj.dao.mongodb.entity.User.builder()
                .login(registerForm.getLogin())
                .email(registerForm.getEmail())
                .name(registerForm.getName())
                .password(registerForm.getPassword())
                .telephone(registerForm.getTelephone()).build();
        userMDao.create(user1);
        if(userMDao.read(user1)==null){
            userMDao.create(user1);
        }else {
            log.warn("User already exists!");
        }

//        if (userDao.read(user) == null) {
//            userDao.create(user);
//        } else {
//            log.warn("User already exsits!!");
//
//        }
        request.getSession().setAttribute("user",user1);



        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerUser(Model model) {
        return new ModelAndView("register", "regForm", new RegisterForm());
    }

}
