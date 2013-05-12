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

import formel0api.model.Login;
import formel0api.model.User;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Peter
 */
@ManagedBean(name="applicationBean", eager=true)
@ApplicationScoped
public class ApplicationBean implements Serializable {

    private Map<String, User> users = new HashMap<String, User>();

    public String save(User user) {
        user.setRegistered(true);
        users.put(user.getUsername(), user);
        return "";//login.xhtml";
    }

    public boolean login(Login login) {
        User user = new User();
        user.setFirstname("Max");
        user.setLastname("Mustermann");
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getExternalContext().getSessionMap().put("user", user);
        return true;/*
        User user = users.get(login.getUsername());
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(login.getPassword());*/
    }
}
