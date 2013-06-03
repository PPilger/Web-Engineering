/*
 * Copyright 2013 Peter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package formel0api.beans;

import com.google.gdata.util.ServiceException;
import formel0api.model.Login;
import formel0api.model.UserData;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import tuwien.big.formel0.picasa.IRaceDriverImpl;
import tuwien.big.formel0.picasa.IRaceDriverService;
import tuwien.big.formel0.picasa.RaceDriver;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "applicationBean")
@ApplicationScoped
public class ApplicationBean implements Serializable {

    private EntityManager man;

    public ApplicationBean() {
        EntityManagerFactory fac = Persistence.createEntityManagerFactory("lab4");
        man = fac.createEntityManager();

        loadRaceDriver();
    }

    public void save(UserData user) {
        System.out.println("save " + user);

        if (!isRegistered(user)) {
            System.out.println("not registered");

            man.getTransaction().begin();
            man.persist(user);
            man.getTransaction().commit();
        } else {
            System.out.println("already registered");
        }
    }

    public List<RaceDriver> getRaceDrivers() throws ServiceException, IOException {
        System.out.println("getRaceDrivers");

        TypedQuery<RaceDriver> query = man.createQuery("from Avatar", RaceDriver.class);
        List<RaceDriver> raceDrivers = query.getResultList();

        System.out.println("race drivers: " + raceDrivers);

        return raceDrivers;
    }

    public boolean isRegistered(UserData user) {
        System.out.println("isRegistered " + user);

        TypedQuery<UserData> query = man.createQuery("select u from UserData u where u.username=:username", UserData.class);
        query.setParameter("username", user.getUsername());
        List<UserData> found = query.getResultList();

        System.out.println("registered user: " + found);

        return found.size() > 0;
    }

    public UserData login(Login login) {
        System.out.println("login " + login);

        TypedQuery<UserData> query = man.createQuery("select u from UserData u where u.username=:username and u.password=:password", UserData.class);
        query.setParameter("username", login.getUsername());
        query.setParameter("password", login.getPassword());
        List<UserData> found = query.getResultList();

        System.out.println("user found: " + found);

        if (found.size() > 0) {
            return found.get(0);
        }
        return null;
    }

    private void loadRaceDriver() {
        System.out.println("Load race drivers");

        try {
            IRaceDriverService raceDriver = new IRaceDriverImpl();
            List<RaceDriver> temp = raceDriver.getRaceDrivers();

            for (RaceDriver driver : temp) {
                man.getTransaction().begin();
                man.persist(driver);
                man.getTransaction().commit();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ServiceException ex) {
            ex.printStackTrace();
        }
    }
}
