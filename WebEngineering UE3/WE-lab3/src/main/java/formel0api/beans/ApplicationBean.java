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
@ManagedBean(name="applicationBean")
@ApplicationScoped
public class ApplicationBean implements Serializable {

    private Map<String, User> users;
    
    public ApplicationBean() {
        System.out.println(this);
        users = new HashMap<String, User>();
    }

    public void save(User user) {
        System.out.println("save "+user.getUsername() + ", "+user.getPassword());
        user.setRegistered(true);
        users.put(user.getUsername(), user);
        System.out.println("users: " + users);
    }

    public User login(Login login) {
        System.out.println("login "+login.getUsername() + ", "+login.getPassword());
        System.out.println("users: " + users);
        User user = users.get(login.getUsername());
        if (user == null) {
            return null;
        } else if(user.getPassword().equals(login.getPassword())) {
            return user;
        }
        return null;
    }
}
