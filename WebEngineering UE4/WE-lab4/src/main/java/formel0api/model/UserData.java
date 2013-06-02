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
package formel0api.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import tuwien.big.formel0.picasa.RaceDriver;

/**
 *
 * @author Peter
 */
@Entity
public class UserData implements Serializable {

    private String firstname;
    private String lastname;
    private long birthdate;
    private String sex = "f";
    @Id
    private String username;
    private String password;
    @OneToOne
    private RaceDriver raceDriver;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return new Date(birthdate);
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate.getTime();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public RaceDriver getRaceDriver() {
        return raceDriver;
    }

    public void setRaceDriver(RaceDriver raceDriver) {
        this.raceDriver = raceDriver;
    }

    @Override
    public String toString() {
        return username + "/" + password;
    }

}
