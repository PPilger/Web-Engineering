package tuwien.big.formel0.entities;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Contains all current registered players
 */
@ManagedBean(name = "rpp")
@ApplicationScoped
public class RegisteredPlayerPool {
    private EntityManager man;

    /**
     * Creates a new instance of RegisteredPlayerPool
     */
    public RegisteredPlayerPool() {
        EntityManagerFactory fac = Persistence.createEntityManagerFactory("lab4");
        man = fac.createEntityManager();
        
        //Add test player
        Player tp = new Player();
        tp.setName("t");
        tp.setPassword("t1");
        
        addPlayer(tp);
    }

    public boolean addPlayer(Player p) {
        System.out.println("addPlayer " + p);
        
        TypedQuery<Player> query = man.createQuery("select p from Player p where p.name=:name", Player.class);
        query.setParameter("name", p.getName());
        List<Player> found = query.getResultList();
        
        System.out.println("found: "+found);
        
        if(found.size() == 0) {
            man.getTransaction().begin();
            man.persist(p);
            man.getTransaction().commit();
            
            return true;
        }
        
        return false;
    }

    public Player getRegisteredPlayer(String username, String password) {
        System.out.println("getRegisteredPlayer un="+username+", pw="+password);
        
        TypedQuery<Player> query = man.createQuery("select p from Player p where p.name=:name and p.password=:password", Player.class);
        query.setParameter("name", username);
        query.setParameter("password", password);
        List<Player> found = query.getResultList();
        
        System.out.println("found: "+found);
        
        if(found.size() > 0) {
            return found.get(0);
        }
        
        return null;
    }

    /**
     * @return the players
     */
    public List<Player> getRegplayers() {
        System.out.println("getRegplayers");
        
        TypedQuery<Player> query = man.createQuery("select p from Player p", Player.class);
        List<Player> players = query.getResultList();
        
        System.out.println(players);
        
        return players;
    }
}
