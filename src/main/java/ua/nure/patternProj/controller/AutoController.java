package ua.nure.patternProj.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.patternProj.dao.DAOFactory;
import ua.nure.patternProj.dao.IAutoDao;
import ua.nure.patternProj.dao.IManufacturerDao;
import ua.nure.patternProj.dao.mongodb.MongoDaoFactory;
import ua.nure.patternProj.dao.mysql.MysqlDaoFactory;
import ua.nure.patternProj.dao.mysql.entity.Auto;
import ua.nure.patternProj.dao.mysql.entity.Manufacturer;
import ua.nure.patternProj.form.AutoForm;
import ua.nure.patternProj.observer.LogFileListener;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Controller
public class AutoController {
    private IAutoDao<Auto> autoDao;
    private IManufacturerDao manufacturerDao;
    private ua.nure.patternProj.dao.mongodb.entity.Auto snapshotAuto;
    private IAutoDao<ua.nure.patternProj.dao.mongodb.entity.Auto> autoMDao;

    @PostConstruct
    public void init() {
        DAOFactory factory = MysqlDaoFactory.getInstance();
        autoDao = factory.getAutoDao();
        manufacturerDao = factory.getManufacturerDao();

        factory = MongoDaoFactory.getInstance();
        autoMDao = factory.getAutoDao();

        autoDao.events.subscribe("create", new LogFileListener("CRUD_AUTO_LOG.txt"));
        autoDao.events.subscribe("read", new LogFileListener("CRUD_AUTO_LOG.txt"));
        autoDao.events.subscribe("update", new LogFileListener("CRUD_AUTO_LOG.txt"));
        autoDao.events.subscribe("delete", new LogFileListener("CRUD_AUTO_LOG.txt"));
    }

    @RequestMapping(value = "/addAuto", method = RequestMethod.POST)
    public String addAuto(@ModelAttribute("autoForm") AutoForm autoForm,
                          BindingResult result, Model model, HttpServletRequest request) {
        String manufacturerName = autoForm.getManufacturer();
        Manufacturer manufacturer = Manufacturer
                .builder()
                .addManufacturerName(manufacturerName)
                .build();
        int hasBabySeat = autoForm.isHasBabySeat() ? 1 : 0;
        int hasConditioner = autoForm.isHasConditioner() ? 1 : 0;
        int hasBar = autoForm.isHasBar() ? 1 : 0;

        /** MySQL */
//        manufacturer = manufacturerDao.manufacturerTransaction(manufacturer);
//        if (manufacturer == null) {
//            log.warn("MANUFACTURER NOT FOUND");
//        }
//        Auto auto = Auto.builder()
//                .addModel(autoForm.getModel())
//                .addSeats(autoForm.getSeats())
//                .addPrice(autoForm.getPrice())
//                .addBabySeat(hasBabySeat)
//                .addConditioner(hasConditioner)
//                .addBar(hasBar)
//                .addManufacturerId(manufacturer.getId())
//                .build();
//        autoDao.create(auto);

        /** MongoDB*/


        ua.nure.patternProj.dao.mongodb.entity.Auto auto = ua.nure.patternProj.dao.mongodb.entity.Auto.builder()
                .model(autoForm.getModel())
                .seats(autoForm.getSeats())
                .price(autoForm.getPrice())
                .hasBabySeat(hasBabySeat)
                .hasConditioner(hasConditioner)
                .hasBar(hasBar)
                .manufacturer(autoForm.getManufacturer())
                .build();
        autoMDao.create(auto);

        return "index";
    }

    @RequestMapping(value = "/addAuto", method = RequestMethod.GET)
    public ModelAndView addAuto(Model model) {
        return new ModelAndView("addAuto", "autoForm", new AutoForm());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateAuto(Model model, @RequestParam("uuid") String uuid) {
        snapshotAuto = autoMDao.getByUuid(uuid);
        AutoForm form = new AutoForm();
        form.setUuid(snapshotAuto.getUuid());
        form.setModel(snapshotAuto.getModel());
        form.setPrice(snapshotAuto.getPrice());
        form.setHasBabySeat(toBool(snapshotAuto.getHasBabySeat()));
        form.setHasConditioner(toBool(snapshotAuto.getHasConditioner()));
        form.setHasBar(toBool(snapshotAuto.getHasBar()));
        form.setSeats(snapshotAuto.getSeats());
        form.setManufacturer(snapshotAuto.getManufacturer());
        return new ModelAndView("update", "autoForm", form);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAuto(@ModelAttribute("autoForm") AutoForm autoForm, Model model, HttpServletRequest request) {
        int hasBabySeat = autoForm.isHasBabySeat() ? 1 : 0;
        int hasConditioner = autoForm.isHasConditioner() ? 1 : 0;
        int hasBar = autoForm.isHasBar() ? 1 : 0;
        /** MySQL */
//        Auto auto = Auto.builder()
//                .addModel(autoForm.getModel())
//                .addSeats(autoForm.getSeats())
//                .addPrice(autoForm.getPrice())
//                .addBabySeat(hasBabySeat)
//                .addConditioner(hasConditioner)
//                .addBar(hasBar)
//                .addManufacturerId(autoForm.getManufacturerId())
//                .build();
//        autoDao.update(auto);

        /** MongoDB */
        ua.nure.patternProj.dao.mongodb.entity.Auto auto = ua.nure.patternProj.dao.mongodb.entity.Auto.builder()
                .model(autoForm.getModel())
                .uuid(autoForm.getUuid())
                .seats(autoForm.getSeats())
                .price(autoForm.getPrice())
                .hasBabySeat(hasBabySeat)
                .hasConditioner(hasConditioner)
                .hasBar(hasBar)
                .manufacturer(autoForm.getManufacturer())
                .build();
        autoMDao.update(auto);
        return "redirect:/catalog";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAuto(Model model, @RequestParam("uuid") String uuid) {
        /** MySQL */
//        Auto auto = autoDao.getByUuid(uuid);
//        autoDao.delete(auto);
        /** MOngoDB */
        ua.nure.patternProj.dao.mongodb.entity.Auto auto = autoMDao.getByUuid(uuid);
        autoMDao.delete(auto);
        return "redirect:/catalog";
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchAuto(@RequestParam("minPrice") int minPrice, @RequestParam("maxPrice") int maxPrice,
                             @RequestParam("hasBabySeat") boolean hasBabySeat, @RequestParam("hasConditioner") boolean hasConditioner,
                             @RequestParam("hasBar") boolean hasBar, Model model, HttpServletRequest request) {
        int hasBabySeat2 = hasBabySeat ? 1 : 0;
        int hasConditioner2 = hasConditioner ? 1 : 0;
        int hasBar2 = hasBar ? 1 : 0;
        /** MySQL */
//        List<Auto> list2 = autoDao.getAutoByParameter(minPrice, maxPrice, hasBabySeat2, hasConditioner2, hasBar2);
//        request.getSession().setAttribute("catalog", list);
        /** MongoDB */

        List<ua.nure.patternProj.dao.mongodb.entity.Auto> list = autoMDao
                .getAutoByParameter(minPrice, maxPrice, hasBabySeat2, hasConditioner2, hasBar2);
        request.getSession().setAttribute("catalog", list);

        return "catalog";
    }

    public static boolean toBool(int i) {
        if (i == 1) {
            return true;
        }
        return false;
    }
}
