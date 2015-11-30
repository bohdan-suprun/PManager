package edu.nure.db.entity;

import edu.nure.db.entity.constraints.MoreOrEq;
import edu.nure.db.entity.constraints.ValidationException;
import edu.nure.db.entity.constraints.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bod on 17.09.15.
 */
public class Order implements Transmittable{

    public static final int ID_NOT_SET = -1;
    private int id = ID_NOT_SET;
    private int customer, responsible;
    private String desc;
    private Date term;
    private int urgency;
    private float forPay;
    private int status;

    public Order(int id, int customer, int responsible, String desc, String term, float forPay, int status, int urg)
            throws ValidationException {
        try {
            setId(id);
            setCustomer(customer);
            setResponsible(responsible);
            setDesc(desc);
            setTerm(term);
            setForPay(forPay);
            setStatus(status);
            setUrgency(urg);
        } catch (ParseException ex) {
            throw new ValidationException();
        }

    }

    public static String[] getFields() {
        return new String[]{"Customer", "Responsible", "Desc", "Term", "For_pay",
                "Status", "Urg"};
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getResponsible() {
        return responsible;
    }

    public void setResponsible(int responsible) {
        this.responsible = responsible;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        if(desc != null)
            desc = desc.replace('\'','"');
        this.desc = desc;
    }

    public float getForPay() {
        return forPay;
    }

    public void setForPay(float forPay) throws ValidationException {
        this.forPay = (Float)Validator.validate(forPay, new MoreOrEq<Float>(0.f));
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(String term) throws ParseException {
        this.term = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(term);
    }

    @Override
    public String toXML() {
        return "<order id=\""+id+"\" customer=\""+customer+"\" responsible=\""+responsible+"\""+
                ((desc == null)?"":" desc=\""+desc.replace('"','\'')+"\"")+" term=\""+
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term)+"\" for_pay=\""+forPay+"\" status=\""+
                status+"\" urgency=\""+urgency+"\"/>";
    }

    @Override
    public String toQuery() {
        return "id=" + id +
                "&customer=" + customer +
                "&responsible=" + responsible +
                ((desc == null)?"":"&desc=" + getDesc().replace("'", "\"")) +
                "&term=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term) +
                "&forPay=" + forPay+
                "&status=" + status+
                "&urgency=" + urgency;
    }
}
