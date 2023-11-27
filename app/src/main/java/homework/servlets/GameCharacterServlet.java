package homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.Dao;
import homework.dao.GameCharacterDao;
import homework.dao.WeaponDao;
import homework.models.GameCharacter;
import homework.models.Weapon;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@WebServlet(name = "characterServlet", value = "/characters")
@Component
public class GameCharacterServlet extends HttpServlet {
    GameCharacterDao dao;
    Dao<Weapon> weaponDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        this.dao = (GameCharacterDao) ac.getBean("gameCharacterDao");
        this.weaponDao = (WeaponDao) ac.getBean("weaponDao");
    }

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
            String characterId = req.getParameter("character_id");
            String factionId = req.getParameter("faction_id");
            if (characterId != null && factionId != null) {
                dao.enterFaction(Long.valueOf(characterId), Long.valueOf(factionId));
                writer.println("You character must have entered the faction, provided they both exist");
            } else {
                if (characterId != null) {
                    writer.println("You don't need to specify character id here if creating. For updating use PUT method.");
                    writer.println("Aborting operation...");
                } else if (req.getParameter("character_weapon_id") == null) {
                    writer.println("For some reason every characters REQUIRES a weapon, so specify id for it");
                    writer.println("Aborting operation...");
                } else {
                    GameCharacter character = dao.save(parseCharacter(req));
                    writer.println("Your character must have been saved with id " + character.getId());
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            if (req.getParameter("character_id") == null) {
                writer.println("You do need to specify character id here if updating. For creating use POST method.");
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
            String factionId = req.getParameter("faction_id");
            if (characterId != null && factionId != null) {
                dao.leaveFaction(Long.valueOf(characterId), Long.valueOf(factionId));
                writer.println("Your character must have left the faction, provided they both exist");
            } else {
                if (characterId == null) {
                    writer.println("You actually do need to specify id here. Cannot delete without id.");
                    writer.println("Aborting operation...");
                } else {
                    dao.delete(Long.valueOf(characterId));
                    writer.println("Your character must have been deleted");
                }
            }
        }
    }

    private GameCharacter parseCharacter(HttpServletRequest req) {
        String idHolder = req.getParameter("character_id");
        String weaponIdHolder = req.getParameter("character_weapon_id");
        String powerHolder = req.getParameter("character_powerlevel");

        Long characterId = idHolder == null ? null : Long.valueOf(idHolder);
        Weapon weapon = weaponIdHolder == null ? null : weaponDao.getById(Long.valueOf(weaponIdHolder));
        Integer powerLevel = powerHolder == null ? null : Integer.valueOf(powerHolder);

        String characterName = req.getParameter("character_name");
        return new GameCharacter(characterId,characterName, weapon, powerLevel);
    }
}
