package domain.model;

import domain.service.DbException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private int userid;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Team team;
    private Role role;

    public User(String email, String password, String firstName, String lastName, Team team) {
        setEmail(email);
        this.password = password;
        setFirstName(firstName);
        setLastName(lastName);
        setTeam(team);
        setRole(Role.EMPLOYEE);
    }

    public User(int userid, String email, String password, String firstName, String lastName, Team team) {
        this(email, password, firstName, lastName, team);
        this.setUserid(userid);
    }

    public User() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setEmail(String email) {
        if (email.isEmpty()) {
            throw new DbException("No email given");
        }
        String USERID_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern p = Pattern.compile(USERID_PATTERN);
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new DbException("Email not valid");
        }
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isCorrectPassword(String password) {
        if (password.isEmpty()) {
            throw new DbException("No password given");
        }
        String hashed = hashPassword(password);
        return hashed.equals(this.password);
    }

    public void setPassword(String password) {
        if (password.isEmpty()) {
            throw new DbException("No password given");
        }
        this.password = hashPassword(password);
    }

    private String hashPassword(String password) {
        try {
            return sha512(password);
        } catch (NoSuchAlgorithmException e) {
            throw new DbException(e);
        } catch (UnsupportedEncodingException e) {
            throw new DbException(e);
        }
    }

    private String sha512(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //Create MessageDigest
        MessageDigest crypt = MessageDigest.getInstance("SHA-512");
        //Reset
        crypt.reset();
        //Update
        byte[] passwordBytes = password.getBytes("UTF-8");
        crypt.update(passwordBytes);
        //Digest
        byte[] digest = crypt.digest();
        //Convert to String
        BigInteger digestAsBigInteger = new BigInteger(1, digest);
        //Return hashed password
        return digestAsBigInteger.toString(16);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.isEmpty()) {
            throw new DbException("No firstname given");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.isEmpty()) {
            throw new DbException("No last name given");
        }
        this.lastName = lastName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTeam(String team) {
        try {
            this.team = Team.valueOf(team.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DbException("There is no team with value " + team);
        }
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + ": " + getUserid() + ", " + getEmail() + ", " + getRole() + ", " + getTeam();
    }
}
