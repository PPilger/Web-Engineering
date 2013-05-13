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
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private Login login = new Login();
    private User user;
    
    @ManagedProperty(value = "#{applicationBean}")
    private ApplicationBean bean;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public void setBean(ApplicationBean bean) {
        this.bean = bean;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = bean.login(login);
        
        if(user != null) {
            Map<String, Object> map = context.getExternalContext().getSessionMap();
            GameBean bean = new GameBean(user);
            map.put("gameBean", bean);
            return "table.xhtml";
        }
        
        ResourceBundle rb = ResourceBundle.getBundle("i18n", context.getExternalContext().getRequestLocale());
        context.addMessage("login:form:submit", new FacesMessage(rb.getString("indexLoginFailed")));
        return "";
    }
}
