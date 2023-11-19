package homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.WeaponDao;
import homework.models.Weapon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "weaponServlet", value = "/weapons")
public class WeaponServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String weaponId = req.getParameter("weapon_id");
        String s;
        if (weaponId == null) {
            s = mapper.writeValueAsString(WeaponDao.getAllWeapons());
        } else {
            s = mapper.writeValueAsString(WeaponDao.getWeaponById(Long.parseLong(weaponId)));
        }
        try (var writer = resp.getWriter()) {
            writer.println(s);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long returnedId = WeaponDao.saveWeapon(parseWeapon(req));
        try (var writer = resp.getWriter()) {
            if (returnedId != null) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.println("Your weapon was saved with id " + returnedId);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Something went horribly wrong and your weapon vanished");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            try {
                WeaponDao.updateWeapon(parseWeapon(req));
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                writer.println("Weapon was updated");
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Weapon was not updated and everything is lost");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            try {
                boolean isDeleted = WeaponDao.deleteWeapon(Long.valueOf(req.getParameter("weapon_id")));
                if (isDeleted) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    writer.println("Weapon was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writer.println("Weapon was not deleted. Maybe you got non-existing weapon_id");
                }
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Weapon was not deleted. Provide weapon_id, maybe that's the case");
            }
        }
    }

    private static Weapon parseWeapon(HttpServletRequest req) {
        String idHolder = req.getParameter("weapon_id");
        String damageHolder = req.getParameter("weapon_damage");

        Long weaponId = idHolder == null ? null : Long.valueOf(idHolder);
        String weaponName = req.getParameter("weapon_name");
        Integer weaponDamage = damageHolder == null ? null : Integer.valueOf(damageHolder);

        return new Weapon(weaponId, weaponName, weaponDamage);
    }
}
