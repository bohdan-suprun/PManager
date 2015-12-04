package edu.nure.net;

import edu.nure.listener.ResponseListener;
import edu.nure.listener.exception.UnknownTagNameException;
import edu.nure.listener.results.DBResult;
import edu.nure.listener.results.DBSelectResult;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by bod on 01.10.15.
 */
class MyParser extends DefaultHandler {
    private ResponseListener listener;
    private DBResult result;

    MyParser(ResponseListener listener){
        this.listener = listener;
    }

    MyParser(){
        this.listener = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("action")) {
            result = createResult(qName, attributes);
        }
        else if(result != null && result.isSuccess()){
            if(attributes.getLength() > 0) {
                try {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        if (attributes.getQName(i).equals("code")) {
                            ((DBSelectResult) result).addResult(qName, attributes.getValue(i));
                            break;
                        }
                    }
                } catch (UnknownTagNameException e) {
                    throw new SAXException("Unknown tag name: "+e.getMessage());
                }
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        if(listener != null) {
            XMLParser.notifyMe(result, listener);
        } else {
            XMLParser.notifyListeners(result);
        }

    }

    private DBResult createResult(String qName, Attributes attributes){
        DBResult result1 = null;
        int action = Integer.valueOf(attributes.getValue("id"));
        int status = Integer.valueOf(attributes.getValue("status"));
        String text = attributes.getValue("text");
        if(status != 200){
            result1 = new DBResult(action, status, text);
        }else{
            if(action >= 200 && action <= 299 || action == 1
                    || action >= 100 && action <= 199) {
                result1 = new DBSelectResult(action);
            } else {
                result1 = new DBResult(action);
            }
        }
        return result1;
    }

}
