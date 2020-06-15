package ua.nure.patternProj.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nure.patternProj.dao.DAOFactory;
import ua.nure.patternProj.dao.IAutoDao;
import ua.nure.patternProj.dao.mysql.MysqlDaoFactory;
import ua.nure.patternProj.dao.mysql.entity.Auto;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CatalogController {

    private IAutoDao autoDao;

    @PostConstruct
    public void init(){
        DAOFactory factory = MysqlDaoFactory.getInstance();
        autoDao = factory.getAutoDao();
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
        public String toCatalog(Model model, HttpServletRequest request){
            List<Auto> list = autoDao.getAllAuto();
            request.getSession().setAttribute("catalog", list);
            return "catalog";
        }
}
