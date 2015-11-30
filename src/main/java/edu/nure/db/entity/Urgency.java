package edu.nure.db.entity;
import edu.nure.db.entity.constraints.MoreOrEq;
import edu.nure.db.entity.constraints.ValidationException;
import edu.nure.db.entity.constraints.Validator;

import java.util.Date;

/**
 * Created by bod on 17.09.15.
 */
public class Urgency implements Transmittable {
    private static final int TO_MS = 60000;
    int term;
    float factor;

    public Urgency(int term, float factor) throws ValidationException {
        setTerm(term);
        setFactor(factor);
    }

    public static String[] getFields() {
        return new String[]{"Term", "Factor"};
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) throws ValidationException {
        Date now = new Date();
        Date inTerm = new Date(now.getTime()+term*TO_MS);
        if(inTerm.after(now)) {
            this.term = term;
        }else throw new ValidationException("Срочность должна быть больше нуля");
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) throws ValidationException {
        this.factor = (Float) Validator.validate(factor, new MoreOrEq<Float>(0.0f));
    }

    @Override
    public String toXML() {
        return "<urgency term=\""+term+"\" factor=\""+factor+"\"/>";
    }

    @Override
    public String toQuery() {
        return "term=" + term +
                "&factor=" + factor;
    }
}
