package homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.Dao;
import homework.dao.GameCharacterDao;
import homework.dao.WeaponDao;
import homework.models.GameCharacter;
import homework.models.Weapon;
import homework.utils.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "characterServlet", value = "/characters")
public class GameCharacterServlet extends HttpServlet {
    Dao<GameCharacter> dao = new GameCharacterDao(HibernateUtil.getSessionFactory());
    Dao<Weapon> weaponDao = new WeaponDao(HibernateUtil.getSessionFactory());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            String characterId = req.getParameter("character_id");
            if (characterId != null) {
                writer.println(new ObjectMapper().writeValueAsString(dao.getById(Long.valueOf(characterId))));
            } else {
                writer.println(new ObjectMapper().writeValueAsString(dao.getAll()));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            if (req.getParameter("character_id") != null) {
                writer.println("You actually don't need to specify character id here. For updating use PUT method.");
                writer.println("Aborting operation...");
            } else if (req.getParameter("character_weapon_id") == null) {
                writer.println("For some reason every characters REQUIRES a weapon, so specify id for it");
                writer.println("Aborting operation...");
            } else {
                dao.save(parseCharacter(req));
                writer.println("Your character must have been saved");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            if (req.getParameter("character_id") == null) {
                writer.println("You actually do need to specify character id here. For creating use POST method.");
                writer.println("Aborting operation...");
            } else {
                dao.update(parseCharacter(req));
                writer.println("Your character must have been updated");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            String characterId = req.getParameter("character_id");
            if (characterId == null) {
                writer.println("You actually do need to specify id here. Cannot delete without id.");
                writer.println("Aborting operation...");
            } else {
                dao.delete(Long.valueOf(characterId));
                writer.println("Your character must have been deleted");
            }
        }
    }

    private GameCharacter parseCharacter(HttpServletRequest req) {
        String idHolder = req.getParameter("character_id");
        String weaponIdHolder = req.getParameter("character_weapon_id");
        String powerHolder = req.getParameter("character_powerlevel");

        Long characterId = idHolder == null ? null : Long.valueOf(idHolder);
        Weapon weapon = weaponIdHolder == null ? null : weaponDao.getById(Long.valueOf(weaponIdHolder));
        Integer powerlevel = powerHolder == null ? null : Integer.valueOf(powerHolder);

        String characterName = req.getParameter("character_name");
        return new GameCharacter(characterId, weapon, characterName, powerlevel, null);
    }
}
