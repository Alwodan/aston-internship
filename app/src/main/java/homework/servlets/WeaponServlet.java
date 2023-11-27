package homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.Dao;
import homework.dao.WeaponDao;
import homework.models.Weapon;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@WebServlet(name = "weaponServlet", value = "/weapons")
@Service
public class WeaponServlet extends HttpServlet {

    Dao<Weapon> dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        this.dao = (WeaponDao) ac.getBean("weaponDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            String weaponId = req.getParameter("weapon_id");
            if (weaponId != null) {
                writer.println(new ObjectMapper().writeValueAsString(dao.getById(Long.valueOf(weaponId))));
            } else {
                writer.println(new ObjectMapper().writeValueAsString(dao.getAll()));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            if (req.getParameter("weapon_id") != null) {
                writer.println("You actually don't need to specify id here. For updating use PUT method.");
                writer.println("Aborting operation...");
            } else {
                Weapon weapon = dao.save(parseWeapon(req));
                writer.println("Your weapon must have been saved with id " + weapon.getId());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            if (req.getParameter("weapon_id") == null) {
                writer.println("You actually do need to specify id here. For creating use POST method.");
                writer.println("Aborting operation...");
            } else {
                dao.update(parseWeapon(req));
                writer.println("Your weapon must have been updated");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            String weaponId = req.getParameter("weapon_id");
            if (weaponId == null) {
                writer.println("You actually do need to specify id here. Cannot delete without id.");
                writer.println("Aborting operation...");
            } else {
                dao.delete(Long.valueOf(weaponId));
                writer.println("Your weapon must have been deleted");
            }
        }
    }

    private Weapon parseWeapon(HttpServletRequest req) {
        String idHolder = req.getParameter("weapon_id");
        String damageHolder = req.getParameter("weapon_damage");

        Long weaponId = idHolder == null ? null : Long.valueOf(idHolder);
        String weaponName = req.getParameter("weapon_name");
        Integer weaponDamage = damageHolder == null ? null : Integer.valueOf(damageHolder);

        return new Weapon(weaponId, weaponName, weaponDamage);
    }
}
