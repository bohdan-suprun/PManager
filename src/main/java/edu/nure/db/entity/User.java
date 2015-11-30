package edu.nure.db.entity;

import edu.nure.db.entity.constraints.ValidationException;
import edu.nure.db.entity.constraints.Validator;

import java.util.Objects;

/**
 * Created by bod on 17.09.15.
 */
public class User implements Transmittable{
    public static final int ID_NOT_SET = -1;
    private int id = ID_NOT_SET;
    private String phone;
    private String name;
    private String password;
    private String email;
    private Right right;

    public User(int id, String name, String phone, String email, String password, Right right) throws ValidationException {
        this.id = id;
        setPhone(phone);
        setName(name);
        setEmail(email);
        this.password = password;
        this.right = right;

    }

    public static String[] getFields() {
        return new String[]{"Name", "Phone", "Email", "Right", "Password"};
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ValidationException {
        try {
            if (email != null) {
                this.email = Validator.validate(email, Validator.EMAIL_VALIDATOR);
            }
        } catch (ValidationException ex) {
            throw new ValidationException("Адресс электронной почты указан неверно");
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws ValidationException {
        try {
            this.phone = Objects.requireNonNull(Validator.validate(phone, Validator.PHONE_VALIDATOR));
        }catch (NullPointerException ex){
                throw new ValidationException("Вы не казали номер телефона");
        }catch (ValidationException ex){
            throw new ValidationException("Номер телефона в неверном формате. Пример: +38(xxx) yyy-yy-yy");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ValidationException {
        try {
            this.name = Objects.requireNonNull(Validator.validate(name.replace('"', '\''), Validator.NAME_VALIDATOR));
        } catch (NullPointerException ex){
            throw new ValidationException("Вы забыли указать имя");
        } catch (ValidationException ex){
            throw new ValidationException("Имя должно состоять только из букв, может содержать символ '");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Right getRight() {
        return right;
    }

    public void setRight(Right right) {
        this.right = right;
    }

    @Override
    public String toXML() {
        return "<user "+"id=\""+id+"\" name=\""+name.replace('"','\'')+"\""
                +" phone=\""+phone+"\""
                + ((email == null)?"":" email=\""+email.replace('"','\'')+"\"")
                +" right=\""+right.getType()+"\"/>";
    }

    @Override
    public String toQuery() {
        return "id=" + id +
                "&phone=" + phone +
                "&name=" + name  +
                ((email == null)?"":"&email="+email.replace('"','\'')) +
                "&right=" + right.getType();
    }

}
